package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Constants;
import ru.yandex.practicum.filmorate.exceptions.BadRequestException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.util.GenerateFilmId;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;
    @GetMapping("/{id}")
    public Film getFilm(@PathVariable int id) {
        log.info("GET request, get FILM");
        return filmService.getFilm(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Film createFilm(@Valid @RequestBody Film film) {
        if (!film.getReleaseDate().isAfter(Constants.earliestFilmDate)) {
            log.error("ReleaseDate is before 1895-12-28");
            throw new BadRequestException("{releaseDate.is.before.1895-12-28}");
        }
        log.info("{POST.request.create.film}");
        film.setId(GenerateFilmId.generateId());
        return filmService.createFilm(film);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @GetMapping()
    public List<Film> getAllFilms() {
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
