package ru.yandex.practicum.filmorate.util;

public class GenerateFilmId {
    private static int filmId = 1;

    public static int generateId() {
        return filmId++;
    }

}
