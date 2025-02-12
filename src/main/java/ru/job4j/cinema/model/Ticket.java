package ru.job4j.cinema.model;

import java.util.Map;
import java.util.Objects;

public class Ticket {
    private int id;
    private int sessionId;
    private int row;
    private int place;
    private int userId;

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "session_id", "sessionId",
            "row_number", "row",
            "place_number", "place",
            "user_id", "userId"
    );

    public Ticket(int id, int sessionId, int row, int place, int userId) {
        this.id = id;
        this.sessionId = sessionId;
        this.row = row;
        this.place = place;
        this.userId = userId;
    }

    public Ticket() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) object;
        return id == ticket.id && sessionId == ticket.sessionId && userId == ticket.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sessionId, userId);
    }
}
