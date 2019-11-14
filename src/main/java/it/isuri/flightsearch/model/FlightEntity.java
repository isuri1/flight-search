package it.isuri.flightsearch.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "FLIGHT")
public class FlightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    private String departure;

    private String arrival;

    private LocalDateTime departureDate;

    private LocalDateTime arrivalDate;

    @Enumerated(value = EnumType.STRING)
    private FlightType flightType;
}
