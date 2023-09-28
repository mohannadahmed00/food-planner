package com.giraffe.foodplannerapplication.database;

import androidx.lifecycle.LiveData;

import com.giraffe.foodplannerapplication.models.Meal;

import java.util.List;

public interface LocalSource {
    void insertMeal(Meal meal);//optional till now just for testing

    void deleteMeal(Meal meal);//optional till now just for testing

    LiveData<List<Meal>> getMeals();//optional till now just for testing

}
