package ru.yandex.practicum.filmorate.service.mpa;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MpaService {
    private final MpaStorage mpaStorage;

    public MPA getMpa(int id) {
        return mpaStorage.getMpa(id);
    }
    public List<MPA> getAllMpa() {
        return mpaStorage.getMpaList().collect(Collectors.toList());
    }
}
