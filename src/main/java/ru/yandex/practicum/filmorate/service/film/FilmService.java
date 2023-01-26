package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public void createFilm(Film film) {
        filmStorage.createFilm(film);
    }

    public Film getFilm(int id) {
        return filmStorage.getFilm(id);
    }

    public void deleteFilm(int id) {
        filmStorage.deleteFilm(id);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms().collect(Collectors.toList());
    }

    public void addLike(int filmId, int userId) {
        Set<Integer> likes = filmStorage.loadLikes(filmId).collect(Collectors.toSet());
        likes.add(userId);
        filmStorage.saveLikes(filmId, likes);
    }

    public void deleteLike(int filmId, int userId) {
        Set<Integer> likes = filmStorage.loadLikes(filmId).collect(Collectors.toSet());
        if (!likes.remove(userId)) {
            throw new NotFoundException("Film not found");
        }
        filmStorage.saveLikes(filmId, likes);
    }

    public Set<Integer> getLikes(int id) {
        return filmStorage.loadLikes(id).collect(Collectors.toSet());
    }
}
