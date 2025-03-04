package ru.job4j.cinema.repository.file;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.File;

import java.util.Optional;

@Repository
public class Sql2oFileRepository implements FileRepository {

    private final Sql2o sql2o;

    public Sql2oFileRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public File save(File file) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("INSERT INTO files(name, path) VALUES (:name, :path)", true)
                    .addParameter("name", file.getName())
                    .addParameter("path", file.getPath());
            int id = query.executeUpdate().getKey(Integer.class);
            file.setId(id);
            return file;
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = sql2o.open()) {
            connection.createQuery("DELETE FROM files WHERE id = :id")
                    .addParameter("id", id).executeUpdate();
        }
    }

    @Override
    public Optional<File> findById(int id) {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM files WHERE id = :id")
                    .addParameter("id", id);
            File file = query.executeAndFetchFirst(File.class);
            return Optional.ofNullable(file);
        }
    }
}
