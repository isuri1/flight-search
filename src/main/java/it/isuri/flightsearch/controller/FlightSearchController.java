package it.isuri.flightsearch.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.isuri.flightsearch.exception.PaginationSortingException;
import it.isuri.flightsearch.model.Direction;
import it.isuri.flightsearch.model.FlightEntity;
import it.isuri.flightsearch.model.OrderBy;
import it.isuri.flightsearch.service.FlightSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    @ApiOperation(value = "Search Flights", response = Page.class)
    public Page<FlightEntity> addInstrument(@ApiParam(value = "Departure") @Valid @RequestParam(value = "departure", required = false) String departure,
                                            @ApiParam(value = "Arrival") @Valid @RequestParam(value = "arrival", required = false) String arrival,
                                            @ApiParam(value = "Date") @Valid @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                            @ApiParam(value = "OrderBy") @Valid @RequestParam(value = "orderBy", defaultValue = "departure") String orderBy,
                                            @ApiParam(value = "Direction") @Valid @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                                            @ApiParam(value = "Page") @Valid @RequestParam(value = "page", defaultValue = "0") int page,
                                            @ApiParam(value = "Size") @Valid @RequestParam(value = "size", defaultValue = "20") int size) {

        if (!(direction.equals(Direction.ASCENDING.getDirectionCode())
                || direction.equals(Direction.DESCENDING.getDirectionCode()))) {
            throw new PaginationSortingException("Invalid sort direction");
        }

        if (!(orderBy.equals(OrderBy.DEPARTURE.getOrderByCode())
                || orderBy.equals(OrderBy.ARRIVAL.getOrderByCode())
                || orderBy.equals(OrderBy.DEPARTURE_DATE.getOrderByCode())
                || orderBy.equals(OrderBy.ARRIVAL_DATE.getOrderByCode()))) {
            throw new PaginationSortingException("Invalid orderBy condition");
        }

        return flightSearchService.searchFlights(orderBy, direction, page, size, departure, arrival, date);
    }
}
