package com.giraffe.foodplannerapplication.features.home.view;

import android.view.View;

import com.giraffe.foodplannerapplication.models.Category;
import com.giraffe.foodplannerapplication.models.Country;
import com.giraffe.foodplannerapplication.models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

public interface HomeView {
    void inflateViews(View view);

    void initClicks();

    void onGetRandomMeal(Meal meal);

    void onGetCategories(List<Category> categories);
    void onGetCountries(List<Country> countries);

    void onGetSearchResult(List<Meal> meals);

    void onFavMealInserted(Completable completable);
    void onFavMealDeleted(Completable completable);
}
