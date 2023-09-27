package com.giraffe.foodplannerapplication.models;

import java.util.List;

public class CategoriesResponse {
    private final List<Category> categories;

    public CategoriesResponse(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
