package ru.job4j.cinema.service.ticket;

import ru.job4j.cinema.model.Ticket;

import java.util.Collection;
import java.util.Optional;

public interface TicketService {
    Optional<Ticket> save(Ticket ticket);

    boolean update(Ticket ticket);

    void deleteById(int id);

    Optional<Ticket> findByRowAndPlace(int row, int place);

    Collection<Ticket> findAll();
}
