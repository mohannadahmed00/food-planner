package com.giraffe.foodplannerapplication.features.settings.view;

import android.view.View;

public interface SettingsView {
    void initViews(View view);

    void initClicks();

    void onLogout(boolean isLoggedOut);
}
