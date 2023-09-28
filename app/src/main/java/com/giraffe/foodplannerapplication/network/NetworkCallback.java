package com.giraffe.foodplannerapplication.network;


import com.giraffe.foodplannerapplication.models.CategoriesResponse;

public interface NetworkCallback{
    void onSuccess(CategoriesResponse response);

    void onFailure(String errorMsg);
}
