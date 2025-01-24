package model;
//User in system
public class User {
    private int id;           // ID пользователя
    private String login;     // Логин пользователя
    private String password;  // Пароль пользователя
    private String name;      // Имя пользователя
    private String surname;   // Фамилия пользователя
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

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
}
