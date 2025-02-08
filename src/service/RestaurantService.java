package service;

import model.Restaurant;
import repositories.RestaurantRepository;
import repositories.interfaces.IRestaurantRepository;

import java.util.List;

public class RestaurantService {
    private final IRestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.getAllRestaurants();
    }

    public Restaurant getRestaurantById(int id) {
        return restaurantRepository.getRestaurantById(id);
    }
}

