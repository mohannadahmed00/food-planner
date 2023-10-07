package com.giraffe.foodplannerapplication.features.details.view;

import android.view.View;

import com.giraffe.foodplannerapplication.models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface DetailsView {
    void inflateViews(View view);

    void initClicks();

    void onFavMealInserted(Completable completable);
    void onFavMealDeleted(Completable completable);
    void onGetFavMeals(Observable<List<Meal>> observable);

    void onPlannedMealInserted(Completable completable);

    void onGetLoggedInFlag(Observable<Boolean> observable);

}
