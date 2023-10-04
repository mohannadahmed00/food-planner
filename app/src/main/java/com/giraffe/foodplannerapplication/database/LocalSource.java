package com.giraffe.foodplannerapplication.database;

import androidx.lifecycle.LiveData;

import com.giraffe.foodplannerapplication.models.CategoriesResponse;
import com.giraffe.foodplannerapplication.models.Category;
import com.giraffe.foodplannerapplication.models.CountriesResponse;
import com.giraffe.foodplannerapplication.models.Country;
import com.giraffe.foodplannerapplication.models.Ingredient;
import com.giraffe.foodplannerapplication.models.IngredientsResponse;
import com.giraffe.foodplannerapplication.models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface LocalSource {
    //optional till now just for testing
    Completable insertMeal(Meal meal);

    Completable deleteMeal(Meal meal);

    Observable<List<Meal>> getMeals();

    void storeCategories(CategoriesResponse response);
    void storeCountries(CountriesResponse response);
    void storeIngredients(IngredientsResponse response);

    List<Category> readCategories();
    List<Country> readCountries();
    List<Ingredient> readIngredients();

}
