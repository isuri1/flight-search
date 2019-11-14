package it.isuri.flightsearch.service;

import it.isuri.flightsearch.model.FlightEntity;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface FlightSearchService {

    Page<FlightEntity> searchFlights(String orderBy, String direction, int page, int size, String departure, String arrival, LocalDate date);
}
