package service;

import model.Restaurant;
import repositories.RestaurantRepository;

import java.util.List;

public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.getAllRestaurants();
    }

    public boolean addRestaurant(String name, String location) {
        if (name == null || name.isEmpty() || location == null || location.isEmpty()) {
            return false;
        }
        return restaurantRepository.addRestaurant(name, location);
    }

    public boolean deleteRestaurant(int restaurantId) {
        return restaurantRepository.deleteRestaurant(restaurantId);
    }

    public Restaurant getRestaurantById(int id) {
        return restaurantRepository.getRestaurantById(id);
    }
}
