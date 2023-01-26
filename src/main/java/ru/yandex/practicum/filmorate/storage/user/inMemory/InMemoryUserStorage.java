package ru.yandex.practicum.filmorate.storage.user.inMemory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Stream;

@Slf4j
@Component
@Qualifier("UserInMemory")
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users;
    private final Map<Integer, Set<Integer>> friends;

    public InMemoryUserStorage() {
        users = new HashMap<>();
        friends = new HashMap<>();
    }

    @Override
    public User createUser(User user) {
        int userId = user.getId();
        users.put(userId, user);
        return users.get(userId);
    }
    @Override
    public User getUser(int id) {
        Optional<User> user = Optional.ofNullable(users.get(id));
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NotFoundException("User not found");
        }
    }
    @Override
    public void deleteUser(int id) {
        users.remove(id);
    }
    @Override
    public User updateUser(User user) {
        int userId = user.getId();
        if (users.containsKey(userId)) {
            users.replace(userId, user);
            return users.get(userId);
        } else {
            throw new NotFoundException("User not found");
        }
    }
    @Override
    public Stream<User> getAllUsers() {
        return users.values().stream();
    }
    @Override
    public void saveFriend(int id, Set<Integer> likes) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("User not found");
        }
        friends.put(id, likes);
    }
    @Override
    public Stream<Integer> getFriends(int id) {
        return friends.get(id) == null ? Stream.empty() : friends.get(id).stream();
    }
}
