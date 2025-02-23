package ru.job4j.cinema.model;

import java.util.Map;
import java.util.Objects;

public class Film {
    private int id;
    private String name;
    private String description;
    private int filmYear;
    private int genreId;
    private int minimalAge;
    private int duration;
    private int fileId;

    /**
     * ключи - это столбцы из БД, а значения - названия полей.
     */
    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "name", "name",
            "description", "description",
            "film_year", "filmYear",
            "genre_id", "genreId",
            "minimal_age", "minimalAge",
            "duration_in_minutes", "duration",
            "file_id", "fileId"
    );

    public Film(int id, String name, String description, int... params) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.filmYear = params[0];
        this.genreId = params[1];
        this.minimalAge = params[2];
        this.duration = params[3];
        this.fileId = params[4];
    }

    public Film() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFilmYear() {
        return filmYear;
    }

    public void setFilmYear(int filmYear) {
        this.filmYear = filmYear;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public int getMinimalAge() {
        return minimalAge;
    }

    public void setMinimalAge(int minimalAge) {
        this.minimalAge = minimalAge;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Film film = (Film) object;
        return id == film.id && duration == film.duration && Objects.equals(name, film.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration);
    }
}
