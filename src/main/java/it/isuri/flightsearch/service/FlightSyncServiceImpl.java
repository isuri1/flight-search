package it.isuri.flightsearch.service;

import it.isuri.flightsearch.client.FlightSearchClient;
import it.isuri.flightsearch.model.*;
import it.isuri.flightsearch.repository.FlightRepository;
import it.isuri.flightsearch.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightSyncServiceImpl implements FlightSyncService {

    private final FlightSearchClient flightSearchClient;

    private final FlightRepository flightRepository;

    @Override
    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void syncFlights() {

        syncCheapFlights();
        syncBusinessFlights();
    }

    private void syncCheapFlights() {

        try {
            log.info("Cheap Flight Sync Started...!!");
            List<FlightEntity> flights = new ArrayList<>();
            ResponseEntity<ExtSearchResult<CheapFlight>> response = flightSearchClient.getCheapFlights();
            ExtSearchResult<CheapFlight> responseBody = response.getBody();
            if (responseBody != null) {
                log.info("Cheap Flight response : " + responseBody.getData());
                for (CheapFlight cheapFlight : responseBody.getData()) {
                    String[] split = cheapFlight.getRoute().split("-");
                    FlightEntity flightEntity = new FlightEntity();
                    flightEntity.setFlightType(FlightType.CHEAP);
                    flightEntity.setArrival(split[0]);
                    flightEntity.setDeparture(split[1]);
                    flightEntity.setArrivalDate(DateUtil.getDateTime(Long.parseLong(cheapFlight.getArrival().split("\\.")[0])));
                    flightEntity.setDepartureDate(DateUtil.getDateTime(Long.parseLong(cheapFlight.getDeparture().split("\\.")[0])));
                    flights.add(flightEntity);
                }
            }

            StreamSupport.stream(flightRepository.saveAll(flights).spliterator(), false)
                    .map(FlightEntity::getId).min(Long::compareTo)
                    .ifPresent(aLong -> flightRepository.deleteByTypeAndId(FlightType.CHEAP, aLong));
            log.info("Cheap Flight Sync Finished...!!");
        } catch (Exception e) {
            log.error("Error while syncing Cheap Flights", e);
        }
    }

    private void syncBusinessFlights() {

        try {
            log.info("Business Flight Sync Started...!!");
            List<FlightEntity> flights = new ArrayList<>();
            ResponseEntity<ExtSearchResult<BusinessFlight>> response = flightSearchClient.getBusinessFlights();
            ExtSearchResult<BusinessFlight> responseBody = response.getBody();
            if (responseBody != null) {
                log.info("Business Flight response : " + responseBody.getData());
                for (BusinessFlight businessFlight : responseBody.getData()) {
                    FlightEntity flightEntity = new FlightEntity();
                    flightEntity.setFlightType(FlightType.BUSINESS);
                    flightEntity.setArrival(businessFlight.getArrival());
                    flightEntity.setDeparture(businessFlight.getDeparture());
                    flightEntity.setArrivalDate(DateUtil.getDateTime(Long.parseLong(businessFlight.getArrivalTime().split("\\.")[0])));
                    flightEntity.setDepartureDate(DateUtil.getDateTime(Long.parseLong(businessFlight.getDepartureTime().split("\\.")[0])));
                    flights.add(flightEntity);
                }
            }

            StreamSupport.stream(flightRepository.saveAll(flights).spliterator(), false)
                    .map(FlightEntity::getId).min(Long::compareTo)
                    .ifPresent(aLong -> flightRepository.deleteByTypeAndId(FlightType.BUSINESS, aLong));
            log.info("Business Flight Sync Finished...!!");
        } catch (Exception e) {
            log.error("Error while syncing Business Flights", e);
        }
    }
}
