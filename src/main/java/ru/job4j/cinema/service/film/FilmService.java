package ru.job4j.cinema.service.film;

import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmService {
    Film save(FilmDto filmDto, FileDto image);

    boolean update(FilmDto filmDto, FileDto image);

    void deleteById(int id);

    Optional<FilmDto> findById(int id);

    Collection<FilmDto> findAll();
}
