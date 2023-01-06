package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage{
    private final Map<Integer, User> users;
    private final Map<Integer, Set<Integer>> friends;

    @Override
    public void createUser(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public User getUser(int id) {
        return users.get(id);
    }

    @Override
    public void deleteUser(int id) {
        users.remove(id);
    }

    @Override
    public void updateUser(User user) {
        users.replace(user.getId(), user);
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public boolean userContains(int id) {
        return users.containsKey(id);
    }

    public InMemoryUserStorage() {
        users = new HashMap<>();
        friends = new HashMap<>();
    }

    @Override
    public void saveFriend(int id, Set<Integer> likes) {
        friends.put(id, likes);
        log.info("{added.friends.to}:{}", id);
    }

    @Override
    public Optional<Set<Integer>> getFriends(int id) {
        return Optional.ofNullable(friends.get(id));
    }
}
