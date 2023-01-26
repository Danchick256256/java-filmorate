package ru.yandex.practicum.filmorate.storage.user.Dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Set;
import java.util.stream.Stream;

@Component
@Qualifier("UserDao")
public class UserDao implements UserStorage {
    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public Stream<User> getAllUsers() {
        return null;
    }

    @Override
    public void saveFriend(int id, Set<Integer> likes) {

    }

    @Override
    public Stream<Integer> getFriends(int id) {
        return null;
    }
}
