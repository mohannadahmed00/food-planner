package com.giraffe.foodplannerapplication.features.plan.presenter;

import com.giraffe.foodplannerapplication.features.plan.view.DayView;
import com.giraffe.foodplannerapplication.features.plan.view.PlanView;
import com.giraffe.foodplannerapplication.models.PlannedMeal;
import com.giraffe.foodplannerapplication.models.repository.Repo;

public class PlanPresenter {
    DayView view;
    PlanView planView;
    Repo repo;

    public PlanPresenter(PlanView view, Repo repo) {
        this.planView = view;
        this.repo = repo;
    }

    public PlanPresenter(DayView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void getPlannedMeals(int day) {
        view.onGetPlannedMeals(repo.getPlannedMeals(day));
    }

    public void deletePlannedMeal(PlannedMeal meal) {
        view.onPlannedMealDeleted(repo.deletePlannedMeal(meal));
    }

    public void isLoggedIn(){
        planView.onGetLoggedInFlag(repo.isLoggedIn());
    }
}
