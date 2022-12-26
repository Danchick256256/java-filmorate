package ru.yandex.practicum.filmorate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Constants {
    public static final LocalDate earliestFilmDate = LocalDate.parse("1895-12-28", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
}
