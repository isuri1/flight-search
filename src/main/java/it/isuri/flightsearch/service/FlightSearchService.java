package it.isuri.flightsearch.service;

import it.isuri.flightsearch.model.SearchResult;

import java.time.LocalDate;

public interface FlightSearchService {

    SearchResult searchFlights(String departure, String arrival, LocalDate date);
}
