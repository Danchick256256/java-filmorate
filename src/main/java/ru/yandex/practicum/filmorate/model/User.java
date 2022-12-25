package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@Entity
public class User {
    private static int userId = 1;

    public static int generateId() {
        return userId++;
    }
    public User() {
        this.id = generateId();
    }

    @Getter
    @Id
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
