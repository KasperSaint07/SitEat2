package service;

import model.Restaurant;

import java.util.ArrayList;
import java.util.List;


public class RestaurantService {
    private List<Restaurant> restaurants = new ArrayList<>();

    public RestaurantService() {
        // Добавим тестовые рестораны
        restaurants.add(new Restaurant(1, "Astana Restaurant", "Astana Center"));
        restaurants.add(new Restaurant(2, "Almaty Diner", "Almaty Downtown"));
    }
//конструктор
    public List<Restaurant> getAllRestaurants() {
        return restaurants;
    }

    public Restaurant getRestaurantById(int id) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getId() == id) {
                return restaurant;
            }
        }
        return null; // Если ресторан не найден
    }
}
