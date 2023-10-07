package com.giraffe.foodplannerapplication.features.login.presenter;

import com.giraffe.foodplannerapplication.features.login.view.LoginView;
import com.giraffe.foodplannerapplication.models.repository.Repo;
import com.giraffe.foodplannerapplication.network.NetworkCallback;

public class LoginPresenter {

    LoginView view;
    Repo repo;

    public LoginPresenter(LoginView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void login(String email, String password) {
        view.onLogin(repo.login(email, password));
    }

    public void loginWithGoogle(String idToken){
        view.onGoogleLogin(repo.loginWithGoogle(idToken));
    }
}
