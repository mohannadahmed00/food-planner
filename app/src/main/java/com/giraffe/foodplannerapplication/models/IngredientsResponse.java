package com.giraffe.foodplannerapplication.models;

import java.util.List;

public class IngredientsResponse {
    private final List<Ingredient> meals;

    public IngredientsResponse(List<Ingredient> meals) {
        this.meals = meals;
    }

    public List<Ingredient> getIngredients() {
        return meals;
    }
}
