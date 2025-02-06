package model;

import lombok.Getter;

@Getter
public class Admin {
    private int id;
    private String username;
    private String password;
    private int restaurantId;

    public Admin(int id, String username, String password, int restaurantId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.restaurantId = restaurantId;
    }

}