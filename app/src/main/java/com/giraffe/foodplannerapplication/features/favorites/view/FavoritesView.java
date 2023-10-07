package com.giraffe.foodplannerapplication.features.favorites.view;

import com.giraffe.foodplannerapplication.models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface FavoritesView {

    void onGetFavorites(Observable<List<Meal>> observable);
    void onDeleteFavorites(Completable completable);

    void onGetLoggedInFlag(Observable<Boolean> observable);
}
