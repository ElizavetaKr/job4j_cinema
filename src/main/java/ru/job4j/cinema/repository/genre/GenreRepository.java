package ru.job4j.cinema.repository.genre;

import ru.job4j.cinema.model.Genre;

import java.util.Collection;

public interface GenreRepository {

    Genre save(Genre genre);

    Collection<Genre> findAll();

    Genre findById(int id);

    Genre findByName(String name);
}
