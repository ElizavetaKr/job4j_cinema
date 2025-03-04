package ru.job4j.cinema.repository.film;

import ru.job4j.cinema.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmRepository {
    Film save(Film film);

    boolean update(Film film);

    void deleteById(int id);

    Optional<Film> findById(int id);

    Collection<Film> findAll();

}
