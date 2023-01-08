package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
        Optional<Film> film = filmStorage.getFilm(id);
        if (film.isPresent()) {
            log.info("{get.film}:{}", film);
            return film.get();
        } else {
            log.info("{film.not.found}");
            throw new NotFoundException("Film not Found");
        }
    }

    public void deleteFilm(int id) {
        filmStorage.deleteFilm(id);
    }

    public Film updateFilm(Film film) {
        if (filmStorage.getFilm(film.getId()).isEmpty()) {
            throw new NotFoundException("Film Not Found");
        }
        return filmStorage.updateFilm(film);
    }

    public ArrayList<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public void addLike(int filmId, int userId) {
        if (!filmStorage.filmExisting(filmId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film not found");
        if (!userStorage.userExisting(userId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        Set<Integer> likes = filmStorage.loadLikes(filmId).orElseGet(HashSet::new);
        likes.add(userId);
        filmStorage.saveLikes(filmId, likes);
    }

    public void deleteLike(int filmId, int userId) {
        if (!filmStorage.filmExisting(filmId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film not found");
        if (!userStorage.userExisting(userId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        Set<Integer> likes = filmStorage.loadLikes(filmId).orElseGet(HashSet::new);
        likes.remove(userId);
        filmStorage.saveLikes(filmId, likes);
    }

    public Set<Integer> getLikes(int id) {
        return filmStorage.loadLikes(id).orElseGet(HashSet::new);
    }
}
