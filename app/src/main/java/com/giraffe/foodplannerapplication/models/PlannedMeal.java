package com.giraffe.foodplannerapplication.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "planned_meals_table", primaryKeys = {"day", "type"})
public class PlannedMeal {
    private final Long date;

    private final int day;

    private final int type;

    @TypeConverters(MealConverter.class)
    private final Meal meal;

    public PlannedMeal(Long date, int day, int type, Meal meal) {
        this.date = date;
        this.day = day;
        this.type = type;
        this.meal = meal;
    }

    public Long getDate() {
        return date;
    }

    public int getDay() {
        return day;
    }

    public int getType() {
        return type;
    }

    public Meal getMeal() {
        return meal;
    }
}


