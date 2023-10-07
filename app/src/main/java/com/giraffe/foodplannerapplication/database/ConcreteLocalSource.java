package com.giraffe.foodplannerapplication.database;

import android.content.Context;

import com.giraffe.foodplannerapplication.models.CategoriesResponse;
import com.giraffe.foodplannerapplication.models.Category;
import com.giraffe.foodplannerapplication.models.CountriesResponse;
import com.giraffe.foodplannerapplication.models.Country;
import com.giraffe.foodplannerapplication.models.Ingredient;
import com.giraffe.foodplannerapplication.models.IngredientsResponse;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.PlannedMeal;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ConcreteLocalSource implements LocalSource {
    private final MealDAO mealDAO;
    private final PlanDAO planDAO;
    private final SharedHelper shared;
    private static ConcreteLocalSource localSource = null;


    private ConcreteLocalSource(Context context) {
        AppDataBase dataBase = AppDataBase.getInstance(context.getApplicationContext());
        shared = SharedHelper.getInstance(context);

        mealDAO = dataBase.getMealDAO();
        planDAO = dataBase.getPlanDAO();
    }

    public static ConcreteLocalSource getInstance(Context context) {
        if (localSource == null) {
            localSource = new ConcreteLocalSource(context);
        }
        return localSource;
    }

    //=================database functions=================
    @Override
    public Completable insertPlannedMeal(PlannedMeal meal) {
        return planDAO.insertMeal(meal).subscribeOn(Schedulers.io());
        //new Thread(()-> mealDAO.insertMeal(meal)).start();
    }

    @Override
    public Completable deletePlannedMeal(PlannedMeal meal) {
        return planDAO.deleteMeal(meal).subscribeOn(Schedulers.io());
        //new Thread(()-> mealDAO.deleteMeal(meal)).start();
    }

    @Override
    public Observable<List<PlannedMeal>> getPlannedMeals(int day) {
        return planDAO.getMeals(day).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<PlannedMeal>> getAllPlannedMeals() {
        return planDAO.getAllMeals().subscribeOn(Schedulers.io());
    }


    @Override
    public Completable insertFavMeal(Meal meal) {
        return mealDAO.insertMeal(meal).subscribeOn(Schedulers.io());
        //new Thread(()-> mealDAO.insertMeal(meal)).start();
    }

    @Override
    public Completable deleteFavMeal(Meal meal) {
        return mealDAO.deleteMeal(meal).subscribeOn(Schedulers.io());
        //new Thread(()-> mealDAO.deleteMeal(meal)).start();
    }

    @Override
    public Observable<List<Meal>> getFavMeals() {
        return mealDAO.getMeals().subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deletePlannedMeals() {
        return planDAO.deleteAllRecords().subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteFavoriteMeals() {
        return mealDAO.deleteAllRecords().subscribeOn(Schedulers.io());
    }

    //=================shared preferences functions=================

    @Override
    public void storeCategories(CategoriesResponse response) {
        Gson gson = new Gson();
        String json = gson.toJson(response);
        shared.store(SharedKeys.categories, json);
    }

    @Override
    public void storeCountries(CountriesResponse response) {
        Gson gson = new Gson();
        String json = gson.toJson(response);
        shared.store(SharedKeys.countries, json);
    }


    @Override
    public void storeIngredients(IngredientsResponse response) {
        Gson gson = new Gson();
        String json = gson.toJson(response);
        shared.store(SharedKeys.ingredients, json);
    }

    @Override
    public void setFirstTime() {
        shared.store(SharedKeys.firstTime, "true");
    }

    @Override
    public Observable<Boolean> isFirstTime() {
        return Observable.just(shared.read(SharedKeys.firstTime) == null).subscribeOn(Schedulers.io());
    }

    @Override
    public List<Category> readCategories() {
        Gson gson = new Gson();
        String json = shared.read(SharedKeys.categories);
        if (json != null) {
            CategoriesResponse response = gson.fromJson(json, CategoriesResponse.class);
            return response.getCategories();
        }
        return null;

    }

    @Override
    public List<Country> readCountries() {
        Gson gson = new Gson();
        String json = shared.read(SharedKeys.countries);
        if (json != null) {
            CountriesResponse response = gson.fromJson(json, CountriesResponse.class);
            return response.getCountries();
        }
        return null;

    }

    @Override
    public List<Ingredient> readIngredients() {
        Gson gson = new Gson();
        String json = shared.read(SharedKeys.ingredients);
        if (json != null) {
            IngredientsResponse response = gson.fromJson(json, IngredientsResponse.class);
            return response.getIngredients();
        }
        return null;
    }


}
