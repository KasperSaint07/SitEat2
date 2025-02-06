package model;

import lombok.Getter;
import lombok.Setter;

//Table info
public class Table {
    @Setter
    @Getter
    private int id;              // ID столика
    @Setter
    @Getter
    private int restaurantId;    // ID ресторана, к которому относится столик
    private boolean isAvailable; // Доступность столика

    public Table(int id, int restaurantId, boolean isAvailable) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.isAvailable = isAvailable;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
