package ru.job4j.cinema.service;

import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.model.File;

import java.util.Optional;

public interface FileService {
    File save(FileDto fileDto);

    void deleteById(int id);

    Optional<FileDto> findById(int id);

}
