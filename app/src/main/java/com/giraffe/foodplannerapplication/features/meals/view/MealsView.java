package com.giraffe.foodplannerapplication.features.meals.view;

import android.view.View;

import com.giraffe.foodplannerapplication.models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface MealsView {
    void inflateViews(View view);

    void initClicks();

    void onGetMeals(Observable<List<Meal>> observable);

    void onGetMeal(Observable<Meal> observable);
    void onGetFavMeals(Observable<List<Meal>> observable);

    void onMealInserted(Completable completable);
    void onMealDeleted(Completable completable);
}
