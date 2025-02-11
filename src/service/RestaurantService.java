package service;

import model.Restaurant;
import repositories.RestaurantRepository;
import repositories.interfaces.IRestaurantRepository;
import service.interfaces.IRestaurantService;

import java.util.List;

public class RestaurantService implements IRestaurantService {
    private final IRestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }
    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.getAllRestaurants();
    }
    @Override
    public Restaurant getRestaurantById(int id) {
        return restaurantRepository.getRestaurantById(id);
    }
}

