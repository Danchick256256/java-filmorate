package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.util.GenerateUserId;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController()
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        log.info("{POST.request.create.user}");

        if (user.getName() == null || user.getName().equals("")) {
            user.setName(user.getLogin());
        }

        user.setId(GenerateUserId.generateId());
        User createdUser = userService.createUser(user);
        log.info("{created.user}:{}", createdUser);
        return createdUser;
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@Valid @RequestBody User user) {
        log.info("{PUT.request.update.user}:{}", user.getId());

        if (user.getName() == null || user.getName().equals("")) {
            user.setName(user.getLogin());
        }

        userService.updateUser(user);
        return user;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable int id) {
        log.info("{GET.request.get.user.by.id}:{}", id);
        return userService.getUser(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        log.info("{GET.request.get.all.users}");
        return userService.getAllUsers();
    }

    @PutMapping("/{userId}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void addFriendsToEachOther(@PathVariable int userId, @PathVariable int friendId) {
        log.debug("{PUT.request.add.friends}");
        userService.addFriendsToEachOther(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFriendsToEachOther(@PathVariable int userId, @PathVariable int friendId) {
        log.debug("{DELETE.request.delete.friends}");
        userService.deleteFriendsToEachOther(userId, friendId);
    }

    @GetMapping("/{userId}/friends")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getFriends(@PathVariable int userId) {
        log.debug("{GET.request.get.user.friends}");
        return userService.getFriendsList(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getFriends(@PathVariable int userId, @PathVariable int otherId) {
        log.debug("{GET.request.get.user.common.friends}");
        return userService.getCommonFriends(userId, otherId);
    }
}
