package com.giraffe.foodplannerapplication.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "planned_meals_table", primaryKeys = {"day", "type"})
public class PlannedMeal {
    private  Long date;

    private  int day;

    private  int type;

    @TypeConverters(MealConverter.class)
    private  Meal meal;

    public PlannedMeal(){}
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

    public void setDate(Long date) {
        this.date = date;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
}


