package com.giraffe.foodplannerapplication.features.onboard.presenter;

import com.giraffe.foodplannerapplication.features.onboard.view.OnBoardView;
import com.giraffe.foodplannerapplication.models.repository.Repo;

public class OnBoardPresenter {
    OnBoardView view;
    Repo repo;

    public OnBoardPresenter(OnBoardView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void setFirstTime(){
        repo.setFirstTime();
    }
}
