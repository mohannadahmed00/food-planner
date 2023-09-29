package com.giraffe.foodplannerapplication.models.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.giraffe.foodplannerapplication.database.LocalSource;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.MealsResponse;
import com.giraffe.foodplannerapplication.network.NetworkCallback;
import com.giraffe.foodplannerapplication.network.RemoteSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repo implements RepoInterface{
    private final static String TAG = "Repo";
    RemoteSource remoteSource;
    LocalSource localSource;
    private static Repo repo = null;


    private Repo(RemoteSource remoteSource,LocalSource localSource) {
        this.remoteSource = remoteSource;
        this.localSource = localSource;
    }

    public static Repo getInstance(RemoteSource remoteSource,LocalSource localSource) {
        if (repo == null) {
            repo = new Repo(remoteSource,localSource);
        }
        return repo;
    }


    //=================remote functions=================
    @Override
    public void getRandomMeal(NetworkCallback<MealsResponse> callback) {
        Call<MealsResponse> call = remoteSource.makeNetworkCall(callback).getRandomMeal();
        call.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
        //call = apiServices.getRandomMeal();
        //call.enqueue(
        // onResponse=>callback.onSuccess(response.data);
        // onFailure=>callback.onFailure(error);
        // );

    }


    //=================local functions=================
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
