package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.stream.Stream;

public interface GenreStorage {
    Genre getGenre(int id);
    Stream<Genre> getGenreList();
}
