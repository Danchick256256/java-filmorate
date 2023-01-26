package ru.yandex.practicum.filmorate.storage.film.inMemory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;
import java.util.stream.Stream;

@Component
@Qualifier("FilmInMemory")
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films;
    private final Map<Integer, Set<Integer>> filmLikes;
    public InMemoryFilmStorage() {
        films = new HashMap<>();
        filmLikes = new HashMap<>();
    }
    @Override
    public Film createFilm(Film film) {
        int filmId = film.getId();
        films.put(filmId, film);
        return films.get(filmId);
    }
    @Override
    public Film getFilm(int filmId) {
        Optional<Film> film = Optional.ofNullable(films.get(filmId));
        if (film.isPresent()) {
            return film.get();
        } else {
            throw new NotFoundException("Film not found");
        }
    }
    @Override
    public Film updateFilm(Film film) {
        int filmId = film.getId();
        if (films.containsKey(filmId)) {
            films.replace(film.getId(), film);
            return films.get(filmId);
        } else {
            throw new NotFoundException("Film not found");
        }
    }
    @Override
    public void deleteFilm(int filmId) {
        films.remove(filmId);
    }
    @Override
    public Stream<Film> getAllFilms() {
        return films.values().stream();
    }
    @Override
    public void saveLikes(int id, Set<Integer> newLikes) {
        filmLikes.put(id, newLikes);
    }
    @Override
    public Stream<Integer> loadLikes(int filmId) {
        return filmLikes.get(filmId) == null ? Stream.empty() : filmLikes.get(filmId).stream();
    }
}
