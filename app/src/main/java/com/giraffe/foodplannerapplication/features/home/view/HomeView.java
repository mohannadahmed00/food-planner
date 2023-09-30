package com.giraffe.foodplannerapplication.features.home.view;

import android.view.View;

import com.giraffe.foodplannerapplication.models.Meal;

public interface HomeView {
    void inflateViews(View view);

    void initClicks();

    void onGetRandomMeal(Meal meal);

    void onGetRandomMealFail(String errorMsg);
}
