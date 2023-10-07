package com.giraffe.foodplannerapplication.features.signup.presenter;

import com.giraffe.foodplannerapplication.features.signup.view.SignUpView;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.NetworkCallback;

public class SignUpPresenter {
    private final static String TAG = "HomePresenter";
    SignUpView view;
    Repo repo;

    public SignUpPresenter(SignUpView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void createAccount(String email, String password) {
        view.onCreateAccount(repo.createAccount(email, password));
    }
}
