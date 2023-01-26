package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface FilmStorage {
    Film createFilm(Film film);
    Film getFilm(int filmId);
    Film updateFilm(Film film);
    void deleteFilm(int filmId);
    Stream<Film> getAllFilms();
    void saveLikes(int id, Set<Integer> newLikes);
    Stream<Integer> loadLikes(int filmId);
}
