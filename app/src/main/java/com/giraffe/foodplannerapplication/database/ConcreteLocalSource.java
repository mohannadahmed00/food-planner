package com.giraffe.foodplannerapplication.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.giraffe.foodplannerapplication.models.Meal;

import java.util.List;

public class ConcreteLocalSource implements LocalSource{
    MealDAO productDAO;
    private static ConcreteLocalSource localSource = null;
    private final LiveData<List<Meal>> meals;

    private ConcreteLocalSource(Context context){
        AppDataBase dataBase = AppDataBase.getInstance(context.getApplicationContext());
        productDAO = dataBase.getMealDAO();
        //todo here i call getProducts() without thread even though ROOM can't touch main thread???
        ////Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
        meals = productDAO.getMeals();
    }
    public static ConcreteLocalSource getInstance(Context context){
        if (localSource==null){
            localSource = new ConcreteLocalSource(context);
        }
        return localSource;
    }
    @Override
    public void insertMeal(Meal meal) {
        new Thread(()-> productDAO.insertMeal(meal)).start();
    }

    @Override
    public void deleteMeal(Meal meal) {
        new Thread(()-> productDAO.deleteMeal(meal)).start();
    }

    @Override
    public LiveData<List<Meal>> getMeals() {
        return meals;
    }
}
