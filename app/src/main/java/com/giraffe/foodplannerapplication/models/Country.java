package com.giraffe.foodplannerapplication.models;


import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Random;

public class Country {

    private final String strArea;

    private String strColor;

    public Country(String strArea) {
        this.strArea = strArea;
    }

    public String getStrArea() {
        return strArea;
    }

    public String getStrColor(){
        return strColor;
    }

    public void setStrColor(String color){
        this.strColor = color;
    }
}