package com.giraffe.foodplannerapplication.features.plan.view;

import com.giraffe.foodplannerapplication.models.PlannedMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface DayView {
    void onGetPlannedMeals(Observable<List<PlannedMeal>> observable);
}
