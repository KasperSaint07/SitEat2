package model;

import lombok.Getter;

@Getter
public class User {
    private final int id;
    private String login = "";
    private String password = "";
    private String name = "";
    private String surname = "";
//    private Boolean gender = Boolean.valueOf("");

    public User(int id, String login, String password, String name, String surname, boolean gender) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
//        this.gender = gender;
    }

    public User(int newId, String login, String password) {
        this.id = newId;
        this.login = login;
        this.password = password;
    }

    public String setPassword() {return ""; }
}