package com.giraffe.foodplannerapplication.features.login.view;

import android.view.View;

public interface LoginView {
    void inflateViews(View view);

    void initClicks();

    void onLogin(Boolean isLoggedIn);
}
