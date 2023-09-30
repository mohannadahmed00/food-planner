package com.giraffe.foodplannerapplication.features.splash.presenter;

import android.util.Log;

import com.giraffe.foodplannerapplication.features.splash.view.SplashFragment;
import com.giraffe.foodplannerapplication.features.splash.view.SplashView;
import com.giraffe.foodplannerapplication.models.CategoriesResponse;
import com.giraffe.foodplannerapplication.models.Category;
import com.giraffe.foodplannerapplication.models.CountriesResponse;
import com.giraffe.foodplannerapplication.models.Country;
import com.giraffe.foodplannerapplication.models.Ingredient;
import com.giraffe.foodplannerapplication.models.IngredientsResponse;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.NetworkCallback;

import java.util.List;

public class SplashPresenter {

    SplashView view;
    Repo repo;

    public SplashPresenter(SplashView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void isLoggedIn() {
        view.onGetLoggedInFlag(repo.isLoggedIn());
    }

    public void getCategories(){

        repo.getCategories(new NetworkCallback<List<Category>>() {
            @Override
            public void onSuccess(List<Category> categories) {
                for (Category category: categories) {
                    Log.i(SplashFragment.TAG,category.getStrCategory());
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.i(SplashFragment.TAG,errorMsg);
            }
        });
    }

    public void getCountries(){
        repo.getCountries(new NetworkCallback<List<Country>>() {
            @Override
            public void onSuccess(List<Country> countries) {
                for (Country country: countries) {
                    Log.i(SplashFragment.TAG,country.getStrArea());
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.i(SplashFragment.TAG,errorMsg);
            }
        });
    }

    public void getIngredients(){
        repo.getIngredients(new NetworkCallback<List<Ingredient>>() {
            @Override
            public void onSuccess(List<Ingredient> ingredients) {
                for (Ingredient ingredient: ingredients) {
                    Log.i(SplashFragment.TAG,ingredient.getStrIngredient());
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                Log.i(SplashFragment.TAG,errorMsg);
            }
        });
    }

}
