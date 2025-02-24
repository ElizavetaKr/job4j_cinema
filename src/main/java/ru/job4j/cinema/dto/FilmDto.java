package ru.job4j.cinema.dto;

public class FilmDto {
    private int id;
    private String name;
    private String description;
    private String genre;
    private int filmYear;
    private int minimalAge;
    private int duration;
    private int fileId;

    public FilmDto(int id, String name, String description, String genre, int... params) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.filmYear = params[0];
        this.minimalAge = params[1];
        this.duration = params[2];
        this.fileId = params[3];
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}
