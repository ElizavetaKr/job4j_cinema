package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.dto.SessionDto;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.SessionRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleSessionService implements SessionService {
    private final SessionRepository sessionRepository;
    private final FilmService filmService;
    private final HallService hallService;

    public SimpleSessionService(SessionRepository sessionRepository, FilmService filmService, HallService hallService) {
        this.sessionRepository = sessionRepository;
        this.filmService = filmService;
        this.hallService = hallService;
    }

    private SessionDto toDto(Session session) {
        Optional<FilmDto> film = filmService.findById(session.getFilmId());
        Optional<Hall> hall = hallService.findById(session.getHallId());
        if (film.isPresent() && hall.isPresent()) {
            return new SessionDto(session.getId(), film.get().getName(),
                    hall.get().getName(), session.getStart(), session.getEnd(),
                    session.getPrice());
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        Optional<SessionDto> sessionDto = findById(id);
        if (sessionDto.isPresent()) {
            sessionRepository.deleteById(id);
        }
    }

    @Override
    public Optional<SessionDto> findById(int id) {
        Optional<Session> sessionOptional = sessionRepository.findById(id);
        if (sessionOptional.isEmpty()) {
            return Optional.empty();
        }
        SessionDto sessionDto = toDto(sessionOptional.get());
        return Optional.ofNullable(sessionDto);
    }

    @Override
    public Collection<SessionDto> findAll() {
        Collection<SessionDto> result = new ArrayList<>();
        Collection<Session> sessions = sessionRepository.findAll();
        for (Session session : sessions) {
            result.add(toDto(session));
        }
        return result;
    }
}
