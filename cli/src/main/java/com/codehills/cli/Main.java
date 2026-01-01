package com.codehills.cli;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    private static final String BASE_URL = "http://localhost:8080/api";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        System.out.println("Car Management CLI");
        while (true) {
            System.out.print("\nEnter command (create-car, add-fuel, fuel-stats, exit): ");
            String cmd = scanner.nextLine().trim();
            switch (cmd) {
                case "create-car":
                    createCar();
                    break;
                case "add-fuel":
                    addFuel();
                    break;
                case "fuel-stats":
                    fuelStats();
                    break;
                case "exit":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Unknown command.");
            }
        }
    }

    private static void createCar() {
        try {
            System.out.print("Brand: ");
            String brand = scanner.nextLine();
            System.out.print("Model: ");
            String model = scanner.nextLine();
            System.out.print("Year: ");
            int year = Integer.parseInt(scanner.nextLine());
            String json = String.format("{\"brand\":\"%s\",\"model\":\"%s\",\"year\":%d}", brand, model, year);
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/cars"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response: " + resp.body());
        } catch (NumberFormatException e) {
            System.out.println("Invalid year. Please enter a valid integer.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addFuel() {
        try {
            System.out.print("Car ID: ");
            int carId = Integer.parseInt(scanner.nextLine());
            System.out.print("Liters: ");
            double liters = Double.parseDouble(scanner.nextLine());
            System.out.print("Price: ");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.print("Odometer: ");
            double odometer = Double.parseDouble(scanner.nextLine());
            String url = String.format(BASE_URL + "/cars/%d/fuel?liters=%s&price=%s&odometer=%s", carId, Double.toString(liters), Double.toString(price), Long.toString((long) odometer));
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.body() == null || resp.body().isBlank()) {
                System.out.println("Response: Fuel entry added successfully.");
            } else {
                System.out.println("Response: " + resp.body());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid numbers.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void fuelStats() {
        try {
            System.out.print("Car ID: ");
            int carId = Integer.parseInt(scanner.nextLine());
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/cars/" + carId + "/fuel/stats"))
                    .GET()
                    .build();
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            System.out.println("Stats: " + resp.body());
        } catch (NumberFormatException e) {
            System.out.println("Invalid car ID. Please enter a valid integer.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
