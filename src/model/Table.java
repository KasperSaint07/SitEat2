package model;

/**
 * Represents a table in a restaurant.
 */
public class Table {
    private int id;              // ID столика
    private int restaurantId;    // ID ресторана, к которому относится столик
    private boolean isAvailable; // Доступность столика

    public Table(int id, int restaurantId, boolean isAvailable) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.isAvailable = isAvailable;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
