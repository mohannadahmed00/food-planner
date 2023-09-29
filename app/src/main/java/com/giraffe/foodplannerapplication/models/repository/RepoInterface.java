package com.giraffe.foodplannerapplication.models.repository;

import androidx.lifecycle.LiveData;

import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.MealsResponse;
import com.giraffe.foodplannerapplication.network.NetworkCallback;

import java.util.List;

public interface RepoInterface {
    //optional till now just for testing
    //=================remote functions=================
    void getRandomMeal(NetworkCallback<MealsResponse> callback);

    void createAccount(String email, String password, NetworkCallback<Boolean> callback);
    void login(String email, String password, NetworkCallback<Boolean> callback);

    //=================local functions=================
    LiveData<List<Meal>> getLocalMeals();

    void insertMeal(Meal meal);

    void deleteMeal(Meal meal);

}
