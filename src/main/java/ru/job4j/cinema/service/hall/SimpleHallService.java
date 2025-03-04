package ru.job4j.cinema.service.hall;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.hall.HallRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleHallService implements HallService {

    private final HallRepository hallRepository;

    public SimpleHallService(HallRepository sql2oHallRepository) {
        this.hallRepository = sql2oHallRepository;
    }

    @Override
    public Hall save(Hall hall) {
        return hallRepository.save(hall);
    }

    @Override
    public boolean update(Hall hall) {
        return hallRepository.update(hall);
    }

    @Override
    public void deleteById(int id) {
        hallRepository.deleteById(id);
    }

    @Override
    public Optional<Hall> findById(int id) {
        return hallRepository.findById(id);
    }

    @Override
    public Collection<Hall> findAll() {
        return hallRepository.findAll();
    }
}
