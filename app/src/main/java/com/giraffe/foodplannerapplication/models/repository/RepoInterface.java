package com.giraffe.foodplannerapplication.models.repository;

import androidx.lifecycle.LiveData;

import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.MealsResponse;
import com.giraffe.foodplannerapplication.network.NetworkCallback;

import java.util.List;

public interface RepoInterface {
    //=================remote functions=================
    void getRandomMeal(NetworkCallback<MealsResponse> callback);//optional till now just for testing
    //=================local functions=================
    LiveData<List<Meal>> getLocalMeals();//optional till now just for testing
    void insertMeal(Meal meal);//optional till now just for testing

    void deleteMeal(Meal meal);//optional till now just for testing

}
