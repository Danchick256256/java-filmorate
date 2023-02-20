package ru.yandex.practicum.filmorate.model;


import lombok.*;

import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
public class Film {
    @Getter
    @Setter
    @Id
    private int id;
    @Getter
    @Setter
    @NotBlank(message = "Film Name is Mandatory")
    @NotNull(message = "Film Name is Mandatory")
    private String name;
    @Getter
    @Setter
    @Size(max = 200, message = "Description Size is Too Long")
    private String description;
    @Getter
    @Setter
    @Positive(message = "Film Duration should be Positive")
    private int duration;
    @Getter
    @Setter
    @Past(message = "Film ReleaseDate should be Past")
    private LocalDate releaseDate;
    @Getter
    @Setter
    @NotNull
    private MPA mpa;
    @Getter
    @Setter
    private List<Genre> genres;
}