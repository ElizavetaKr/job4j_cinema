package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Session;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oSessionRepository implements SessionRepository {
    private final Sql2o sql2o;

    public Sql2oSessionRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Session save(Session session) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("""
                            INSERT INTO film_sessions(film_id, hall_id, start_time, end_time, price)
                            VALUES (:filmId, :hallId, :start, :end, :price)
                            """, true)
                    .addParameter("filmId", session.getFilmId())
                    .addParameter("hallId", session.getHallId())
                    .addParameter("start", session.getStart())
                    .addParameter("end", session.getEnd())
                    .addParameter("price", session.getPrice());
            int id = query.executeUpdate().getKey(Integer.class);
            session.setId(id);
            return session;
        }
    }

    @Override
    public boolean update(Session session) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("""
                            UPDATE film_sessions
                            SET film_id = :filmId, hall_id = :hallId, start_time = :start, end_time = :end, price = :price
                            WHERE id = :id
                            """)
                    .addParameter("filmId", session.getFilmId())
                    .addParameter("hallId", session.getHallId())
                    .addParameter("start", session.getStart())
                    .addParameter("end", session.getEnd())
                    .addParameter("price", session.getPrice());
            int affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = sql2o.open()) {
            connection.createQuery("DELETE FROM film_sessions WHERE id = :id")
                    .addParameter("id", id);
        }
    }

    @Override
    public Optional<Session> findById(int id) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM film_sessions WHERE id = :id")
                    .addParameter("id", id);
            Session session = query.setColumnMappings(Session.COLUMN_MAPPING).executeAndFetchFirst(Session.class);
            return Optional.ofNullable(session);
        }
    }

    @Override
    public Collection<Session> findAll() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM film_sessions");
            return query.setColumnMappings(Session.COLUMN_MAPPING).executeAndFetch(Session.class);
        }
    }
}
