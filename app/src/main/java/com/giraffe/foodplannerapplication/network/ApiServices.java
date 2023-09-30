package com.giraffe.foodplannerapplication.network;


import com.giraffe.foodplannerapplication.models.CategoriesResponse;
import com.giraffe.foodplannerapplication.models.CountriesResponse;
import com.giraffe.foodplannerapplication.models.IngredientsResponse;
import com.giraffe.foodplannerapplication.models.MealsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {
    @GET("random.php")
    Call<MealsResponse> getRandomMeal();

    @GET("categories.php")
    Call<CategoriesResponse> getCategories();

    @GET("list.php?a=list")
    Call<CountriesResponse> getCountries();

    @GET("list.php?i=list")
    Call<IngredientsResponse> getIngredients();
}
