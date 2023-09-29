package com.giraffe.foodplannerapplication.network;


import com.giraffe.foodplannerapplication.models.MealsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {
    @GET("random.php")
    Call<MealsResponse> getRandomMeal();
}
