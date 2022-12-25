package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class UsersTest {
    private final UserController userController = new UserController();

    @Test
    void createUser() {
        User user = new User(1, "test@gmail.com", "testLogin", "testName", LocalDate.parse("1995-12-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        ResponseEntity<User> createdUser = userController.create(user);
        assertEquals(createdUser, new ResponseEntity<>(user, HttpStatus.CREATED));
    }

    @Test
    void failedLogin() {
        User user = new User(1, "test@gmail.com", "", "testName", LocalDate.parse("1995-12-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        ValidationException validationException = assertThrows(
                ValidationException.class,
                () -> userController.create(user));
        assertEquals("{wrong.login}", validationException.getMessage());
    }

    @Test
    void failedEmail() {
        User user = new User(1, "mail", "login", "testName", LocalDate.parse("1995-12-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        ValidationException validationException = assertThrows(
                ValidationException.class,
                () -> userController.create(user));
        assertEquals("{wrong.email}", validationException.getMessage());
    }

    @Test
    void failedBirthday() {
        User user = new User(1, "test@mail.ru", "login", "testName", LocalDate.parse("3995-12-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        ValidationException validationException = assertThrows(
                ValidationException.class,
                () -> userController.create(user));
        assertEquals("{birthday.is.after.now}", validationException.getMessage());
    }

    @Test
    void updateUnknownUser() {
        User user = new User(1, "test@gmail.com", "testLogin", "testName", LocalDate.parse("1995-12-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        userController.create(user);
        User updatedUser = new User(11, "new@gmail.com", "testLogin", "testName", LocalDate.parse("1995-12-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        ValidationException validationException = assertThrows(
                ValidationException.class,
                () -> userController.update(updatedUser));
        assertEquals("{unknown.user}", validationException.getMessage());
    }

    @Test
    void createUserWithEmptyName() {
        User user = new User(1, "test@gmail.com", "testLogin", "", LocalDate.parse("1995-12-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        ResponseEntity<User> createdUser = userController.create(user);
        assertEquals(Objects.requireNonNull(createdUser.getBody()).getName(), user.getLogin());
    }

    @Test
    void getAll() {
        User user = new User(10, "test@gmail.com", "testLogin", "testName", LocalDate.parse("1995-12-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        userController.create(user);
        ResponseEntity<List<User>> createdUser = userController.getAll();
        List<User> userList = new ArrayList<>();
        userList.add(user);
        assertEquals(createdUser, new ResponseEntity<>(userList, HttpStatus.OK));
    }
}
