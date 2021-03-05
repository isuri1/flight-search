package it.isuri.flightsearch.client;

import it.isuri.flightsearch.model.BusinessFlight;
import it.isuri.flightsearch.model.CheapFlight;
import it.isuri.flightsearch.model.ExtSearchResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

//test5
@FeignClient(name = "flightSearchClient", url = "${flight.search.url}")
public interface FlightSearchClient {

    @GetMapping(
            value = {"${flight.search.cheap}"},
            produces = {"application/json"}
    )
    ResponseEntity<ExtSearchResult<CheapFlight>> getCheapFlights();

    @GetMapping(
            value = {"${flight.search.business}"},
            produces = {"application/json"}
    )
    ResponseEntity<ExtSearchResult<BusinessFlight>> getBusinessFlights();
}
