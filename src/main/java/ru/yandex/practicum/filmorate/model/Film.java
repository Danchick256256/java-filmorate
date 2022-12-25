package ru.yandex.practicum.filmorate.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@Entity
public class Film {
    private static int filmId = 1;

    public static int generateId() {
        return filmId++;
    }
    public Film() {
        this.id = generateId();
    }

    @Getter
    @Id
    private int id;
    @NotBlank(message = "Film Name is Mandatory")
    @NotNull(message = "Film Name is Mandatory")
    private String name;
    @Size(max = 200, message = "Description Size is Too Long")
    private String description;
    @Positive(message = "Film Duration should be Positive")
    private int duration;
    @Past(message = "Film ReleaseDate should be Past")
    private LocalDate releaseDate;
}