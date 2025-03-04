package ru.job4j.cinema.repository.session;

import ru.job4j.cinema.model.Session;

import java.util.Collection;
import java.util.Optional;

public interface SessionRepository {
    Session save(Session session);

    boolean update(Session session);

    void deleteById(int id);

    Optional<Session> findById(int id);

    Collection<Session> findAll();

}
