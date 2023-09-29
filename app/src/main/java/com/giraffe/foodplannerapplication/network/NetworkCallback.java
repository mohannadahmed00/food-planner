package com.giraffe.foodplannerapplication.network;


import com.giraffe.foodplannerapplication.models.MealsResponse;

public interface NetworkCallback<T>{
    void onSuccess(T response);

    void onFailure(String errorMsg);
}
