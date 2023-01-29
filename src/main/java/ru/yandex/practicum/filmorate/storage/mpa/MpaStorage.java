package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.model.MPA;

import java.util.stream.Stream;

public interface MpaStorage {
    MPA getMpa(int id);
    Stream<MPA> getMpaList();
}
