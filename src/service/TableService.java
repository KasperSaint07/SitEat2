package service;

import model.Table;

import java.util.ArrayList;
import java.util.List;

public class TableService {
    private List<Table> tables = new ArrayList<>();

    public TableService() {
        // Добавим тестовые столики
        for (int i = 1; i <= 5; i++) {
            tables.add(new Table(i, 1, true)); // Столики для ресторана с ID 1
        }
        for (int i = 6; i <= 10; i++) {
            tables.add(new Table(i, 2, true)); // Столики для ресторана с ID 2
        }
    }

    public List<Table> getAvailableTables(int restaurantId) {
        List<Table> availableTables = new ArrayList<>();
        for (Table table : tables) {
            if (table.getRestaurantId() == restaurantId && table.isAvailable()) {
                availableTables.add(table);
            }
        }
        return availableTables;
    }

    public void reserveTable(int tableId) {
        for (Table table : tables) {
            if (table.getId() == tableId) {
                table.setAvailable(false);
                break;
            }
        }
    }
}
