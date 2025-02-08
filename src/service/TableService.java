package service;

import model.Table;
import repositories.TableRepository;
import repositories.interfaces.ITableRepository;

import java.util.List;

public class TableService {
    private final ITableRepository tableRepository;

    public TableService(ITableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public List<Table> getAvailableTables(int restaurantId) {
        return tableRepository.getAvailableTables(restaurantId);
    }

    public boolean reserveTable(int tableId) {
        return tableRepository.updateTableAvailability(tableId, false);
    }

    public boolean releaseTable(int tableId) {
        return tableRepository.updateTableAvailability(tableId, true);
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


