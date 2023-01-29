package ru.yandex.practicum.filmorate.storage.mpa.Dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;

@Slf4j
@Component
@Qualifier("MpaDao")
@Primary
public class MpaDao implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    public MpaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MPA getMpa(int id) {
        String query = "SELECT * FROM mpa_ratings WHERE mpa_rating_id = ?;";
        try {
            return jdbcTemplate.queryForObject(query, (resultSet, rowNum) -> mapMpa(resultSet), id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException("MPA not found");
        }
    }

    @Override
    public Stream<MPA> getMpaList() {
        String query = "SELECT * FROM mpa_ratings;";
        return jdbcTemplate.query(query, (resultSet, rowNum) -> mapMpa(resultSet)).stream();
    }

    private MPA mapMpa(ResultSet resultSet) throws SQLException {
        return MPA.builder()
                .id(resultSet.getInt("mpa_rating_id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .build();
    }
}
