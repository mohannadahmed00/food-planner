package com.giraffe.foodplannerapplication.features.home.presenter;


import com.giraffe.foodplannerapplication.features.home.view.HomeView;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.PlannedMeal;
import com.giraffe.foodplannerapplication.models.repository.Repo;

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

    public void insertFavMeal(Meal meal) {
        view.onFavMealInserted(repo.insertFavMeal(meal));
    }

    public void deleteFavMeal(Meal meal) {
        view.onFavMealDeleted(repo.deleteFavMeal(meal));
    }

    public void insertPlannedMeal(PlannedMeal meal) {
        view.onPlannedMealInserted(repo.insertPlannedMeal(meal));
    }

    public void isLoggedIn() {
        view.onGetLoggedInFlag(repo.isLoggedIn());
    }

    public void getFavorites() {
        view.onGetFavorites(repo.getFavMeals());
    }


}
