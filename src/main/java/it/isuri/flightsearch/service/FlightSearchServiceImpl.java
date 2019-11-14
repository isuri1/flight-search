package it.isuri.flightsearch.service;

import it.isuri.flightsearch.model.FlightEntity;
import it.isuri.flightsearch.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FlightSearchServiceImpl implements FlightSearchService {

    private final FlightRepository flightRepository;

    @Override
    public Page<FlightEntity> searchFlights(String orderBy, String direction, int page, int size, String departure, String arrival, LocalDate date) {

        Sort sort;
        if (direction.equals("ASC")) {
            sort = Sort.by(new Sort.Order(Direction.ASC, orderBy));
        } else {
            sort = Sort.by(new Sort.Order(Direction.DESC, orderBy));
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        return flightRepository.findAllByDepartureAndArrivalAndDepartureDate(departure, arrival, date, date != null ? date.plusDays(1) : null, pageable);
    }
}
