package it.isuri.flightsearch.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Flight {

    private String departure;

    private String arrival;

    private LocalDateTime from;

    private LocalDateTime to;

}
