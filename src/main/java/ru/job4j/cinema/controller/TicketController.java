package ru.job4j.cinema.controller;

import com.google.errorprone.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.dto.SessionDto;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ThreadSafe
@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final SessionService sessionService;

    public TicketController(TicketService ticketService, SessionService sessionService) {
        this.ticketService = ticketService;
        this.sessionService = sessionService;
    }

    @GetMapping("/{sessionId}")
    public String getAll(Model model, @PathVariable int sessionId) {
        Optional<SessionDto> sessionDto = sessionService.findById(sessionId);
        if (sessionDto.isEmpty()) {
            model.addAttribute("message", "Фильм не найден");
            return "errors/404";
        }
        SessionDto session = sessionDto.get();
        model.addAttribute("filmSession", session);
        Hall hall = session.getHall();
        List<Integer> rows = IntStream.rangeClosed(1, hall.getRow()).boxed().collect(Collectors.toList());
        List<Integer> places = IntStream.rangeClosed(1, hall.getPlace()).boxed().collect(Collectors.toList());
        model.addAttribute("rows", rows);
        model.addAttribute("places", places);
        return "tickets/list";
    }

    @PostMapping("/{sessionId}")
    public String buyTicket(Model model, @ModelAttribute Ticket ticket, @PathVariable int sessionId) {
        Optional<Ticket> buyTicket = ticketService.save(ticket);
        if (buyTicket.isEmpty()) {
            model.addAttribute("message", "Видимо, этот билет уже куплен. Попробуйте забронировать другой");
            return "errors/404";
        }
        Ticket ticket1 = buyTicket.get();
        String atr = "Ряд: " + ticket1.getRow() + " Место: " + ticket1.getPlace();
        model.addAttribute("message", atr);
        return "tickets/buy";
    }
}
