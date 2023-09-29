package com.giraffe.foodplannerapplication.features.signup.view;

import android.view.View;

public interface SignUpView {
    void inflateViews(View view);
    void initClicks();
    void onCreateAccount(Boolean isRegistered);
}
