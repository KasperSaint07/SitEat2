package model;

public class Table {
    private int id;
    private int restaurantId;
    private boolean isAvailable;
    public Table(int id, int restaurantId, boolean isAvailable) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.isAvailable = isAvailable;    }
    public int getId() {
        return id;    }
    public int getRestaurantId() {
        return restaurantId;    }
    public boolean isAvailable() {
        return isAvailable;    }
    public void setAvailable(boolean available) {
        isAvailable = available;    }
}