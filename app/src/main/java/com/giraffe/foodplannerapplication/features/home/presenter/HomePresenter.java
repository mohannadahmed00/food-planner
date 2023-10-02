package com.giraffe.foodplannerapplication.features.home.presenter;


import android.graphics.Color;
import android.util.Log;

import com.giraffe.foodplannerapplication.MainActivity;
import com.giraffe.foodplannerapplication.features.home.view.HomeFragment;
import com.giraffe.foodplannerapplication.features.home.view.HomeView;
import com.giraffe.foodplannerapplication.models.Category;
import com.giraffe.foodplannerapplication.models.Country;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.MealsResponse;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.NetworkCallback;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HomePresenter {
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
                Log.i(HomeFragment.TAG, errorMsg);
            }
        });
    }

    public void getCategories() {
        repo.getCategories(new NetworkCallback<List<Category>>() {

            @Override
            public void onSuccess(List<Category> response) {
                view.onGetCategories(response);
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.i(HomeFragment.TAG, errorMsg);
            }
        });
    }

    public void getCountries() {
        repo.getCountries(new NetworkCallback<List<Country>>() {
            @Override
            public void onSuccess(List<Country> response) {
                response.forEach(e -> e.setStrColor(getRandomColor()));
                view.onGetCountries(response);
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.i(HomeFragment.TAG, errorMsg);
            }
        });
    }

    private String getRandomColor() {
        Random random = new Random();
        int color = Color.argb(56, random.nextInt(Integer.MAX_VALUE - 157), random.nextInt(Integer.MAX_VALUE - 157), random.nextInt(Integer.MAX_VALUE - 157));
        return String.format("#%06X", 0xFFFFFF & color);
    }

    public void getSearchResult(String word) {
        repo.getSearchResult(word, new NetworkCallback<List<Meal>>() {
            @Override
            public void onSuccess(List<Meal> response) {
                view.onGetSearchResult(response);
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.i(HomeFragment.TAG, errorMsg);
            }
        });
    }


}
