package com.fuel.tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fuel.tracking.servlet.FuelStatsServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    // Register the manual servlet for /servlet/fuel-stats
    @Bean
    public ServletRegistrationBean<FuelStatsServlet> fuelStatsServletRegistration(FuelStatsServlet servlet) {
        return new ServletRegistrationBean<>(servlet, "/servlet/fuel-stats");
    }
}
