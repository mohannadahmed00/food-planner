package com.giraffe.foodplannerapplication.models.repository;

import com.giraffe.foodplannerapplication.models.Category;
import com.giraffe.foodplannerapplication.models.Country;
import com.giraffe.foodplannerapplication.models.Ingredient;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.PlannedMeal;
import com.giraffe.foodplannerapplication.network.NetworkCallback;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface RepoInterface {
    //optional till now just for testing
    //=================remote functions=================
    Observable<Meal> getRandomMeal();

    Observable<Meal> getMealById(String mealId);

    Completable createAccount(String email, String password);

    Completable login(String email, String password);

    Completable logout();

    Completable loginWithGoogle(String idToken);

    Observable<List<Category>> getCategories();

    Observable<List<Country>> getCountries();

    Observable<List<Ingredient>> getIngredients();

    Observable<List<Meal>> getCategoryMeals(String category);

    Observable<List<Meal>> getCountryMeals(String country);

    Observable<List<Meal>> getSearchResult(String word);

    //=================local functions=================
    Observable<Boolean> isLoggedIn();

    Observable<List<Meal>> getFavMeals();

    Completable insertFavMeal(Meal meal);

    Completable deleteFavMeal(Meal meal);


    Observable<List<PlannedMeal>> getPlannedMeals(int day);

    Completable insertPlannedMeal(PlannedMeal meal);

    Completable deletePlannedMeal(PlannedMeal meal);

    Observable<Boolean> isFirstTime();
    Completable deletePlannedMeals();
    Completable deleteFavoriteMeals();

    void setFirstTime();

}
