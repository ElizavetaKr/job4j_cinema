package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Hall;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oHallRepository implements HallRepository {

    private final Sql2o sql2o;

    public Sql2oHallRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Hall save(Hall hall) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("""
                            INSERT INTO halls(name, row_count, place_count, description)
                            VALUES (:name, :row, :place, :description)
                            """, true)
                    .addParameter("name", hall.getName())
                    .addParameter("row", hall.getRow())
                    .addParameter("place", hall.getPlace())
                    .addParameter("description", hall.getDescription());
            int id = query.executeUpdate().getKey(Integer.class);
            hall.setId(id);
            return hall;
        }
    }

    @Override
    public boolean update(Hall hall) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("""
                            UPDATE halls
                            SET name = :name, row_count = :row, place_count = :place, description = :description
                            WHERE id = :id
                            """)
                    .addParameter("name", hall.getName())
                    .addParameter("row", hall.getRow())
                    .addParameter("place", hall.getPlace())
                    .addParameter("description", hall.getDescription());
            int affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = sql2o.open()) {
            connection.createQuery("DELETE FROM halls WHERE id = :id")
                    .addParameter("id", id);
        }
    }

    @Override
    public Optional<Hall> findById(int id) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM halls WHERE id = :id")
                    .addParameter("id", id);
            Hall hall = query.executeAndFetchFirst(Hall.class);
            return Optional.ofNullable(hall);
        }
    }

    @Override
    public Collection<Hall> findAll() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM halls");
            return query.setColumnMappings(Hall.COLUMN_MAPPING).executeAndFetch(Hall.class);
        }
    }
}
