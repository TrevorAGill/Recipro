package com.epicodus.recipro;

import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class Recipe {
    private String name;
    private ArrayList ingredients;
    private String time;
    private String course;
    private String cuisine;
    private String flavor;
    private String rating;

    //For use by Parceler
    public Recipe() {

    }

    public Recipe(String name, ArrayList<String> ingredients, String time, String course, String cuisine) {
        this.name = name;
        this.ingredients = ingredients;
        this.time = time;
        this.course = course;
        this.cuisine = cuisine;
    }

    public Recipe(String name, ArrayList<String> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public String getTime() {
        return time;
    }

    public String getCourse() {
        return course;
    }

    public String getFlavor() {
        return flavor;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getRating() {
        return rating;
    }
}
