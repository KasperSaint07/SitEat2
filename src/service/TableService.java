package service;

import model.Table;
import repositories.TableRepository;

import java.util.List;

public class TableService {
    private final TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public List<Table> getAvailableTables(int restaurantId) {
        return tableRepository.getAvailableTables(restaurantId);
    }

    public boolean reserveTable(int tableId, boolean reserve) {
        tableRepository.updateTableAvailability(tableId, false);
        return reserve;
    }

    public boolean releaseTable(int tableId, boolean release) {
        tableRepository.updateTableAvailability(tableId, true);
        return release;
    }

    public boolean addTable(int restaurantId) {
        return tableRepository.addTable(restaurantId);
    }

    public boolean removeTable(int tableId) {
        return tableRepository.removeTable(tableId);
    }

    public int getRestaurantIdByTable(int tableId) {
        return tableRepository.getRestaurantIdByTable(tableId);
    }

    public List<Table> getTablesByRestaurant(int restaurantId) {
        return tableRepository.getTablesByRestaurant(restaurantId);
    }
}
