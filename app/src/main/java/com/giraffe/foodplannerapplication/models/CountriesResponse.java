package com.giraffe.foodplannerapplication.models;

import java.util.List;

public class CountriesResponse {
    private final List<Country> meals;

    public CountriesResponse(List<Country> meals) {
        this.meals = meals;
    }

    public List<Country> getCountries() {
        return meals;
    }
}
