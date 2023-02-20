package ru.yandex.practicum.filmorate.storage.genre.Dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;

@Slf4j
@Component
@Qualifier("GenreDao")
@Primary
public class GenreDao implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre getGenre(int id) {
        String query = "SELECT * FROM genres WHERE genre_id = ?;";
        try {
            return jdbcTemplate.queryForObject(query, (resultSet, rowNum) -> mapGenre(resultSet), id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException("MPA not found");
        }
    }

    @Override
    public Stream<Genre> getGenreList() {
        String query = "SELECT * FROM genres;";
        return jdbcTemplate.query(query, (resultSet, rowNum) -> mapGenre(resultSet)).stream();
    }

    private Genre mapGenre(ResultSet resultSet) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("genre_id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
