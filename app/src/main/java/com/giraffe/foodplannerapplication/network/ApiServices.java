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
    Observable<MealsResponse> getRandomMeal();

    @GET("categories.php")
    Observable<CategoriesResponse> getCategories();

    @GET("list.php?a=list")
    Observable<CountriesResponse> getCountries();

    @GET("list.php?i=list")
    Observable<IngredientsResponse> getIngredients();

    @GET("filter.php")
    Observable<MealsResponse> getCategoryMeals(@Query("c") String category);

    @GET("filter.php")
    Observable<MealsResponse> getCountryMeals(@Query("a") String country);

    @GET("search.php")
    Observable<MealsResponse> getSearchResult(@Query("s") String word);

    @GET("lookup.php")
    Observable<MealsResponse> getMealById(@Query("i") String mealId);
}
