package ru.job4j.cinema.service.genre;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.repository.genre.GenreRepository;

import java.util.Collection;

@Service
public class SimpleGenreService implements GenreService {

    private final GenreRepository genreRepository;

    public SimpleGenreService(GenreRepository sql2oGenreRepository) {
        this.genreRepository = sql2oGenreRepository;
    }

    @Override
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Collection<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre findById(int id) {
        return genreRepository.findById(id);
    }

    @Override
    public Genre findByName(String name) {
        return genreRepository.findByName(name);
    }

}
