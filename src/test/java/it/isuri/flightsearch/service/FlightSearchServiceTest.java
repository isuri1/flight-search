package it.isuri.flightsearch.service;

import it.isuri.flightsearch.model.FlightEntity;
import it.isuri.flightsearch.model.FlightType;
import it.isuri.flightsearch.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightSearchServiceTest {

    @Mock
    private FlightRepository flightRepository;

    private FlightSearchService flightSearchService;

    @BeforeEach
    void init() {
        flightSearchService = new FlightSearchServiceImpl(flightRepository);
    }

    @Test
    void testSearchFlights() {

        FlightEntity flight = getFlightEntity(FlightType.CHEAP, "Ankara", "Antalya", LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        FlightEntity flight2 = getFlightEntity(FlightType.BUSINESS, "Ankara", "Antalya", LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        List<FlightEntity> flights = new ArrayList<>();
        flights.add(flight);
        flights.add(flight2);

        Page<FlightEntity> pagedResponse = new PageImpl<>(flights);

        when(flightRepository.findAllByDepartureAndArrivalAndDepartureDate(anyString(), anyString(), any(LocalDate.class), any(LocalDate.class), any(Pageable.class))).thenReturn(pagedResponse);

        Page<FlightEntity> flightResults = flightSearchService.searchFlights("departure", "ASC", 0, 10, "Ankara", "Antalya", LocalDate.now());

        assertNotNull(flightResults);
        assertNotNull(flightResults.getContent());

        assertEquals(FlightType.CHEAP, flightResults.getContent().get(0).getFlightType());
        assertEquals("Ankara", flightResults.getContent().get(0).getDeparture());
        assertEquals("Antalya", flightResults.getContent().get(0).getArrival());
    }

    private FlightEntity getFlightEntity(FlightType flightType, String departure, String arrival, LocalDateTime departureDate, LocalDateTime arrivalDate) {

        FlightEntity flightEntity = new FlightEntity();
        flightEntity.setFlightType(flightType);
        flightEntity.setDeparture(departure);
        flightEntity.setArrival(arrival);
        flightEntity.setDepartureDate(departureDate);
        flightEntity.setArrivalDate(arrivalDate);

        return flightEntity;
    }
}