package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage storage) {
        this.userStorage = storage;
    }

    public User createUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        userStorage.createUser(user);
        log.info("{user.created}:{}", user);
        return user;
    }

    public User updateUser(User user) {
        log.info(String.valueOf(userStorage.userContains(user.getId())));
        if (!userStorage.userContains(user.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        userStorage.updateUser(user);
        log.info("{user.updated}:{}", user);
        return user;
    }

    public List<User> getAllUsers() {
        ArrayList<User> users = userStorage.getAllUsers();
        log.info("{get.all.users}:{}", users.size());
        return users;
    }

    public User getUser(int id) {
        User user = userStorage.getUser(id);
        if (user == null) {
            log.info("{user.not.found}");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else {
            log.info("{get.user}:{}", user);
            return user;
        }
    }

    public void addFriendsToEachOther(int userId, int friendId) {
        if (!userStorage.userContains(userId) || !userStorage.userContains(friendId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        addFriend(userId, friendId);
        addFriend(friendId, userId);
    }

    public void deleteFriendsToEachOther(int userId, int friendId) {
        if (!userStorage.userContains(userId) || !userStorage.userContains(friendId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        deleteFriend(userId, friendId);
        deleteFriend(friendId, userId);
    }

    protected void addFriend(int userId, int friendId) {
        Set<Integer> likes = userStorage.getFriends(userId).orElseGet(HashSet::new);
        likes.add(friendId);
        userStorage.saveFriend(userId, likes);
    }

    protected void deleteFriend(int userId, int friendId) {
        Set<Integer> likes = userStorage.getFriends(userId).orElseGet(HashSet::new);
        likes.remove(friendId);
        userStorage.saveFriend(userId, likes);
    }

    public Optional<Set<Integer>> getFriends(int userId) {
        return userStorage.getFriends(userId);
    }

    public List<User> getFriendsList(int userId) {
        Set<Integer> friends = userStorage.getFriends(userId).orElseGet(HashSet::new);
        List<User> friendsList = new ArrayList<>();
        for (Integer id : friends) {
            friendsList.add(userStorage.getUser(id));
        }
        return friendsList;
    }

    public List<User> getCommonFriends(int userId, int friendId) {
        List<User> returnedList = new ArrayList<>();
        List<User> userFriends = getFriendsList(userId);
        List<User> friendFriends = getFriendsList(friendId);
        for (User user : userFriends) {
            for (User friend : friendFriends) {
                if (user == friend) {
                    returnedList.add(user);
                }
            }
        }
        return returnedList;
    }

}
