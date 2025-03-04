package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.job4j.cinema.service.film.FilmService;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FilmControllerTest {

    private FilmController filmController;
    private FilmService filmService;

    @BeforeEach
    public void initServices() {
        filmService = mock(FilmService.class);
        filmController = new FilmController(filmService);
    }

    @Test
    public void whenGetAllThenPageWithFilms() {
        Model model = new ConcurrentModel();

        assertThat(filmController.getAll(model)).isEqualTo("films/list");
    }

}