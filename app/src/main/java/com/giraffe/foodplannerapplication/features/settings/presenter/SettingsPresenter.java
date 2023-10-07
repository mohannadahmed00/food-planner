package com.giraffe.foodplannerapplication.features.settings.presenter;

import com.giraffe.foodplannerapplication.features.settings.view.SettingsView;
import com.giraffe.foodplannerapplication.models.repository.Repo;

public class SettingsPresenter {
    SettingsView view;
    Repo repo;

    public SettingsPresenter(SettingsView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void logout() {
        view.onLogout(repo.logout());
    }

    public void backup(){
        view.onDataBackedUp(repo.backUp());
    }

    public void isLoggedIn() {
        view.onGetLoggedInFlag(repo.isLoggedIn());
    }

    public void deletePlannedMeals() {
        view.onPlannedDeleted(repo.deletePlannedMeals());
    }

    public void deleteFavoriteMeals() {
        view.onFavoritesDeleted(repo.deleteFavoriteMeals());
    }
}
