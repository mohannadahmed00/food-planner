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

import io.reactivex.rxjava3.core.Completable;

public class HomePresenter {
    HomeView view;
    Repo repo;

    public HomePresenter(HomeView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void getRandomMeal() {
        view.onGetRandomMeal(repo.getRandomMeal());
    }

    public void getCategories() {
        view.onGetCategories(repo.getCategories());
    }

    public void getCountries() {
        view.onGetCountries(repo.getCountries());
    }

    public void getSearchResult(String word) {
        view.onGetSearchResult(repo.getSearchResult(word));
    }

    public void insertMeal(Meal meal){
        view.onFavMealInserted(repo.insertMeal(meal));
    }
    public void deleteMeal(Meal meal){
        view.onFavMealDeleted(repo.deleteMeal(meal));
    }


}
