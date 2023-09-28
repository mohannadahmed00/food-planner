package com.giraffe.foodplannerapplication.models.repository;

import androidx.lifecycle.LiveData;

import com.giraffe.foodplannerapplication.database.LocalSource;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.network.NetworkCallback;
import com.giraffe.foodplannerapplication.network.RemoteSource;

import java.util.List;

public class Repo implements RepoInterface{
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
    public void getRandomMeal(NetworkCallback callback) {
        remoteSource.makeNetworkCall(callback);
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
