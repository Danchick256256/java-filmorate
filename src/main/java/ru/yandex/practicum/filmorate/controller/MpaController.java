package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.mpa.MpaService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {
    private final MpaService mpaService;
    @GetMapping("/{id}")
    public MPA getMpa(@PathVariable int id) {
        log.info("GET request, get MPA");
        return mpaService.getMpa(id);
    }

    @GetMapping()
    public List<MPA> getAllMpa() {
        log.info("GET request, get all MPA");
        return mpaService.getAllMpa();
    }
}
