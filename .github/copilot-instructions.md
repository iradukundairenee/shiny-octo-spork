# Copilot Instructions for Car Management & Fuel Tracking System

## Project Overview
- **Architecture:**
  - Monorepo with two main components:
    - `backend/`: Spring Boot REST API (in-memory storage, manual servlet)
    - `cli/`: Standalone Java CLI client (uses `HttpClient` to call backend)
- **Data Flow:**
  - CLI → REST API endpoints (and servlet for stats)
  - Controller → Service → Repository (backend)
  - Servlet → Service (backend)

## Key Workflows
- **Build & Run:**
  - Backend: `cd backend && mvn spring-boot:run`
  - CLI: `./run-cli.sh [arguments]` (builds and runs CLI)
- **Testing:**
  - No explicit test suite detected; focus on manual API/CLI validation.
- **Debugging:**
  - Backend errors handled via `GlobalExceptionHandler` (see `handler/`)
  - 400/404 responses for invalid input/IDs

## REST API Endpoints
- `POST   /api/cars` — Create car
- `GET    /api/cars` — List cars
- `POST   /api/cars/{id}/fuel` — Add fuel entry
- `GET    /api/cars/{id}/fuel/stats` — Get fuel stats
- **Servlet:** `GET /servlet/fuel-stats?carId={id}` — Fuel stats (raw servlet)

## Project-Specific Patterns
- **In-memory storage:** All data in Java Maps/Lists (no DB)
- **DTOs:** Use `dto/` for request/response objects
- **Manual servlet:** `FuelStatsServlet` (not Spring)
- **Error handling:** Centralized in `handler/GlobalExceptionHandler.java`
- **Repository pattern:** `repository/` for data access
- **Mapping:** `mapper/CarMapper.java` for DTO/model conversion

## Integration Points
- **CLI → Backend:** Uses HTTP calls (see `cli/src/main/java/com/codehills/cli/Main.java`)
- **Servlet:** Demonstrates manual Java Servlet skills (see `servlet/FuelStatsServlet.java`)

## Conventions
- No database; all data lost on backend restart
- Robust error handling (user-friendly messages)
- Example CLI output in README

## References
- See `README.md` for usage, commands, and example flows
- Key backend files: `controller/CarController.java`, `service/CarService.java`, `repository/CarRepository.java`, `servlet/FuelStatsServlet.java`, `handler/GlobalExceptionHandler.java`
- Key CLI file: `cli/src/main/java/com/codehills/cli/Main.java`

---
_If unclear about a workflow or pattern, check README.md and referenced source files for concrete examples._
