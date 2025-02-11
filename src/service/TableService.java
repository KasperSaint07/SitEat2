package service;

import model.Table;
import repositories.TableRepository;
import repositories.interfaces.ITableRepository;
import service.interfaces.ITableService;

import java.util.List;

public class TableService implements ITableService {
    private final ITableRepository tableRepository;
    public TableService(ITableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }
    @Override
    public List<Table> getAvailableTables(int restaurantId) {
        return tableRepository.getAvailableTables(restaurantId);
    }
    @Override
    public boolean reserveTable(int tableId) {
        return tableRepository.updateTableAvailability(tableId, false);
    }
    @Override
    public boolean releaseTable(int tableId) {
        return tableRepository.updateTableAvailability(tableId, true);
    }
    @Override
    public boolean addTable(int restaurantId) {
        return tableRepository.addTable(restaurantId);
    }
    @Override
    public boolean removeTable(int tableId) {
        return tableRepository.removeTable(tableId);
    }
    @Override
    public int getRestaurantIdByTable(int tableId) {
        return tableRepository.getRestaurantIdByTable(tableId);
    }
    @Override
    public List<Table> getTablesByRestaurant(int restaurantId) {
        return tableRepository.getTablesByRestaurant(restaurantId);
    }
}


