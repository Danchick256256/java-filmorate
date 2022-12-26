package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.GenerateFilmId;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static ru.yandex.practicum.filmorate.Constants.earliestFilmDate;

@RestController
@Slf4j
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();

    @PostMapping("/films")
    public ResponseEntity<Film> create(@Valid @RequestBody Film film) {
        log.info("POST request, create FILM");
        if (!film.getReleaseDate().isAfter(earliestFilmDate)) {
            log.error("ReleaseDate is before 1895-12-28");
            throw new ValidationException("{releaseDate.is.before.1895-12-28}");
        }

        if (film.getDescription().length() >= 200) {
            log.error("Description more than 200 characters");
            throw new ValidationException("{description.is.too.long}");
        }

        if (film.getName().isBlank() || film.getName() == null) {
            log.error("Name is Empty or Null");
            throw new ValidationException("{name.is.empty.or.null}");
        }

        if (film.getDuration() < 0) {
            log.error("Film duration is negative");
            throw new ValidationException("{duration.is.negative}");
        }

        film.setId(GenerateFilmId.generateId());
        films.put(film.getId(), film);
        return new ResponseEntity<>(films.get(film.getId()), HttpStatus.CREATED);
    }

    @PutMapping("/films")
    public ResponseEntity<Film> update(@Valid @RequestBody Film film) {

        if(films.containsKey(film.getId())){
            films.replace(film.getId(), film);
            return new ResponseEntity<>(films.get(film.getId()), HttpStatus.OK);
        } else {
            throw new ValidationException("{unknown.film}");
        }
    }

    @GetMapping("/films")
    public ResponseEntity<List<Film>> getAll() {
        log.info("GET request, get all FILM");
        return new ResponseEntity<>(new ArrayList<>(films.values()), HttpStatus.OK);
    }
}
