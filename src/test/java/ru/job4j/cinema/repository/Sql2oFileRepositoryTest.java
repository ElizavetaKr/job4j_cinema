package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.repository.file.Sql2oFileRepository;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oFileRepositoryTest {
    private static Sql2oFileRepository sql2oFileRepository;
    private static Sql2o sql2o;

    @BeforeAll
    public static void initRepositories() throws Exception {
        Properties properties = new Properties();
        try (InputStream inputStream = Sql2oFileRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        String url = properties.getProperty("datasource.url");
        String username = properties.getProperty("datasource.username");
        String password = properties.getProperty("datasource.password");

        DatasourceConfiguration configuration = new DatasourceConfiguration();
        DataSource datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

        sql2oFileRepository = new Sql2oFileRepository(sql2o);
    }

    @Test
    public void whenFindNotExistThenGetEmptyOptional() {
        Optional<File> file = sql2oFileRepository.findById(100);
        assertThat(file).isEmpty();
    }

    @Test
    public void whenDeleteThenGetEmptyOptional() {
        File file = sql2oFileRepository.save(new File("name", "path"));
        sql2oFileRepository.deleteById(file.getId());
        Optional<File> fileOptional = sql2oFileRepository.findById(file.getId());
        assertThat(fileOptional).isEmpty();
    }

    @Test
    public void whenFindByIdThenGetFile() {
        Optional<File> file = sql2oFileRepository.findById(1);
        File expectedFile = new File(1, "Avatar", "src/main/resources/files/Avatar.jpg");
        assertThat(file.get()).usingRecursiveComparison().isEqualTo(expectedFile);
    }
}