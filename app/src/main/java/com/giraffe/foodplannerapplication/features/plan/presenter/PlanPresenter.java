package com.giraffe.foodplannerapplication.features.plan.presenter;

import com.giraffe.foodplannerapplication.features.plan.view.DayView;
import com.giraffe.foodplannerapplication.models.repository.Repo;

public class PlanPresenter {
    DayView view;
    Repo repo;

    public PlanPresenter(DayView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void getPlannedMeals(int day){
        view.onGetPlannedMeals(repo.getPlannedMeals(day));
    }
}
