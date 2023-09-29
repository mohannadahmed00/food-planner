package com.giraffe.foodplannerapplication.features.home.presenter;


import com.giraffe.foodplannerapplication.features.home.view.HomeView;
import com.giraffe.foodplannerapplication.models.MealsResponse;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.NetworkCallback;

public class HomePresenter {
    private final static String TAG = "HomePresenter";

    HomeView view;
    Repo repo;

    public HomePresenter(HomeView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void getRandomMeal() {
        repo.getRandomMeal(new NetworkCallback<MealsResponse>() {
            @Override
            public void onSuccess(MealsResponse response) {
                view.onGetRandomMeal(response.getMeals().get(0));
            }

            @Override
            public void onFailure(String errorMsg) {
                view.onFailGetRandomMeal(errorMsg);
            }
        });
    }
}
