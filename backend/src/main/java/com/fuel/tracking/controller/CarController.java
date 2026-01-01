package com.fuel.tracking.controller;

import com.fuel.tracking.dto.CarResponse;
import com.fuel.tracking.dto.CreateCarRequest;
import com.fuel.tracking.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllCars() {
        List<CarResponse> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable Long id) {
        CarResponse car = carService.getCarById(id);
        return ResponseEntity.ok(car);
    }

    @PostMapping
    public ResponseEntity<CarResponse> createCar(@RequestBody CreateCarRequest request) {
        CarResponse created = carService.saveCar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/{id}/fuel")
    public ResponseEntity<com.fuel.tracking.model.FuelEntry> addFuelEntry(@PathVariable Long id, @RequestParam double liters, @RequestParam double price, @RequestParam long odometer) {
        com.fuel.tracking.model.FuelEntry entry = carService.addFuelEntryAndReturn(id, liters, price, odometer);
        return ResponseEntity.status(HttpStatus.CREATED).body(entry);
    }

    @GetMapping("/{id}/fuel/stats")
    public ResponseEntity<CarService.FuelStats> getFuelStats(@PathVariable Long id) {
        CarService.FuelStats stats = carService.getFuelStats(id);
        return ResponseEntity.ok(stats);
    }
}
