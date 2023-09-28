package com.giraffe.foodplannerapplication.network;


import com.giraffe.foodplannerapplication.models.CategoriesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {
    @GET("categories.php")
    Call<CategoriesResponse> getCategories();
}
