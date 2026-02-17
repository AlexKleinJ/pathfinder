package org.example.service;

import org.example.exception.NoRouteException;
import org.example.model.Country;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

@Service
public class CountriesService {

    private final Map<String, List<String>> countries;

    public CountriesService(ObjectMapper objectMapper) {
        ClassPathResource resource = new ClassPathResource("countries.json");
        List<Country> countries;
        try (InputStream is = resource.getInputStream()) {
            countries = objectMapper.readValue(is, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        this.countries = countries.stream()
                .collect(toMap(Country::cca3, Country::borders));
    }

    public List<String> findRoute(String origin, String destination) {
        LinkedList<String> countriesToVisit = new LinkedList<>();
        Map<String, String> previousCountries = new HashMap<>();
        Set<String> visitedCountries = new HashSet<>();

        countriesToVisit.add(origin);
        visitedCountries.add(origin);

        while (!countriesToVisit.isEmpty()) {
            String current = countriesToVisit.removeFirst();

            if (current.equals(destination)) {
                return buildPath(previousCountries, destination);
            }

            for (String neighbor : countries.get(current)) {
                if (!visitedCountries.contains(neighbor)) {
                    visitedCountries.add(neighbor);
                    previousCountries.put(neighbor, current);
                    countriesToVisit.add(neighbor);
                }
            }
        }

        throw new NoRouteException();
    }

    private static List<String> buildPath(
            Map<String, String> previousCountries,
            String end) {
        List<String> path = new ArrayList<>();
        String current = end;

        while (current != null) {
            path.add(current);
            current = previousCountries.get(current);
        }

        Collections.reverse(path);
        return path;
    }
}