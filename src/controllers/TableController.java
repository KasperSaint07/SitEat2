package controllers;

import service.TableService;

import java.util.Scanner;

public class TableController {
    private final TableService tableService;
    private final Scanner scanner = new Scanner(System.in);

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    public void viewAvailableTables(int restaurantId) {
        System.out.println("Available tables:");
        tableService.getAvailableTables(restaurantId).forEach(table ->
                System.out.println("Table ID: " + table.getId())
        );
    }
}
