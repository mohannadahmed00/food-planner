package com.giraffe.foodplannerapplication.features.home.view;

import android.view.View;

import com.giraffe.foodplannerapplication.models.Category;
import com.giraffe.foodplannerapplication.models.Country;
import com.giraffe.foodplannerapplication.models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface HomeView {
    void inflateViews(View view);

    void initClicks();

    void onGetRandomMeal(Observable<Meal> observable);

    void onGetCategories(Observable<List<Category>> observable);
    void onGetCountries(Observable<List<Country>> observable);

    void onGetSearchResult(Observable<List<Meal>> observable);

    void onFavMealInserted(Completable completable);
    void onFavMealDeleted(Completable completable);

    void onPlannedMealInserted(Completable completable);

    void onGetLoggedInFlag(Observable<Boolean> observable);



}
