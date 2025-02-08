package repositories.interfaces;
import java.util.List;
import model.Restaurant;

public interface IRestaurantRepository {
    List<Restaurant> getAllRestaurants();
    Restaurant getRestaurantById(int Id);
}
