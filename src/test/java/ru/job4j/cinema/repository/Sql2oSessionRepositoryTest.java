package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Session;

import javax.sql.DataSource;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oSessionRepositoryTest {
    private static Sql2oSessionRepository sql2oSessionRepository;

    private static Sql2o sql2o;

    @BeforeAll
    public static void initRepositories() throws Exception {
        Properties properties = new Properties();
        try (InputStream inputStream = Sql2oSessionRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        String url = properties.getProperty("datasource.url");
        String username = properties.getProperty("datasource.username");
        String password = properties.getProperty("datasource.password");

        DatasourceConfiguration configuration = new DatasourceConfiguration();
        DataSource datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

        sql2oSessionRepository = new Sql2oSessionRepository(sql2o);
    }

    @Test
    public void whenFindNotExistThenGetEmptyOptional() {
        Optional<Session> session = sql2oSessionRepository.findById(100);
        assertThat(session).isEmpty();
    }

    @Test
    public void whenDeleteThenGetEmptyOptional() {
        LocalDateTime start = LocalDateTime.of(2025, 10, 2, 10, 0);
        LocalDateTime end = LocalDateTime.of(2025, 10, 2, 12, 30);
        Session session = sql2oSessionRepository.save(new Session(1, 3, 1, start, end, 100));
        sql2oSessionRepository.deleteById(session.getId());
        Optional<Session> sessionOptional = sql2oSessionRepository.findById(session.getId());
        assertThat(sessionOptional).isEmpty();
    }

    @Test
    public void whenUpdateThenGetUpdated() {
        LocalDateTime start = LocalDateTime.of(2025, 10, 2, 10, 0);
        LocalDateTime end = LocalDateTime.of(2025, 10, 2, 12, 30);
        Session session = sql2oSessionRepository.save(new Session(1, 3, 1, start, end, 100));
        Session updatedSession = new Session(
                session.getId(), session.getFilmId(), session.getHallId(),
                session.getStart(), session.getEnd(), 150
        );
        boolean isUpdated = sql2oSessionRepository.update(updatedSession);
        var savedSession = sql2oSessionRepository.findById(updatedSession.getId()).get();
        assertThat(isUpdated).isTrue();
        assertThat(savedSession).usingRecursiveComparison().isEqualTo(updatedSession);
    }

    @Test
    public void whenUpdateExistSessionThenGetFalse() {
        LocalDateTime start = LocalDateTime.of(2025, 10, 2, 10, 0);
        LocalDateTime end = LocalDateTime.of(2025, 10, 2, 12, 30);
        Session session = new Session(1, 3, 1, start, end, 100);
        boolean isUpdated = sql2oSessionRepository.update(session);
        assertThat(isUpdated).isFalse();
    }
}