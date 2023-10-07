package com.giraffe.foodplannerapplication.features.signup.view;

import android.view.View;

import io.reactivex.rxjava3.core.Completable;

public interface SignUpView {
    void inflateViews(View view);
    void initClicks();
    void onCreateAccount(Completable completable);
}
