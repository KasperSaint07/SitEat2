package model;

import lombok.Getter;
import lombok.Setter;

//User in system
@Getter
public class User {
    // Getters and Setters
    private int id;           // ID пользователя
    @Setter
    private String login;     // Логин пользователя
    @Setter
    private String password;  // Пароль пользователя
    @Setter
    private String name;      // Имя пользователя
    @Setter
    private String surname;   // Фамилия пользователя
    @Setter
    private boolean gender;   // Пол (true - мужской, false - женский)

    public User(int id, String login, String password, String name, String surname, boolean gender) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
    }

    public User(int id, String login, String password) {
        this(id, login, password, "", "", true);
    }
}
