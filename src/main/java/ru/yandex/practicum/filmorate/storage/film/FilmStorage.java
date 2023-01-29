package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.stream.Stream;

public interface FilmStorage {
    Film createFilm(Film film);
    Film getFilm(int filmId);
    Film updateFilm(Film film);
    void deleteFilm(int filmId);
    Stream<Film> getAllFilms();
    void addLike(int filmId, int userId);
    void deleteLike(int filmId, int userId);
    Stream<Integer> getLikes(int filmId);
}
