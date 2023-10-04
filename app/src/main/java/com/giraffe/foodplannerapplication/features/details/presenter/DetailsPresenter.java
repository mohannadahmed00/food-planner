package com.giraffe.foodplannerapplication.features.details.presenter;

import com.giraffe.foodplannerapplication.features.details.view.DetailsView;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.repository.Repo;

public class DetailsPresenter {
    DetailsView view;
    Repo repo;

    public DetailsPresenter(DetailsView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void insertMeal(Meal meal){
        view.onFavMealInserted(repo.insertMeal(meal));
    }
    public void deleteMeal(Meal meal){
        view.onFavMealDeleted(repo.deleteMeal(meal));
    }

    public void getMeals(){
        view.onGetFavMeals(repo.getLocalMeals());
    }
}
