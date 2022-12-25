package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();

    @PostMapping("/films")
    public ResponseEntity<Film> create(@Valid @RequestBody Film film) {
        log.info("POST request, create FILM");
        films.put(film.getId(), film);
        return new ResponseEntity<>(films.get(film.getId()), HttpStatus.CREATED);
    }

    @PutMapping("/films")
    public ResponseEntity<Film> update(@Valid @RequestBody Film film) {

        if(films.containsKey(film.getId())){
            films.replace(film.getId(), film);
            return new ResponseEntity<>(films.get(film.getId()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(film, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/films")
    public ResponseEntity<List<Film>> getAll() {
        log.info("GET request, get all FILM");
        return new ResponseEntity<>(new ArrayList<>(films.values()), HttpStatus.OK);
    }
}
