package com.giraffe.foodplannerapplication.features.meals.presenter;

import android.util.Log;

import com.giraffe.foodplannerapplication.features.meals.view.MealsFragment;
import com.giraffe.foodplannerapplication.features.meals.view.MealsView;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.NetworkCallback;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class MealsPresenter {
    MealsView view;
    Repo repo;

    public MealsPresenter(MealsView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void getMealById(String mealId) {
        view.onGetMeal(repo.getMealById(mealId));
    }

    public void getCategoryMeals(String category) {
        view.onGetMeals(repo.getCategoryMeals(category));
    }

    public void getCountryMeals(String country) {
        view.onGetMeals(repo.getCountryMeals(country));
    }

    public void getFavMeals() {
        view.onGetFavMeals(repo.getLocalMeals());
    }

    public void insertMeal(Meal meal) {
        view.onMealInserted(repo.insertMeal(meal));
    }

    public void deleteMeal(Meal meal) {
        view.onMealDeleted(repo.deleteMeal(meal));
    }
}
