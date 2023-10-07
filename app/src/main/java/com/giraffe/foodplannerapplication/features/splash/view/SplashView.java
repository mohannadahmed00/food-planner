package com.giraffe.foodplannerapplication.features.splash.view;

import android.view.View;

import io.reactivex.rxjava3.core.Observable;

public interface SplashView {
    void onGetLoggedInFlag(Observable<Boolean> observable);
    void onGetFirstTimeFlag(Observable<Boolean> observable);
}
