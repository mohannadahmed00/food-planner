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
        view.onGetCategories(repo.getCategories());
    }

    public void getCountries() {
        view.onGetCountries(repo.getCountries());
    }

    public void getIngredients() {
        view.onGetIngredient(repo.getIngredients());
    }
}
