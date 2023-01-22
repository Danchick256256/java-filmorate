package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films;
    private final Map<Integer, Set<Integer>> filmLikes;


    @Autowired
    public InMemoryFilmStorage() {
        films = new HashMap<>();
        filmLikes = new HashMap<>();
    }
    @Override
    public void createFilm(Film film) {
        films.put(film.getId(), film);
    }

    @Override
    public Optional<Film> getFilm(int filmId) {
        return Optional.ofNullable(films.get(filmId));
    }

    @Override
    public void deleteFilm(int filmId) {
        films.remove(filmId);
    }

    @Override
    public Film updateFilm(Film film) {
        films.replace(film.getId(), film);
        return film;
    }

    @Override
    public ArrayList<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public void saveLikes(int id, Set<Integer> newLikes) {
        filmLikes.put(id, newLikes);
    }

    @Override
    public Optional<Set<Integer>> loadLikes(int filmId) {
        return Optional.ofNullable(filmLikes.get(filmId));
    }

    @Override
    public boolean filmExisting(int filmId) {
        return films.containsKey(filmId);
    }
}
