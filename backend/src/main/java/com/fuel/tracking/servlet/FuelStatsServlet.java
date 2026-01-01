
package com.fuel.tracking.servlet;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuel.tracking.service.CarService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@WebServlet(name = "FuelStatsServlet", urlPatterns = {"/servlet/fuel-stats"})
public class FuelStatsServlet extends HttpServlet {
    @Autowired
    private CarService carService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String carIdStr = req.getParameter("carId");
        resp.setContentType("application/json");
        if (carIdStr == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Missing carId\"}");
            return;
        }
        long carId;
        try {
            carId = Long.parseLong(carIdStr);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid carId\"}");
            return;
        }
        try {
            CarService.FuelStats stats = carService.getFuelStats(carId);
            Map<String, Object> result = new HashMap<>();
            result.put("totalFuel", stats.getTotalFuel());
            result.put("totalCost", stats.getTotalCost());
            result.put("avgConsumption", stats.getAvgConsumption());
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(objectMapper.writeValueAsString(result));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\":\"Car not found\"}");
        }
    }
}
