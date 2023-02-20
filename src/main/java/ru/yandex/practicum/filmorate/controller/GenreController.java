package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreStorage genreStorage;
    @GetMapping("/{id}")
    public Genre getGenre(@PathVariable int id) {
        log.info("GET request, get Genre");
        return genreStorage.getGenre(id);
    }

    @GetMapping()
    public List<Genre> getAllGenre() {
        log.info("GET request, get all Genre");
        return genreStorage.getGenreList().collect(Collectors.toList());
    }
}
