package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Genre;

import java.util.Collection;

@Repository
public class Sql2oGenreRepository implements GenreRepository {
    private final Sql2o sql2o;

    public Sql2oGenreRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Genre save(Genre genre) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("INSERT INTO genres(name) VALUES (:name)", true)
                    .addParameter("name", genre.getName());
            int id = query.executeUpdate().getKey(Integer.class);
            genre.setId(id);
            return genre;
        }
    }

    @Override
    public Collection<Genre> findAll() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM genres");
            return query.executeAndFetch(Genre.class);
        }
    }

    @Override
    public Genre findById(int id) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM genres WHERE id = :id")
                    .addParameter("id", id);
            return query.executeAndFetchFirst(Genre.class);
        }
    }

    @Override
    public Genre findByName(String name) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM genres WHERE name = :name")
                    .addParameter("name", name);
            return query.executeAndFetchFirst(Genre.class);
        }
    }
}
