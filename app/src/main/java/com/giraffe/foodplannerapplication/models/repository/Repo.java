package com.giraffe.foodplannerapplication.models.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.giraffe.foodplannerapplication.database.LocalSource;
import com.giraffe.foodplannerapplication.models.CategoriesResponse;
import com.giraffe.foodplannerapplication.models.CountriesResponse;
import com.giraffe.foodplannerapplication.models.IngredientsResponse;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.MealsResponse;
import com.giraffe.foodplannerapplication.network.NetworkCallback;
import com.giraffe.foodplannerapplication.network.RemoteSource;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repo implements RepoInterface {
    RemoteSource remoteSource;
    LocalSource localSource;
    FirebaseAuth mAuth;
    private static Repo repo = null;


    private Repo(RemoteSource remoteSource, LocalSource localSource) {
        this.remoteSource = remoteSource;
        this.localSource = localSource;
        mAuth = FirebaseAuth.getInstance();
    }

    public static Repo getInstance(RemoteSource remoteSource, LocalSource localSource) {
        if (repo == null) {
            repo = new Repo(remoteSource, localSource);
        }
        return repo;
    }


    //=================remote functions=================
    @Override
    public void getRandomMeal(NetworkCallback<MealsResponse> callback) {
        Call<MealsResponse> call = remoteSource.makeNetworkCall(callback).getRandomMeal();
        call.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealsResponse> call, @NonNull Response<MealsResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<MealsResponse> call, @NonNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void createAccount(String email, String password, NetworkCallback<Boolean> callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mAuth.signOut();
                        callback.onSuccess(true);
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    @Override
    public void login(String email, String password, NetworkCallback<Boolean> callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(true);
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    @Override
    public boolean logout() {
        mAuth.signOut();
        return mAuth.getCurrentUser() == null;
    }
    @Override
    public void getCategories(NetworkCallback<CategoriesResponse> callback){
        Call<CategoriesResponse> call = remoteSource.makeNetworkCall(callback).getCategories();
        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(@NonNull Call<CategoriesResponse> call, @NonNull Response<CategoriesResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CategoriesResponse> call, @NonNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });

    }

    @Override
    public void getCountries(NetworkCallback<CountriesResponse> callback){
        Call<CountriesResponse> call = remoteSource.makeNetworkCall(callback).getCountries();
        call.enqueue(new Callback<CountriesResponse>() {
            @Override
            public void onResponse(@NonNull Call<CountriesResponse> call, @NonNull Response<CountriesResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CountriesResponse> call, @NonNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });

    }

    @Override
    public void getIngredients(NetworkCallback<IngredientsResponse> callback){
        Call<IngredientsResponse> call = remoteSource.makeNetworkCall(callback).getIngredients();
        call.enqueue(new Callback<IngredientsResponse>() {
            @Override
            public void onResponse(@NonNull Call<IngredientsResponse> call, @NonNull Response<IngredientsResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<IngredientsResponse> call, @NonNull Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });

    }

    //=================local functions=================
    @Override
    public boolean isLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

    @Override
    public LiveData<List<Meal>> getLocalMeals() {
        return localSource.getMeals();
    }

    @Override
    public void insertMeal(Meal meal) {
        localSource.insertMeal(meal);
    }

    @Override
    public void deleteMeal(Meal meal) {
        localSource.deleteMeal(meal);
    }
}
