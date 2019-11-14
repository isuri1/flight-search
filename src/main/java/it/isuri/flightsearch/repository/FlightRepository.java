package it.isuri.flightsearch.repository;

import it.isuri.flightsearch.model.FlightEntity;
import it.isuri.flightsearch.model.FlightType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Repository
public interface FlightRepository extends PagingAndSortingRepository<FlightEntity, Long> {

    @Query("SELECT f FROM FlightEntity f WHERE (:departure is null or f.departure = :departure) AND "
            + "(:arrival is null or f.arrival = :arrival) AND "
            + "(:date is null or (f.departureDate BETWEEN :date AND :endDate))")
    Page<FlightEntity> findAllByDepartureAndArrivalAndDepartureDate(String departure, String arrival, LocalDate date, LocalDate endDate, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM FlightEntity f WHERE f.flightType = :flightType AND f.id < :id")
    void deleteByTypeAndId(@Param("flightType") FlightType flightType, @Param("id") Long id);
}
