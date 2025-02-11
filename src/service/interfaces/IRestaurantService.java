package service.interfaces;

import model.Restaurant;

import java.util.List;

public interface IRestaurantService {
    List<Restaurant> getAllRestaurants();
    Restaurant getRestaurantById(int id);
}
