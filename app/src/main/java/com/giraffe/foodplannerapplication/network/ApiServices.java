package com.giraffe.foodplannerapplication.network;


import com.giraffe.foodplannerapplication.models.CategoriesResponse;
import com.giraffe.foodplannerapplication.models.CountriesResponse;
import com.giraffe.foodplannerapplication.models.IngredientsResponse;
import com.giraffe.foodplannerapplication.models.MealsResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("random.php")
    Call<MealsResponse> getRandomMeal();

    @GET("categories.php")
    Call<CategoriesResponse> getCategories();

    @GET("list.php?a=list")
    Call<CountriesResponse> getCountries();

    @GET("list.php?i=list")
    Call<IngredientsResponse> getIngredients();

    @GET("filter.php")
    Observable<MealsResponse> getCategoryMeals(@Query("c") String category);

    @GET("filter.php")
    Observable<MealsResponse> getCountryMeals(@Query("a") String country);

    @GET("search.php")
    Call<MealsResponse> getSearchResult(@Query("s") String word);

    @GET("lookup.php")
    Call<MealsResponse> getMealById(@Query("i") String mealId);
}
