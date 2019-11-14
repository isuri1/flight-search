package it.isuri.flightsearch.model;

public enum OrderBy {

    DEPARTURE("departure"),
    ARRIVAL("arrival"),
    DEPARTURE_DATE("departureDate"),
    ARRIVAL_DATE("arrivalDate");
    private String OrderByCode;

    private OrderBy(String orderBy) {
        this.OrderByCode = orderBy;
    }

    public String getOrderByCode() {
        return this.OrderByCode;
    }
}
