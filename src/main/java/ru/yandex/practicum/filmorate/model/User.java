package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class User {
    private static int userId = 1;

    private static int generateId() {
        return userId++;
    }
    public User() {
        this.id = generateId();
    }

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Email(message = "Wrong Email")
    @NotBlank(message = "Email is Mandatory")
    @NotNull(message = "Email is Mandatory")
    private String email;
    @Setter
    @Getter
    @NotBlank(message = "Login is Mandatory")
    @NotNull(message = "Login is Mandatory")
    private String login;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    @Past(message = "Birthday should be past")
    private LocalDate birthday;
}
