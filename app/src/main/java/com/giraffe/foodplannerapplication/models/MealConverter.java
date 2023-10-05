package com.giraffe.foodplannerapplication.models;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

public class MealConverter {
    @TypeConverter
    public String fromMeal(Meal meal) {
        Gson gson = new Gson();
        return gson.toJson(meal);
    }

    @TypeConverter
    public Meal toMeal(String mealJson) {
        Gson gson = new Gson();
        return gson.fromJson(mealJson, Meal.class);
    }
}
