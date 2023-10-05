package com.giraffe.foodplannerapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.giraffe.foodplannerapplication.models.Meal;
import com.giraffe.foodplannerapplication.models.PlannedMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface PlanDAO {
    @Query("SELECT * FROM planned_meals_table")
    Observable<List<PlannedMeal>> getMeals();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMeal(PlannedMeal meal);

    @Delete
    Completable deleteMeal(PlannedMeal meal);
}
