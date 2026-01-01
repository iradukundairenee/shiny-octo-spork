package com.fuel.tracking.repository;

import org.springframework.stereotype.Repository;
import com.fuel.tracking.model.Car;
import java.util.*;

@Repository
public class CarRepository {
	private final Map<Long, Car> carMap = new HashMap<>();
	private long nextId = 1;

	public synchronized Car save(Car car) {
		if (car.getId() == null) {
			car.setId(nextId++);
		}
		carMap.put(car.getId(), car);
		return car;
	}

	public List<Car> findAll() {
		return new ArrayList<>(carMap.values());
	}

	public Optional<Car> findById(Long id) {
		return Optional.ofNullable(carMap.get(id));
	}
}
