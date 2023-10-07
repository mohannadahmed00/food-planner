package com.giraffe.foodplannerapplication.features.settings.view;

import android.view.View;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface SettingsView {
    void initViews(View view);

    void initClicks();

    void onLogout(Completable completable);

    void onGetLoggedInFlag(Observable<Boolean> observable);

    void onFavoritesDeleted(Completable completable);
    void onPlannedDeleted(Completable completable);

    void onDataBackedUp(Completable completable);

}
