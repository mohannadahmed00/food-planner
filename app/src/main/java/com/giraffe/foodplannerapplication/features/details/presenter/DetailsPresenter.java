package com.giraffe.foodplannerapplication.features.details.presenter;

import com.giraffe.foodplannerapplication.features.details.view.DetailsView;
import com.giraffe.foodplannerapplication.models.repository.Repo;

public class DetailsPresenter {
    DetailsView view;
    Repo repo;

    public DetailsPresenter(DetailsView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }
}
