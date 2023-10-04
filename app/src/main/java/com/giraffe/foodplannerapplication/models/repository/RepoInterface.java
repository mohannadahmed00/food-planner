package com.giraffe.foodplannerapplication.models.repository;

import androidx.lifecycle.LiveData;

import com.giraffe.foodplannerapplication.models.CategoriesResponse;
import com.giraffe.foodplannerapplication.models.Category;
import com.giraffe.foodplannerapplication.models.CountriesResponse;
import com.giraffe.foodplannerapplication.models.Country;
import com.giraffe.foodplannerapplication.models.Ingredient;
import com.giraffe.foodplannerapplication.models.IngredientsResponse;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.MealsResponse;
import com.giraffe.foodplannerapplication.network.NetworkCallback;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface RepoInterface {
    //optional till now just for testing
    //=================remote functions=================
    void getRandomMeal(NetworkCallback<MealsResponse> callback);
    void getMealById(String mealId,NetworkCallback<Meal> callback);

    void createAccount(String email, String password, NetworkCallback<Boolean> callback);

    void login(String email, String password, NetworkCallback<Boolean> callback);

    boolean logout();

    void getCategories(NetworkCallback<List<Category>> callback);

    void getCountries(NetworkCallback<List<Country>> callback);

    void getIngredients(NetworkCallback<List<Ingredient>> callback);
    Observable<List<Meal>> getCategoryMeals(String category);
    Observable<List<Meal>>  getCountryMeals(String country);
    void getSearchResult(String word,NetworkCallback<List<Meal>> callback);

    //=================local functions=================
    boolean isLoggedIn();

    Observable<List<Meal>> getLocalMeals();

    Completable insertMeal(Meal meal);

    Completable deleteMeal(Meal meal);

}
