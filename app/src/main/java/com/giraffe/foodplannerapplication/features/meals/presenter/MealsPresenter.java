package com.giraffe.foodplannerapplication.features.meals.presenter;

import com.giraffe.foodplannerapplication.features.meals.view.MealsView;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.repository.Repo;

public class MealsPresenter {
    MealsView view;
    Repo repo;

    public MealsPresenter(MealsView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void getMealById(String mealId) {
        view.onGetMeal(repo.getMealById(mealId));
    }

    public void getCategoryMeals(String category) {
        view.onGetMeals(repo.getCategoryMeals(category));
    }

    public void getCountryMeals(String country) {
        view.onGetMeals(repo.getCountryMeals(country));
    }

    public void getFavMeals() {
        view.onGetFavMeals(repo.getFavMeals());
    }

    public void insertMeal(Meal meal) {
        if (meal.getStrInstructions() != null) {
            view.onMealInserted(repo.insertFavMeal(meal));
        } else {
            repo.getMealById(meal.getIdMeal()).subscribe(
                    meal1 -> {
                        view.onMealInserted(repo.insertFavMeal(meal1));
                    }
            );
        }
    }

    public void deleteMeal(Meal meal) {
        view.onMealDeleted(repo.deleteFavMeal(meal));
    }
}
