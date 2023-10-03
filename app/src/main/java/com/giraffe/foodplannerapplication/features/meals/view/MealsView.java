package com.giraffe.foodplannerapplication.features.meals.view;

import android.view.View;

import com.giraffe.foodplannerapplication.models.Meal;

import java.util.List;

public interface MealsView {
    void inflateViews(View view);

    void initClicks();

    void onGetMeals(List<Meal> meals);

    void onGetMeal(Meal meal);
}
