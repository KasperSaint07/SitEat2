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

    public void addRestaurant() {
        System.out.print("Enter restaurant name: ");
        String name = scanner.nextLine();
        System.out.print("Enter restaurant location: ");
        String location = scanner.nextLine();

        if (restaurantService.addRestaurant(name, location)) {
            System.out.println("Restaurant added successfully.");
        } else {
            System.out.println("Failed to add restaurant.");
        }
    }

    public void deleteRestaurant() {
        System.out.print("Enter restaurant ID to delete: ");
        int restaurantId = scanner.nextInt();
        scanner.nextLine();

        if (restaurantService.deleteRestaurant(restaurantId)) {
            System.out.println("Restaurant deleted successfully.");
        } else {
            System.out.println("Failed to delete restaurant.");
        }
    }
}