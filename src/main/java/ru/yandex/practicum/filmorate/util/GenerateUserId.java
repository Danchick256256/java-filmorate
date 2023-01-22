package ru.yandex.practicum.filmorate.util;

public class GenerateUserId {
    private static int userId = 1;

    public static int generateId() {
        return userId++;
    }

}
