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
}
