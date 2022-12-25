package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.GenerateUserId;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController("/users")
@Slf4j
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();

    @PostMapping("/users")
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        log.info("POST request, create USER");

        if (user.getName() == null || user.getName().equals("")) {
            user.setName(user.getLogin());
        }

        if (user.getLogin().isBlank()) {
            log.error("Wrong Login");
            throw new ValidationException("{wrong.login}");
        }

        if (!user.getEmail().contains("@") || !user.getEmail().contains(".")) {
            log.error("Wrong email");
            throw new ValidationException("{wrong.email}");
        }

        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Birthday is not past");
            throw new ValidationException("{birthday.is.after.now}");
        }

        user.setId(GenerateUserId.generateId());
        users.put(user.getId(), user);
        log.info("USER created");
        return new ResponseEntity<>(users.get(user.getId()), HttpStatus.CREATED);
    }

    @PutMapping("/users")
    public ResponseEntity<User> update(@Valid @RequestBody User user) {
        log.info("PUT request, update USER");

        if (user.getName() == null || user.getName().equals("")) {
            user.setName(user.getLogin());
        }

        if(users.containsKey(user.getId())){
            users.replace(user.getId(), user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            log.error("Unknown USER");
            throw new ValidationException("{unknown.user}");
        }

    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAll() {
        log.info("GET request, get all USER");
        return new ResponseEntity<>(new ArrayList<>(users.values()), HttpStatus.OK);
    }
}
