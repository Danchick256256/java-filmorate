package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage filmStorage, InMemoryUserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

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

    public ArrayList<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public void addLike(int filmId, int userId) {
        if (!filmStorage.filmContains(filmId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film not found");
        if (!userStorage.userContains(userId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        Set<Integer> likes = filmStorage.loadLikes(filmId).orElseGet(HashSet::new);
        likes.add(userId);
        filmStorage.saveLikes(filmId, likes);
    }

    public void deleteLike(int filmId, int userId) {
        if (!filmStorage.filmContains(filmId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film not found");
        if (!userStorage.userContains(userId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        Set<Integer> likes = filmStorage.loadLikes(filmId).orElseGet(HashSet::new);
        likes.remove(userId);
        filmStorage.saveLikes(filmId, likes);
    }

    public Set<Integer> getLikes(int id) {
        return filmStorage.loadLikes(id).orElseGet(HashSet::new);
    }
}
