package com.giraffe.foodplannerapplication.features.home.view;

import com.giraffe.foodplannerapplication.models.Meal;

public interface HomeView {

    void onGetRandomMeal(Meal meal);

    void onGetRandomMealFail(String errorMsg);
}
