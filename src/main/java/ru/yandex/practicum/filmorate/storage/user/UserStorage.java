package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

public interface UserStorage {
    void createUser(User user);
    Optional<User> getUser(int id);
    void deleteUser(int id);
    void updateUser(User user);
    ArrayList<User> getAllUsers();
    boolean userExisting(int id);
    void saveFriend(int id, Set<Integer> likes);
    Optional<Set<Integer>> getFriends(int id);
}
