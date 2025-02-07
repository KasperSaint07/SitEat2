package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Restaurant {
    private int id;            // ID ресторана
    private String name;       // Название ресторана
    private String location;   // Локация ресторана
    private String category;   // Категория ресторана

    public Restaurant(int id, String name, String location, String category) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.category = category;
    }
}
