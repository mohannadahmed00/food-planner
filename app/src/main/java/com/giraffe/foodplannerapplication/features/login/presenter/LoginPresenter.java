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
        repo.login(email, password, new NetworkCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                view.onLogin(true);
            }

            @Override
            public void onFailure(String errorMsg) {
                view.onLogin(false);
            }
        });
    }

    public void loginWithGoogle(String idToken){
        view.onGoogleLogin(repo.loginWithGoogle(idToken));
    }
}
