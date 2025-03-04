package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.job4j.cinema.dto.SessionDto;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.session.SessionService;
import ru.job4j.cinema.service.ticket.TicketService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketControllerTest {
    private TicketController ticketController;
    private TicketService ticketService;

    private SessionService sessionService;

    @BeforeEach
    public void initServices() {
        ticketService = mock(TicketService.class);
        sessionService = mock(SessionService.class);
        ticketController = new TicketController(ticketService, sessionService);
    }

    @Test
    public void whenGetAllThenPageWithTickets() {
        Model model = new ConcurrentModel();
        LocalDateTime start = LocalDateTime.of(2025, 10, 2, 10, 0);
        LocalDateTime end = LocalDateTime.of(2025, 10, 2, 12, 30);
        Hall hall = new Hall(1, "hall", 1, 1, "desc");
        Optional<SessionDto> session = Optional.of(
                new SessionDto(1, "film", hall, start, end, 1));
        when(sessionService.findById(anyInt())).thenReturn(session);
        assertThat(ticketController.getAll(model, 1)).isEqualTo("tickets/list");

    }

    @Test
    public void whenSessionNotFoundThenPageWithError() {
        Model model = new ConcurrentModel();
        when(sessionService.findById(anyInt())).thenReturn(Optional.empty());
        assertThat(ticketController.getAll(model, 1)).isEqualTo("errors/404");
    }

    @Test
    public void whenTicketWasBoughtThenPageWithError() {
        Model model = new ConcurrentModel();
        Ticket ticket = new Ticket();
        when(ticketService.save(ticket)).thenReturn(Optional.empty());
        assertThat(ticketController.buyTicket(model, ticket, 1)).isEqualTo("errors/409");
    }

    @Test
    public void whenBuyTicketThenPageWithBuy() {
        Model model = new ConcurrentModel();
        Ticket ticket = new Ticket();
        when(ticketService.save(ticket)).thenReturn(Optional.of(ticket));
        assertThat(ticketController.buyTicket(model, ticket, 1)).isEqualTo("tickets/buy");
    }

}