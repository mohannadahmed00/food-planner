package com.giraffe.foodplannerapplication.models;

import java.util.List;

public class MealsResponse {
    private final List<Meal> meals;

    public MealsResponse(List<Meal> meals) {
        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }
}
