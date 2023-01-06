package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.util.GenerateFilmId;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ru.yandex.practicum.filmorate.Constants.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable int id) {
        if (filmService.getFilm(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film not found");
        }
        log.info("GET request, get FILM");
        return filmService.getFilm(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Film createFilm(@Valid @RequestBody Film film) {
        if (!film.getReleaseDate().isAfter(LocalDate.parse("1895-12-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
            log.error("ReleaseDate is before 1895-12-28");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "{releaseDate.is.before.1895-12-28}");
        }
        log.info("{POST.request.create.film}");

        film.setId(GenerateFilmId.generateId());
        filmService.createFilm(film);
        return film;
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (filmService.getFilm(film.getId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film not found");
        }
        return filmService.updateFilm(film);
    }

    @GetMapping()
    public ArrayList<Film> getAllFilms() {
        log.info("GET request, get all FILM");
        return filmService.getAllFilms();
    }

    @PutMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public List<Film> getPopular(@RequestParam(defaultValue = "10") Integer count) {
        List<Film> allFilms = getAllFilms();
        return allFilms
                .stream()
                .sorted((o1, o2) -> filmService.getLikes(o2.getId()).size() - filmService.getLikes(o1.getId()).size())
                .limit(count)
                .collect(Collectors.toList());
    }
}
