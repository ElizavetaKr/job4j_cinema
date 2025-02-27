package ru.job4j.cinema.dto;

import ru.job4j.cinema.model.Hall;

import java.time.LocalDateTime;
import java.util.Objects;

public class SessionDto {
    private int id;
    private String film;
    private Hall hall;
    private LocalDateTime start;
    private LocalDateTime end;
    private int price;

    public SessionDto(int id, String film, Hall hall, LocalDateTime start, LocalDateTime end, int price) {
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

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        SessionDto that = (SessionDto) object;
        return id == that.id && Objects.equals(film, that.film) && Objects.equals(hall, that.hall);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, film, hall);
    }
}
