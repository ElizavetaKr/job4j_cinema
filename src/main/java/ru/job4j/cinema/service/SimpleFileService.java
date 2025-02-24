package ru.job4j.cinema.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.repository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Service
public class SimpleFileService implements FileService {

    private final FileRepository fileRepository;
    private final String storageDirectory;

    public SimpleFileService(FileRepository sql2oFileRepository,
                             @Value("${file.directory}") String storageDirectory) {
        this.fileRepository = sql2oFileRepository;
        this.storageDirectory = storageDirectory;
        createStorageDirectory(storageDirectory);
    }

    private void createStorageDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getNewFilePath(String sourceName) {
        return storageDirectory + java.io.File.separator + UUID.randomUUID() + sourceName;
    }

    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteFile(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File save(FileDto fileDto) {
        var path = getNewFilePath(fileDto.getName());
        writeFileBytes(path, fileDto.getContent());
        return fileRepository.save(new File(fileDto.getName(), path));
    }

    @Override
    public void deleteById(int id) {
        Optional<File> file = fileRepository.findById(id);
        if (file.isPresent()) {
            deleteFile(file.get().getPath());
            fileRepository.deleteById(id);
        }
    }

    @Override
    public Optional<FileDto> findById(int id) {
        Optional<File> file = fileRepository.findById(id);
        if (file.isEmpty()) {
            return Optional.empty();
        }
        byte[] content = readFileAsBytes(file.get().getPath());
        return Optional.of(new FileDto(file.get().getName(), content));
    }
}
