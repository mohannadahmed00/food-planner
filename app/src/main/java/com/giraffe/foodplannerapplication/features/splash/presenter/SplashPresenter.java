package com.giraffe.foodplannerapplication.features.splash.presenter;

import android.util.Log;

import com.giraffe.foodplannerapplication.features.splash.view.SplashView;
import com.giraffe.foodplannerapplication.models.repository.Repo;

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

    public void getCategories() {
        repo.getCategories().subscribe();
    }

    public void getCountries() {
        repo.getCountries().subscribe();
    }

    public void getIngredients() {
        repo.getIngredients().subscribe();
    }

    public void isFirstTime(){
        view.onGetFirstTimeFlag(repo.isFirstTime());
    }

}
