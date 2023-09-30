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

public interface RepoInterface {
    //optional till now just for testing
    //=================remote functions=================
    void getRandomMeal(NetworkCallback<MealsResponse> callback);

    void createAccount(String email, String password, NetworkCallback<Boolean> callback);

    void login(String email, String password, NetworkCallback<Boolean> callback);

    boolean logout();

    void getCategories(NetworkCallback<List<Category>> callback);

    void getCountries(NetworkCallback<List<Country>> callback);

    void getIngredients(NetworkCallback<List<Ingredient>> callback);

    //=================local functions=================
    boolean isLoggedIn();

    LiveData<List<Meal>> getLocalMeals();

    void insertMeal(Meal meal);

    void deleteMeal(Meal meal);

}
