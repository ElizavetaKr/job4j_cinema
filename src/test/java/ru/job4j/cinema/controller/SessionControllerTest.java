package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.job4j.cinema.service.SessionService;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SessionControllerTest {

    private SessionController sessionController;
    private SessionService sessionService;

    @BeforeEach
    public void initServices() {
        sessionService = mock(SessionService.class);
        sessionController = new SessionController(sessionService);
    }

    @Test
    public void whenGetAllThenPageWithSessions() {
        Model model = new ConcurrentModel();

        assertThat(sessionController.getAll(model)).isEqualTo("sessions/list");
    }
}