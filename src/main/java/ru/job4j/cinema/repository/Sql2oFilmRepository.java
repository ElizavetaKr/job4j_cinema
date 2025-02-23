package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Film;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oFilmRepository implements FilmRepository {
    private final Sql2o sql2o;

    public Sql2oFilmRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Film save(Film film) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("""
                            INSERT INTO films(name, description, film_year, genre_id, minimal_age, duration_in_minutes, file_id)
                            VALUES (:name, :description, :filmYear, :genreId, :minimalAge, :duration, :fileId)
                            """, true)
                    .addParameter("name", film.getName())
                    .addParameter("description", film.getDescription())
                    .addParameter("filmYear", film.getFilmYear())
                    .addParameter("genreId", film.getGenreId())
                    .addParameter("minimalAge", film.getMinimalAge())
                    .addParameter("duration", film.getDuration())
                    .addParameter("fileId", film.getFileId());
            int id = query.executeUpdate().getKey(Integer.class);
            film.setId(id);
            return film;
        }
    }

    @Override
    public boolean update(Film film) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("""
                            UPDATE films
                            SET name = :name, description = :description, film_year = :filmYear, genre_id = :genreId,
                            minimal_age = :minimalAge, duration_in_minutes = :duration, file_id = :fileId
                            WHERE id = :id
                            """)
                    .addParameter("name", film.getName())
                    .addParameter("description", film.getDescription())
                    .addParameter("filmYear", film.getFilmYear())
                    .addParameter("genreId", film.getGenreId())
                    .addParameter("minimalAge", film.getMinimalAge())
                    .addParameter("duration", film.getDuration())
                    .addParameter("fileId", film.getFileId());
            int affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = sql2o.open()) {
            connection.createQuery("DELETE FROM films WHERE id = :id")
                    .addParameter("id", id);
        }
    }

    @Override
    public Optional<Film> findById(int id) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM films WHERE id = :id")
                    .addParameter("id", id);
            Film film = query.executeAndFetchFirst(Film.class);
            return Optional.ofNullable(film);
        }
    }

    @Override
    public Collection<Film> findAll() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM films");
            return query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetch(Film.class);
        }
    }
}
