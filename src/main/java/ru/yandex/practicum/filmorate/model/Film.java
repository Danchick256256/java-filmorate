package ru.yandex.practicum.filmorate.model;


import lombok.*;
import org.aspectj.lang.annotation.After;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@Entity
public class Film {
    @Getter
    @Setter
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