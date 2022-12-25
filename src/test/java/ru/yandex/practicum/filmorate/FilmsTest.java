package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class FilmsTest {

    private final FilmController filmController = new FilmController();

    @Test
    void createFilm() {
        Film film = new Film(1, "testFilm", "testDescription", 100, LocalDate.parse("1995-12-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        ResponseEntity<Film> createdFilm = filmController.create(film);
        assertEquals(createdFilm, new ResponseEntity<>(film, HttpStatus.CREATED));
    }

    @Test
    void updateFilm() {
        Film film = new Film(1, "testFilm", "newDesc", 100, LocalDate.parse("1995-12-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        filmController.create(film);
        ResponseEntity<Film> updatedFilm = filmController.update(film);
        assertEquals(updatedFilm, new ResponseEntity<>(film, HttpStatus.OK));
    }

    @Test
    void emptyName() {
        Film film = new Film(1, "", "newDesc", 100, LocalDate.parse("1995-12-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        ValidationException validationException = assertThrows(
                ValidationException.class,
                () -> filmController.create(film));
        assertEquals("{name.is.empty.or.null}", validationException.getMessage());
    }

    @Test
    void longDescription() {
        Film film = new Film(1, "filmName", "c".repeat(210), 100, LocalDate.parse("1995-12-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        ValidationException validationException = assertThrows(
                ValidationException.class,
                () -> filmController.create(film));
        assertEquals("{description.is.too.long}", validationException.getMessage());
    }

    @Test
    void failReleaseDate() {
        Film film = new Film(1, "filmName", "newDesc", 100, LocalDate.parse("1795-12-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        ValidationException validationException = assertThrows(
                ValidationException.class,
                () -> filmController.create(film));
        assertEquals("{releaseDate.is.before.1895-12-28}", validationException.getMessage());
    }

    @Test
    void failDuration() {
        Film film = new Film(1, "filmName", "newDesc", -100, LocalDate.parse("1995-12-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        ValidationException validationException = assertThrows(
                ValidationException.class,
                () -> filmController.create(film));
        assertEquals("{duration.is.negative}", validationException.getMessage());
    }

    @Test
    void updateUnknown() {
        Film newFilm = new Film(100, "testFilm", "newDesc", 100, LocalDate.parse("1995-12-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        ValidationException validationException = assertThrows(
                ValidationException.class,
                () -> filmController.update(newFilm));
        assertEquals("{unknown.film}", validationException.getMessage());
    }

    @Test
    void getAll() {
        Film film = new Film(1, "testFilm", "testDescription", 100, LocalDate.parse("1995-12-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        filmController.create(film);
        ResponseEntity<List<Film>> createdFilm = filmController.getAll();
        List<Film> filmList = new ArrayList<>();
        filmList.add(film);
        assertEquals(createdFilm, new ResponseEntity<>(filmList, HttpStatus.OK));
    }
}
