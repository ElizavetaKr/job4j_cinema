package ru.job4j.cinema.service;

import ru.job4j.cinema.dto.SessionDto;

import java.util.Collection;
import java.util.Optional;

public interface SessionService {

    void deleteById(int id);

    Optional<SessionDto> findById(int id);

    Collection<SessionDto> findAll();
}
