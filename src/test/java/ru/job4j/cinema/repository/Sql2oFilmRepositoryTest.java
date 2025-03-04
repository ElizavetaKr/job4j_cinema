package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.film.Sql2oFilmRepository;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oFilmRepositoryTest {

    private static Sql2oFilmRepository sql2oFilmRepository;
    private static Sql2o sql2o;

    @BeforeAll
    public static void initRepositories() throws Exception {
        Properties properties = new Properties();
        try (InputStream inputStream = Sql2oFilmRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        String url = properties.getProperty("datasource.url");
        String username = properties.getProperty("datasource.username");
        String password = properties.getProperty("datasource.password");

        DatasourceConfiguration configuration = new DatasourceConfiguration();
        DataSource datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
    }

    @Test
    public void whenFindNotExistThenGetEmptyOptional() {
        Optional<Film> film = sql2oFilmRepository.findById(100);
        assertThat(film).isEmpty();
    }

    @Test
    public void whenDeleteThenGetEmptyOptional() {
        Film film = sql2oFilmRepository.save(new Film(1, "name", "desc..", 1, 1, 1, 1, 1));
        sql2oFilmRepository.deleteById(film.getId());
        Optional<Film> filmOptional = sql2oFilmRepository.findById(film.getId());
        assertThat(filmOptional).isEmpty();
    }

    @Test
    public void whenUpdateThenGetUpdated() {
        Film film = sql2oFilmRepository.save(new Film(1, "name", "desc..", 1, 1, 1, 1, 1));
        Film updatedFilm = new Film(film.getId(), film.getName(), film.getDescription(), 1, 1, 1, 1, 2);
        boolean isUpdated = sql2oFilmRepository.update(updatedFilm);
        var savedFilm = sql2oFilmRepository.findById(updatedFilm.getId()).get();
        assertThat(isUpdated).isTrue();
        assertThat(savedFilm).usingRecursiveComparison().isEqualTo(updatedFilm);
    }

    @Test
    public void whenUpdateExistFilmThenGetFalse() {
        Film film = new Film(10, "name", "desc..", 1, 1, 1, 1, 1);
        boolean isUpdated = sql2oFilmRepository.update(film);
        assertThat(isUpdated).isFalse();
    }

}