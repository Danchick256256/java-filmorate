package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User createUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        User createdUser = userStorage.createUser(user);
        log.info("{user.created}:{}", user);
        return createdUser;
    }

    public User updateUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        User updatedUser = userStorage.updateUser(user);
        log.info("{user.updated}:{}", user);
        return updatedUser;
    }

    public List<User> getAllUsers() {
        List<User> users = userStorage.getAllUsers().collect(Collectors.toList());
        log.info("{get.all.users}:{}", users.size());
        return users;
    }

    public User getUser(int id) {
        return userStorage.getUser(id);
    }

    public void addFriendsToEachOther(int userId, int friendId) {
        addFriend(userId, friendId);
        addFriend(friendId, userId);
    }

    public void deleteFriendsToEachOther(int userId, int friendId) {
        deleteFriend(userId, friendId);
        deleteFriend(friendId, userId);
    }

    protected void addFriend(int userId, int friendId) {
        Set<Integer> likes = userStorage.getFriends(userId).collect(Collectors.toSet());
        likes.add(friendId);
        userStorage.saveFriend(userId, likes);
    }

    protected void deleteFriend(int userId, int friendId) {
        Set<Integer> likes = userStorage.getFriends(userId).collect(Collectors.toSet());
        likes.remove(friendId);
        userStorage.saveFriend(userId, likes);
    }

    public List<Integer> getFriends(int userId) {
        return userStorage.getFriends(userId).collect(Collectors.toList());
    }

    public List<User> getFriendsList(int userId) {
        Set<Integer> friends = userStorage.getFriends(userId).collect(Collectors.toSet());
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
