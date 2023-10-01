package com.giraffe.foodplannerapplication.features.home.view;

import android.view.View;

import com.giraffe.foodplannerapplication.models.Category;
import com.giraffe.foodplannerapplication.models.Country;
import com.giraffe.foodplannerapplication.models.Meal;

import java.util.List;

public interface HomeView {
    void inflateViews(View view);

    void initClicks();

    void onGetRandomMeal(Meal meal);

    void onGetCategories(List<Category> categories);
    void onGetCountries(List<Country> countries);
}
