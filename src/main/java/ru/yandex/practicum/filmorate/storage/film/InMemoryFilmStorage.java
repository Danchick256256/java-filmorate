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
    public Film getFilm(int id) {
        return films.get(id);
    }

    @Override
    public void deleteFilm(int id) {
        films.remove(id);
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
    public Optional<Set<Integer>> loadLikes(int id) {
        return Optional.ofNullable(filmLikes.get(id));
    }

    @Override
    public boolean filmContains(int id) {
        return films.containsKey(id);
    }
}
