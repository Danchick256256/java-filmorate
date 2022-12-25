package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
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
            return new ResponseEntity<>(user, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAll() {
        log.info("GET request, get all USER");
        return new ResponseEntity<>(new ArrayList<>(users.values()), HttpStatus.OK);
    }
}
