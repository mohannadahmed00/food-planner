package com.giraffe.foodplannerapplication.features.home.Filter.presenter;

import com.giraffe.foodplannerapplication.features.home.Filter.view.FilterView;
import com.giraffe.foodplannerapplication.models.repository.Repo;

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
