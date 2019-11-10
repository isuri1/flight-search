package it.isuri.flightsearch.service;

import it.isuri.flightsearch.client.FlightSearchClient;
import it.isuri.flightsearch.model.ExtSearchResult;
import it.isuri.flightsearch.model.Flight;
import it.isuri.flightsearch.model.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class FlightSearchServiceImpl implements FlightSearchService {

    private final FlightSearchClient flightSearchClient;

    @Override
    public SearchResult searchFlights(String departure, String arrival, LocalDate date) {

        ResponseEntity<ExtSearchResult> cheapFlights = flightSearchClient.getCheapFlights();
        System.out.println("***** Flights :  " + cheapFlights.getBody().getData());

        Flight flight = new Flight();
        flight.setDeparture("Cruz del Eje");
        flight.setArrival("Antalya");
        flight.setFrom(LocalDateTime.now());
        flight.setTo(LocalDateTime.now());
        SearchResult searchResult = new SearchResult();
        searchResult.setFlightList(Collections.singletonList(flight));
        return searchResult;
    }
}
