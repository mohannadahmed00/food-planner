package com.giraffe.foodplannerapplication.models;

import java.util.List;

public class IngredientsResponse {
    private final List<Meal> meals;

    public IngredientsResponse(List<Meal> meals) {
        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }
}
