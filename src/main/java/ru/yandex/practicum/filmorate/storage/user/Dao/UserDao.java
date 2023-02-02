package ru.yandex.practicum.filmorate.storage.user.Dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@Component
@Qualifier("UserDao")
@Primary
public class UserDao implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User createUser(User user) {
        String query = "INSERT INTO users(email, login, name, birthday) VALUES (?, ?, ?, ?);";
        jdbcTemplate.update(query, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        query = "SELECT * FROM users WHERE user_id = ?;";
        return jdbcTemplate.queryForObject(query, (resultSet, rowNum) -> mapUser(resultSet), user.getId());
    }

    @Override
    public User getUser(int id) {
        String query = "SELECT * FROM users WHERE user_id = ?;";
        try {
            return jdbcTemplate.queryForObject(query, (resultSet, rowNum) -> mapUser(resultSet), id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException("User not found");
        }
    }

    @Override
    public User updateUser(User user) {
        String query = "UPDATE users SET email = ?, login = ?, name = ?, birthday = ? WHERE user_id = ?;";
        jdbcTemplate.update(query, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user .getId());
        query = "SELECT * FROM users WHERE user_id = ?;";
        try {
            return jdbcTemplate.queryForObject(query, (resultSet, rowNum) -> mapUser(resultSet), user.getId());
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException("User not found");
        }
    }

    @Override
    public void deleteUser(int id) {
        String query = "DELETE FROM users WHERE user_id = ?;";
        jdbcTemplate.update(query, id);
    }

    @Override
    public Stream<User> getAllUsers() {
        String query = "SELECT * FROM users;";
        return jdbcTemplate.query(query, (resultSet, rowNum) -> mapUser(resultSet)).stream();
    }

    @Override
    public void addFriend(int id, int friendId) {
        String query = "INSERT INTO user_friends(user_id, friend_id) VALUES (?, ?);";
        try {
            jdbcTemplate.update(query, id, friendId);
        } catch (Exception exception) {
            throw new NotFoundException("User not found");
        }
    }

    @Override
    public Stream<Integer> getFriends(int id) {
        String query = "SELECT friend_id FROM user_friends WHERE user_id = ?;";
        return jdbcTemplate.queryForList(query, id).stream()
                .map(value -> Integer.parseInt(value.toString().replaceAll("[^0-9]", "")));
    }

    public void deleteFriends(int userId, int friendId) {
        String query = "DELETE FROM user_friends WHERE user_id = ? AND friend_id = ?;";
        jdbcTemplate.update(query, userId, friendId);
    }

    private User mapUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("user_id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getObject("birthday", LocalDate.class))
                .build();
    }
}
