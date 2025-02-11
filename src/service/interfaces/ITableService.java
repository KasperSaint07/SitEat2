package service.interfaces;
import java.util.List;

import model.Table;
import service.TableService;

public interface ITableService {
    List<Table> getAvailableTables(int restaurantId);
    boolean reserveTable(int tableId);
    boolean releaseTable(int tableId);
    boolean addTable(int restaurantId);
    boolean removeTable(int tableId);
    int getRestaurantIdByTable(int tableId);
    List<Table> getTablesByRestaurant(int restaurantId);

}
