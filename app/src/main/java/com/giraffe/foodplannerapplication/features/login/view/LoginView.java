package com.giraffe.foodplannerapplication.features.login.view;

import android.view.View;

import io.reactivex.rxjava3.core.Completable;

public interface LoginView {
    void inflateViews(View view);

    void initClicks();

    void onLogin(Boolean isLoggedIn);

    void onGoogleLogin(Completable completable);
}
