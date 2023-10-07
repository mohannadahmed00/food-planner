package com.giraffe.foodplannerapplication.features.home.Filter.view;

import com.giraffe.foodplannerapplication.models.Category;
import com.giraffe.foodplannerapplication.models.Country;
import com.giraffe.foodplannerapplication.models.Ingredient;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface FilterView {
    void onGetCategories(Observable<List<Category>> observable);

    void onGetCountries(Observable<List<Country>> observable);

    void onGetIngredient(Observable<List<Ingredient>> observable);
}
