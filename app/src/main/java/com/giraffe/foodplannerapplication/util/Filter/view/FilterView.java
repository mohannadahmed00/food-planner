package com.giraffe.foodplannerapplication.util.Filter.view;

import com.giraffe.foodplannerapplication.models.Category;
import com.giraffe.foodplannerapplication.models.Country;
import com.giraffe.foodplannerapplication.models.Ingredient;

import java.util.List;

public interface FilterView {
    void onGetCategories(List<Category> categories);

    void onGetCountries(List<Country> countries);

    void onGetIngredient(List<Ingredient> ingredients);
}
