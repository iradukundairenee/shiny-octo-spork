# Car Management & Fuel Tracking System

## Overview
This project is a full-stack Java solution for car management and fuel tracking, designed as a technical assignment. It demonstrates:
- A Spring Boot backend with in-memory storage (no database)
- A manual Java Servlet for fuel stats
- A standalone Java CLI client using `HttpClient` to interact with the backend

---

## Project Structure

```
finalcar/
├── backend/   # Spring Boot backend (REST API, in-memory, servlet)
│   └── src/main/java/com/fuel/tracking/
│       ├── controller/   # REST controllers (CarController)
│       ├── service/      # Business logic (CarService)
│       ├── repository/   # In-memory repositories (CarRepository, FuelEntryRepository)
│       ├── model/        # Data models (Car, FuelEntry)
│       ├── dto/          # DTOs (CarResponse, CreateCarRequest)
│       ├── handler/      # Global exception handler
│       └── servlet/      # Manual servlet (FuelStatsServlet)
├── cli/       # Standalone Java CLI client
│   └── src/main/java/com/codehills/cli/Main.java
```

---

## Backend Features
- **In-memory storage**: All data is stored in Java Maps/Lists (no database)
- **REST API Endpoints:**
  - `POST   /api/cars`            - Create a car (brand, model, year required)
  - `GET    /api/cars`            - List all cars
  - `POST   /api/cars/{id}/fuel`  - Add fuel entry (liters, price, odometer)
  - `GET    /api/cars/{id}/fuel/stats` - Get fuel stats for a car
- **Manual Servlet:**
  - `GET /servlet/fuel-stats?carId={id}` - Returns fuel stats as JSON (demonstrates raw servlet skills)
- **Error Handling:**
  - 400 Bad Request for missing/invalid input
  - 404 Not Found for invalid car IDs

---

## CLI Features
- **Standalone Java app** (no Spring, no database)
- Uses `java.net.http.HttpClient` to call backend endpoints
- **Commands:**
  - `create-car`   - Register a car
  - `add-fuel`     - Add fuel entry for a car
  - `fuel-stats`   - View calculated statistics for a car
- **Example usage:**
  ```sh
  java -jar target/car-management-cli-1.0-SNAPSHOT-jar-with-dependencies.jar
  # Then follow the prompts:
  # create-car, add-fuel, fuel-stats, exit
  ```

---

## How Components Interact
- **CLI** → **REST API** (and servlet for stats)
- **Controller** (routes) → **Service** (logic) → **Repository** (data)
- **Servlet** → **Service** (logic)

---

## How to Run
1. **Start the backend:**
   ```sh
   cd backend
   mvn spring-boot:run
   ```
2. **Build and run the CLI:**
   ```sh
   ./run-cli.sh [arguments]
   ```
3. **(Optional) Call the servlet directly:**
   ```sh
  curl "$BACKEND_URL/servlet/fuel-stats?carId=1"
   ```

---

## Running the CLI Client


To run the CLI client, use the provided script from the project root:


To run the CLI client, use the following command from the `cli/` directory:

```bash
../run-cli.sh [arguments]
```

This will build and execute the CLI application. You can pass any arguments required by the CLI.

---

## Notes
- All data is lost on backend restart (in-memory only)
- The servlet is included to demonstrate manual Java Servlet skills as required by the challenge
- Error handling is robust and user-friendly

---

## Example Output
```
Enter command (create-car, add-fuel, fuel-stats, exit): create-car
Brand: Toyota
Model: Corolla
Year: 2022
Response: {"id":1,"brand":"Toyota","model":"Corolla","year":2022}

Enter command (create-car, add-fuel, fuel-stats, exit): add-fuel
Car ID: 1
Liters: 40
Price: 52.5
Odometer: 45000
Response: ...

Enter command (create-car, add-fuel, fuel-stats, exit): fuel-stats
Car ID: 1
Stats: {"totalFuel":40.0,"totalCost":52.5,"avgConsumption":...}
```

---

## Author & Challenge Notes
- Built for a technical assignment
- Follows all challenge requirements (Spring Boot, in-memory, manual servlet, CLI, error handling)
- Ready for demonstration or further extension
