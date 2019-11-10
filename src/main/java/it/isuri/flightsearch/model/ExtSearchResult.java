package it.isuri.flightsearch.model;

import lombok.Data;

import java.util.List;

@Data
public class ExtSearchResult {

    private List<ExtFlight> data;
}
