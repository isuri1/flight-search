package it.isuri.flightsearch.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Flight {

    private FlightType flightType;

    private String departure;

    private String arrival;

    private LocalDateTime departureDate;

    private LocalDateTime arrivalDate;

}
