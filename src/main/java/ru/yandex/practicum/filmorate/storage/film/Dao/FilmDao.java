package ru.yandex.practicum.filmorate.storage.film.Dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.genre.Dao.GenreDao;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.Dao.MpaDao;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Qualifier("FilmDao")
@Slf4j
public class FilmDao implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;

    public FilmDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaStorage = new MpaDao(jdbcTemplate);
        this.genreStorage = new GenreDao(jdbcTemplate);
    }
    @Override
    public Film createFilm(Film film) {
        Set<Integer> genreArrayJava = film.getGenres() != null ? film.getGenres().stream().map(Genre::getId).collect(Collectors.toSet()) : Collections.emptySet();
        jdbcTemplate.execute((Connection connection) -> jdbcTemplate.update("INSERT INTO films(name, description, release_date, duration, mpa_rating_id, genre) VALUES (?, ?, ?, ?, ?, ?);", film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), connection.createArrayOf("INTEGER", genreArrayJava.toArray())));
        String query = "SELECT * FROM films WHERE film_id = ?;";
        return jdbcTemplate.queryForObject(query, (resultSet, rowNum) -> mapFilm(resultSet), film.getId());
    }

    @Override
    public Film getFilm(int filmId) {
        String query = "SELECT * FROM films WHERE film_id = ?;";
        try {
            return jdbcTemplate.queryForObject(query, (resultSet, rowNum) -> mapFilm(resultSet), filmId);
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException("Film not found");
        }
    }

    @Override
    public Film updateFilm(Film film) {
        Set<Integer> genreArrayJava = film.getGenres() != null ? film.getGenres().stream().map(Genre::getId).collect(Collectors.toSet()) : Collections.emptySet();
        jdbcTemplate.execute((Connection connection) -> jdbcTemplate.update("UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, mpa_rating_id = ?, genre = ? WHERE film_id = ?;", film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), connection.createArrayOf("INTEGER", genreArrayJava.toArray()), film.getId()));
        String query = "SELECT * FROM films WHERE film_id = ?;";
        try {
            return jdbcTemplate.queryForObject(query, (resultSet, rowNum) -> mapFilm(resultSet), film.getId());
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException("Film not found");
        }
    }

    @Override
    public void deleteFilm(int filmId) {
        String query = "DELETE FROM films WHERE film_id = ?;";
        jdbcTemplate.update(query, filmId);
    }
    @Override
    public Stream<Film> getAllFilms() {
        String query = "SELECT * FROM films;";
        return jdbcTemplate.query(query, (resultSet, rowNum) -> mapFilm(resultSet)).stream();
    }
    @Override
    public void addLike(int filmId, int userId) {
        String query = "INSERT INTO film_likes(film_id, user_id) VALUES (?, ?);";
        jdbcTemplate.update(query, filmId, userId);
    }
    @Override
    public void deleteLike(int filmId, int userId) {
        String query = "DELETE FROM film_likes WHERE film_id = ? AND user_id = ?;";
        if (jdbcTemplate.update(query, filmId, userId) == 0) {
            throw new NotFoundException("User not found");
        }
    }
    @Override
    public Stream<Integer> getLikes(int filmId) {
        String query = "SELECT user_id FROM film_likes WHERE film_id = " + filmId + ";";
        return jdbcTemplate.query(query, (resultSet, rowNum) -> resultSet.getInt("user_id")).stream();
    }

    private Film mapFilm(ResultSet resultSet) throws SQLException {
        List<Genre> genre;
        if (resultSet.getArray("genre") != null) {
            Array genreArray = resultSet.getArray("genre");
            Object[] objectArray = (Object[]) genreArray.getArray();
            genre = resultSet.getObject("genre") == null ? Collections.emptyList() : Arrays.stream(objectArray).map(genreId -> genreStorage.getGenre((Integer) genreId)).collect(Collectors.toList());
        } else {
            genre = Collections.emptyList();
        }
        return Film.builder()
                .id(resultSet.getInt("film_id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getObject("release_date", LocalDate.class))
                .duration(resultSet.getInt("duration"))
                .mpa(mpaStorage.getMpa(resultSet.getInt("mpa_rating_id")))
                .genres(genre)
                .build();
    }
}
