package com.fuel.tracking.mapper;

import com.fuel.tracking.dto.CarResponse;
import com.fuel.tracking.dto.CreateCarRequest;
import com.fuel.tracking.model.Car;

public class CarMapper {
    public static CarResponse toResponse(Car car) {
        return new CarResponse(car.getId(), car.getBrand(), car.getModel(), car.getYear());
    }

    public static Car fromCreateRequest(CreateCarRequest req) {
        Car car = new Car();
        car.setBrand(req.getBrand());
        car.setModel(req.getModel());
        car.setYear(req.getYear());
        return car;
    }
}