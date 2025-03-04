package ru.job4j.cinema.service.film;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.film.FilmRepository;
import ru.job4j.cinema.service.genre.GenreService;
import ru.job4j.cinema.service.file.FileService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleFilmService implements FilmService {
    private final FilmRepository filmRepository;

    private final FileService fileService;

    private final GenreService genreService;

    public SimpleFilmService(FilmRepository sql2oFilmRepository, FileService fileService, GenreService genreService) {
        this.filmRepository = sql2oFilmRepository;
        this.fileService = fileService;
        this.genreService = genreService;
    }

    private void saveNewFile(FilmDto filmDto, FileDto image) {
        File file = fileService.save(image);
        filmDto.setFileId(file.getId());
    }

    private Film fromDto(FilmDto filmDto) {
        int genreId = genreService.findByName(filmDto.getGenre()).getId();
        return new Film(filmDto.getId(), filmDto.getName(), filmDto.getDescription(),
                filmDto.getFilmYear(), genreId, filmDto.getMinimalAge(), filmDto.getDuration(),
                filmDto.getFileId());
    }

    private FilmDto toDto(Film film) {
        String genreName = genreService.findById(film.getGenreId()).getName();
        return new FilmDto(film.getId(), film.getName(), film.getDescription(),
                genreName, film.getFilmYear(), film.getMinimalAge(),
                film.getDuration(), film.getFileId());
    }

    @Override
    public Film save(FilmDto filmDto, FileDto image) {
        saveNewFile(filmDto, image);
        return filmRepository.save(fromDto(filmDto));
    }

    @Override
    public boolean update(FilmDto filmDto, FileDto image) {
        boolean isNewFileEmpty = image.getContent().length == 0;
        Film film = fromDto(filmDto);
        if (isNewFileEmpty) {
            return filmRepository.update(film);
        }
        int oldFileId = filmDto.getFileId();
        saveNewFile(filmDto, image);
        boolean isUpdated = filmRepository.update(film);
        fileService.deleteById(oldFileId);
        return isUpdated;
    }

    @Override
    public void deleteById(int id) {
        Optional<FilmDto> film = findById(id);
        if (film.isPresent()) {
            filmRepository.deleteById(id);
            fileService.deleteById(film.get().getFileId());
        }
    }

    @Override
    public Optional<FilmDto> findById(int id) {
        Optional<Film> film = filmRepository.findById(id);
        if (film.isEmpty()) {
            return Optional.empty();
        }
        FilmDto filmDto = toDto(film.get());
        return Optional.of(filmDto);
    }

    @Override
    public Collection<FilmDto> findAll() {
        Collection<FilmDto> result = new ArrayList<>();
        Collection<Film> films = filmRepository.findAll();
        for (Film film : films) {
            result.add(toDto(film));
        }
        return result;
    }
}
