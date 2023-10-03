package com.giraffe.foodplannerapplication.features.meals.presenter;

import android.util.Log;

import com.giraffe.foodplannerapplication.features.meals.view.MealsFragment;
import com.giraffe.foodplannerapplication.features.meals.view.MealsView;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.NetworkCallback;

import java.util.List;

public class MealsPresenter {
    MealsView view;
    Repo repo;

    public MealsPresenter(MealsView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void getMealById(String mealId) {
        repo.getMealById(mealId, new NetworkCallback<Meal>() {
            @Override
            public void onSuccess(Meal response) {
                view.onGetMeal(response);
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.i(MealsFragment.TAG, errorMsg);
            }
        });
    }

    public void getCategoryMeals(String category) {
        repo.getCategoryMeals(category, new NetworkCallback<List<Meal>>() {
            @Override
            public void onSuccess(List<Meal> response) {
                view.onGetMeals(response);
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.i(MealsFragment.TAG, errorMsg);
            }
        });
    }

    public void getCountryMeals(String country) {
        repo.getCountryMeals(country, new NetworkCallback<List<Meal>>() {
            @Override
            public void onSuccess(List<Meal> response) {
                view.onGetMeals(response);
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.i(MealsFragment.TAG, errorMsg);
            }
        });
    }
}
