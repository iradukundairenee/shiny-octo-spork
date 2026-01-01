


package com.fuel.tracking.repository;

import com.fuel.tracking.model.FuelEntry;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class FuelEntryRepository {
    private final Map<Long, List<FuelEntry>> fuelEntriesByCar = new HashMap<>();

    public synchronized void addFuelEntry(Long carId, FuelEntry entry) {
        fuelEntriesByCar.computeIfAbsent(carId, k -> new ArrayList<>()).add(entry);
    }

    public List<FuelEntry> getFuelEntries(Long carId) {
        return fuelEntriesByCar.getOrDefault(carId, Collections.emptyList());
    }
}