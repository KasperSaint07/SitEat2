package model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class User {
    private int id;

    @NotNull(message = "Login cannot be empty")
    @Size(min = 3, max = 20, message = "Login must contain from 3 to 20 characters")
    private String login;

    @NotNull(message = "Password cannot be empty")
    @Size(min = 3, message = "Password must contain from 3 to 20 characters")
    private String password;

    @NotNull(message = "Name cannot be empty")
    @Size(min = 3, max = 30, message = "Name must contain from 3 to 30 characters")
    private String name;

    @NotNull(message = "Surname cannot be empty")
    @Size(min = 3, max = 30, message = "Surname must contain from 3 to 30 characters")
    private String surname;

    @NotNull(message = "Gender cannot be empty")
    private boolean gender;
}