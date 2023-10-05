package com.giraffe.foodplannerapplication.features.favorites.presenter;

import com.giraffe.foodplannerapplication.features.favorites.view.FavoritesView;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.repository.Repo;

public class FavoritesPresenter {
    FavoritesView view;
    Repo repo;

    public FavoritesPresenter(FavoritesView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void getFavorites(){
        view.onGetFavorites(repo.getFavMeals());
    }
    public void deleteFavorite(Meal meal){
        view.onDeleteFavorites(repo.deleteFavMeal(meal));
    }
}
