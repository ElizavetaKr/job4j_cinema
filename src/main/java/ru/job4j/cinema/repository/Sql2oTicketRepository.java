package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import ru.job4j.cinema.model.Ticket;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oTicketRepository implements TicketRepository {

    private final Sql2o sql2o;

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("""
                                   INSERT INTO tickets(session_id, row_number, place_number, user_id)
                                   VALUES (:sessionId, :row, :place, :userId)
                            """, true)
                    .addParameter("sessionId", ticket.getSessionId())
                    .addParameter("row", ticket.getRow())
                    .addParameter("place", ticket.getPlace())
                    .addParameter("userId", ticket.getUserId());
            int id = query.executeUpdate().getKey(Integer.class);
            ticket.setId(id);
            return Optional.of(ticket);
        } catch (Sql2oException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Ticket ticket) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("""
                                   UPDATE tickets
                                   SET session_id = :sessionId, row_number = :row, place_number = :place, user_id = :userId
                                   WHERE id = :id
                            """)
                    .addParameter("sessionId", ticket.getSessionId())
                    .addParameter("row", ticket.getRow())
                    .addParameter("place", ticket.getPlace())
                    .addParameter("userId", ticket.getUserId());
            int affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = sql2o.open()) {
            connection.createQuery("DELETE FROM tickets WHERE id = :id")
                    .addParameter("id", id);
        }
    }

    @Override
    public Optional<Ticket> findByRowAndPlace(int row, int place) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("""
                            SELECT * FROM tickets WHERE row_number = :row
                            AND place_number = :place
                            """)
                    .addParameter("row", row)
                    .addParameter("place", place);
            Ticket ticket = query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetchFirst(Ticket.class);
            return Optional.ofNullable(ticket);
        }
    }

    @Override
    public Collection<Ticket> findAll() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM tickets");
            return query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetch(Ticket.class);
        }
    }
}
