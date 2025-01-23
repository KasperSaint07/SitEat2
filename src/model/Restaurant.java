package model;

public class Restaurant {
    private int id; // ID ресторана
    private String name; // Название ресторана
    private String location; // Локация ресторана

    public Restaurant(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
}
