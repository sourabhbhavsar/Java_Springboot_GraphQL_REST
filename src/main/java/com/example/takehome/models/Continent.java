package com.example.takehome.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class Continent {
    private String name;
    private List<String> countries;
    private List<String> otherCountries;

    public Continent() {
        countries = new ArrayList<>();
        otherCountries = new ArrayList<>();
    }

    public void addCountry(String country) {
        countries.add(country);
    }

    public void addOtherCountry(String country) {
        otherCountries.add(country);
    }
}
