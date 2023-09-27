package com.giraffe.foodplannerapplication.models;

public class Ingredient {
    private final String idIngredient;
    private final String strIngredient;
    private final String strDescription;
    private final Object strType;

    public Ingredient(String idIngredient, String strIngredient, String strDescription, Object strType) {
        this.idIngredient = idIngredient;
        this.strIngredient = strIngredient;
        this.strDescription = strDescription;
        this.strType = strType;
    }

    public String getIdIngredient() {
        return idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public Object getStrType() {
        return strType;
    }
}