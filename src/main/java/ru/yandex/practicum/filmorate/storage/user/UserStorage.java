package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface UserStorage {
    User createUser(User user);
    User getUser(int id);
    User updateUser(User user);
    void deleteUser(int id);
    Stream<User> getAllUsers();
    void saveFriend(int id, int friendId);
    void deleteFriends(int userId, int friendId);
    Stream<Integer> getFriends(int id);
}
