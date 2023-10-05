package com.giraffe.foodplannerapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;

import com.giraffe.foodplannerapplication.features.meals.presenter.MealsPresenter;
import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.MealConverter;
import com.giraffe.foodplannerapplication.models.PlannedMeal;

@Database(entities = {Meal.class, PlannedMeal.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance = null;

    public abstract MealDAO getMealDAO();//ROOM implement its' CURD functions
    public abstract PlanDAO getPlanDAO();//ROOM implement its' CURD functions

    public static synchronized AppDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "appdb").build();
        }
        return instance;
    }

}
