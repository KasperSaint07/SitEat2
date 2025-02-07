package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Restaurant {
    private int id;
    private String name;
    private String location;
    private String category;  // новое поле для категории

    public Restaurant(int id, String name, String location, String category) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.category = category;
    }
}
