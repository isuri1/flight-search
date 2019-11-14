package it.isuri.flightsearch.model;

import lombok.Data;

import java.util.List;

@Data
public class ExtSearchResult<T> {

    private List<T> data;
}
