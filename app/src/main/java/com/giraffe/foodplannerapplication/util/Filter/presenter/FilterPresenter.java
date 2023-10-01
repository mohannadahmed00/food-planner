package com.giraffe.foodplannerapplication.util.Filter.presenter;

import com.giraffe.foodplannerapplication.models.Category;
import com.giraffe.foodplannerapplication.models.Country;
import com.giraffe.foodplannerapplication.models.Ingredient;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.NetworkCallback;
import com.giraffe.foodplannerapplication.util.Filter.view.FilterView;

import java.util.ArrayList;
import java.util.List;

public class FilterPresenter {
    FilterView view;
    Repo repo;

    public FilterPresenter(FilterView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void getCategories() {
        repo.getCategories(new NetworkCallback<List<Category>>() {
            @Override
            public void onSuccess(List<Category> response) {
                view.onGetCategories(response);
            }

            @Override
            public void onFailure(String errorMsg) {
                view.onGetCategories(new ArrayList<>());
            }
        });
    }

    public void getCountries() {
        repo.getCountries(new NetworkCallback<List<Country>>() {
            @Override
            public void onSuccess(List<Country> response) {
                view.onGetCountries(response);
            }

            @Override
            public void onFailure(String errorMsg) {
                view.onGetCountries(new ArrayList<>());
            }
        });
    }

    public void getIngredients() {
        repo.getIngredients(new NetworkCallback<List<Ingredient>>() {
            @Override
            public void onSuccess(List<Ingredient> response) {
                view.onGetIngredient(response);
            }

            @Override
            public void onFailure(String errorMsg) {
                view.onGetIngredient(new ArrayList<>());
            }
        });
    }
}
