package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Hall;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oHallRepositoryTest {

        private static Sql2oHallRepository sql2oHallRepository;

        private static Sql2o sql2o;

        @BeforeAll
        public static void initRepositories() throws Exception {
            Properties properties = new Properties();
            try (InputStream inputStream = Sql2oHallRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
                properties.load(inputStream);
            }
            String url = properties.getProperty("datasource.url");
            String username = properties.getProperty("datasource.username");
            String password = properties.getProperty("datasource.password");

            DatasourceConfiguration configuration = new DatasourceConfiguration();
            DataSource datasource = configuration.connectionPool(url, username, password);
            sql2o = configuration.databaseClient(datasource);

            sql2oHallRepository = new Sql2oHallRepository(sql2o);
        }

        @Test
        public void whenFindNotExistThenGetEmptyOptional() {
            Optional<Hall> hall = sql2oHallRepository.findById(100);
            assertThat(hall).isEmpty();
        }

        @Test
        public void whenDeleteThenGetEmptyOptional() {
            Hall hall = sql2oHallRepository.save(new Hall(1, "name", 1, 1, "desc"));
            sql2oHallRepository.deleteById(hall.getId());
            Optional<Hall> hallOptional = sql2oHallRepository.findById(hall.getId());
            assertThat(hallOptional).isEmpty();
        }

        @Test
        public void whenUpdateThenGetUpdated() {
            Hall hall = sql2oHallRepository.save(new Hall(1, "name", 1, 1, "desc"));
            Hall updatedHall = new Hall(
                    hall.getId(), hall.getName(), hall.getRow(),
                    2, hall.getDescription()
            );
            boolean isUpdated = sql2oHallRepository.update(updatedHall);
            var savedHall = sql2oHallRepository.findById(updatedHall.getId()).get();
            assertThat(isUpdated).isTrue();
            assertThat(savedHall).usingRecursiveComparison().isEqualTo(updatedHall);
        }

        @Test
        public void whenUpdateExistHallThenGetFalse() {
            Hall hall = new Hall(10, "name", 1, 1, "desc");
            boolean isUpdated = sql2oHallRepository.update(hall);
            assertThat(isUpdated).isFalse();
        }
    }