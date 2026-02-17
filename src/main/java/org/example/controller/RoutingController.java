package org.example.controller;

import jakarta.validation.constraints.NotBlank;
import org.example.controller.model.RoutingResponse;
import org.example.service.CountriesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/routing")
public class RoutingController {
    private final CountriesService countriesService;

    public RoutingController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @GetMapping("/{origin}/{destination}")
    public RoutingResponse findRoute(
            @PathVariable @NotBlank(message = "Origin is required") String origin,
            @PathVariable @NotBlank(message = "Destination is required") String destination
    ) {
        return new RoutingResponse(countriesService.findRoute(origin, destination));
    }
}
