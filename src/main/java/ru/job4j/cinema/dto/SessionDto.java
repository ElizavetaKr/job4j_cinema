package ru.job4j.cinema.dto;

import java.time.LocalDateTime;

public class SessionDto {
    private int id;
    private String film;
    private String hall;
    private LocalDateTime start;
    private LocalDateTime end;
    private int price;

    public SessionDto(int id, String film, String hall, LocalDateTime start, LocalDateTime end, int price) {
        this.id = id;
        this.film = film;
        this.hall = hall;
        this.start = start;
        this.end = end;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
