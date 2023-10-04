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
    Observable<Meal> getRandomMeal();
    Observable<Meal> getMealById(String mealId);

    void createAccount(String email, String password, NetworkCallback<Boolean> callback);

    void login(String email, String password, NetworkCallback<Boolean> callback);

    boolean logout();

    Observable<List<Category>> getCategories();

    Observable<List<Country>> getCountries();

    Observable<List<Ingredient>> getIngredients();
    Observable<List<Meal>> getCategoryMeals(String category);
    Observable<List<Meal>>  getCountryMeals(String country);
    Observable<List<Meal>> getSearchResult(String word);

    //=================local functions=================
    boolean isLoggedIn();

    Observable<List<Meal>> getLocalMeals();

    Completable insertMeal(Meal meal);

    Completable deleteMeal(Meal meal);

}
