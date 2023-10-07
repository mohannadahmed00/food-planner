package com.giraffe.foodplannerapplication.features.plan.view;

import io.reactivex.rxjava3.core.Observable;

public interface PlanView {

    void onGetLoggedInFlag(Observable<Boolean> observable);
}
