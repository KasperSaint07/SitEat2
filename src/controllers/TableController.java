package controllers;

import service.TableService;

import java.util.Scanner;

public class TableController {
    private final TableService tableService;
    private final Scanner scanner = new Scanner(System.in);

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    public void viewAvailableTables() {
        System.out.print("\nEnter restaurant ID to view available tables: ");
        int restaurantId = getUserChoice();
        System.out.println("Available tables:");
        tableService.getAvailableTables(restaurantId).forEach(table ->
                System.out.println("Table ID: " + table.getId())
        );
    }
    private int getUserChoice() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Please enter a number: ");
            }
        }
    }
}
