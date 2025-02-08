package repositories.interfaces;

import model.Table;
import java.util.List;

public interface ITableRepository {
    List<Table> getAvailableTables(int restaurantId);
    boolean isTableAvailable(int tableId);
    boolean updateTableAvailability(int tableId, boolean isAvailable);
    List<Table> getTablesByRestaurant(int restaurantId);
    boolean addTable(int restaurantId);
    boolean removeTable(int tableId);
    int getRestaurantIdByTable(int tableId);
}
