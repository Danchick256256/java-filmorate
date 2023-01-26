package ru.yandex.practicum.filmorate.storage.film.Dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Set;
import java.util.stream.Stream;

@Component
@Qualifier("FilmDao")
public class FilmDao implements FilmStorage {
    @Override
    public Film createFilm(Film film) {
        return null;
    }

    @Override
    public Film getFilm(int filmId) {
        return null;
    }

    @Override
    public Film updateFilm(Film film) {
        return null;
    }

    @Override
    public void deleteFilm(int filmId) {

    }

    @Override
    public Stream<Film> getAllFilms() {
        return null;
    }

    @Override
    public void saveLikes(int id, Set<Integer> newLikes) {

    }

    @Override
    public Stream<Integer> loadLikes(int filmId) {
        return null;
    }
}
