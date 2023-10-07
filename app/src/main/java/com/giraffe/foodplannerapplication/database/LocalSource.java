package com.giraffe.foodplannerapplication.database;

import com.giraffe.foodplannerapplication.models.CategoriesResponse;
import com.giraffe.foodplannerapplication.models.Category;
import com.giraffe.foodplannerapplication.models.CountriesResponse;
import com.giraffe.foodplannerapplication.models.Country;
import com.giraffe.foodplannerapplication.models.Ingredient;
import com.giraffe.foodplannerapplication.models.IngredientsResponse;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.PlannedMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface LocalSource {
    //optional till now just for testing

    Completable deletePlannedMeals();
    Completable deleteFavoriteMeals();

    Completable insertPlannedMeal(PlannedMeal meal);

    Completable deletePlannedMeal(PlannedMeal meal);

    Observable<List<PlannedMeal>> getPlannedMeals(int day);

    Completable insertFavMeal(Meal meal);

    Completable deleteFavMeal(Meal meal);

    Observable<List<Meal>> getFavMeals();

    void storeCategories(CategoriesResponse response);

    void storeCountries(CountriesResponse response);

    void storeIngredients(IngredientsResponse response);

    void setFirstTime();

    Observable<Boolean> isFirstTime();

    List<Category> readCategories();

    List<Country> readCountries();

    List<Ingredient> readIngredients();

}
