package it.isuri.flightsearch.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.isuri.flightsearch.model.SearchResult;
import it.isuri.flightsearch.service.FlightSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@Api(tags = "Flight Search API", produces = MediaType.APPLICATION_JSON_VALUE)
public class FlightSearchController {

    private final FlightSearchService flightSearchService;

    @GetMapping(
            value = "/flight-search",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(value = "Search Flights", response = SearchResult.class)
    public ResponseEntity<SearchResult> addInstrument(@RequestParam(value = "departure", defaultValue = "Cruz del Eje", required = false) String departure,
                                                      @RequestParam(value = "arrival", defaultValue = "Antalya", required = false) String arrival,
                                                      @RequestParam(value = "date", defaultValue = "2019-11-11") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(flightSearchService.searchFlights(departure, arrival, date));
    }

}
