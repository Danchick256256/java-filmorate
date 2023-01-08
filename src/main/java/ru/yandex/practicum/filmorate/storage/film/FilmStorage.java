package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

public interface FilmStorage {
    void createFilm(Film film);
    Optional<Film> getFilm(int filmId);
    void deleteFilm(int filmId);
    Film updateFilm(Film film);
    ArrayList<Film> getAllFilms();
    void saveLikes(int id, Set<Integer> newLikes);
    Optional<Set<Integer>> loadLikes(int filmId);
    boolean filmExisting(int filmId);
}
