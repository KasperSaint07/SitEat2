package model;

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

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getRestaurantId() { return restaurantId; }
}