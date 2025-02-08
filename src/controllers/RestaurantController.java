package controllers;

import model.Restaurant;
import service.RestaurantService;

import java.util.List;
import java.util.Scanner;

public class RestaurantController {
    private final RestaurantService restaurantService;
    private final Scanner scanner = new Scanner(System.in);

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    public void viewRestaurants() {
        System.out.println("Available Restaurants:");
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        for (Restaurant restaurant : restaurants) {
            System.out.println("ID: " + restaurant.getId() + ", Name: " + restaurant.getName() + ", Location: " + restaurant.getLocation() + " (" + restaurant.getCategory() + ")");;
        }
    }
}