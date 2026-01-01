package com.fuel.tracking.service;

import com.fuel.tracking.model.Car;
import com.fuel.tracking.model.FuelEntry;
import com.fuel.tracking.repository.CarRepository;
import com.fuel.tracking.repository.FuelEntryRepository;
import com.fuel.tracking.dto.CarResponse;
import com.fuel.tracking.dto.CreateCarRequest;
import com.fuel.tracking.mapper.CarMapper;
import com.fuel.tracking.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private FuelEntryRepository fuelEntryRepository;

    public List<CarResponse> getAllCars() {
        return carRepository.findAll().stream()
                .map(CarMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CarResponse getCarById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        return CarMapper.toResponse(car);
    }

    public CarResponse saveCar(CreateCarRequest request) {
        // Validate required fields
        if (request.getBrand() == null || request.getBrand().isEmpty() ||
            request.getModel() == null || request.getModel().isEmpty() ||
            request.getYear() == 0) {
            throw new IllegalArgumentException("brand, model, and year are required");
        }
        Car car = CarMapper.fromCreateRequest(request);
        Car saved = carRepository.save(car);
        return CarMapper.toResponse(saved);
    }


    public FuelEntry addFuelEntryAndReturn(Long carId, double liters, double price, long odometer) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + carId));
        FuelEntry entry = new FuelEntry(liters, price, odometer);
        fuelEntryRepository.addFuelEntry(carId, entry);
        return entry;
    }

    public List<FuelEntry> getFuelEntries(Long carId) {
        carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + carId));
        return fuelEntryRepository.getFuelEntries(carId);
    }

    public FuelStats getFuelStats(Long carId) {
        List<FuelEntry> entries = getFuelEntries(carId);
        double totalFuel = entries.stream().mapToDouble(FuelEntry::getLiters).sum();
        double totalCost = entries.stream().mapToDouble(FuelEntry::getPrice).sum();
        double avgConsumption = 0.0;
        if (entries.size() > 1) {
            long minOdo = entries.stream().mapToLong(FuelEntry::getOdometer).min().orElse(0);
            long maxOdo = entries.stream().mapToLong(FuelEntry::getOdometer).max().orElse(0);
            long distance = maxOdo - minOdo;
            if (distance > 0) {
                avgConsumption = (totalFuel / distance) * 100.0;
            }
        }
        return new FuelStats(totalFuel, totalCost, avgConsumption);
    }

    public static class FuelStats {
        private double totalFuel;
        private double totalCost;
        private double avgConsumption;

        public FuelStats(double totalFuel, double totalCost, double avgConsumption) {
            this.totalFuel = totalFuel;
            this.totalCost = totalCost;
            this.avgConsumption = avgConsumption;
        }
        public double getTotalFuel() { return totalFuel; }
        public double getTotalCost() { return totalCost; }
        public double getAvgConsumption() { return avgConsumption; }
    }
}