package com.epicodus.recipro;

import org.parceler.Parcel;

@Parcel
public class Recipe {
    private String name;
    private String[] ingredients;
    private String time;
    private String course;
    private String cuisine;
    private String flavor;
    private String rating;

    //For use by Parceler
    public Recipe() {

    }

    public Recipe(String name, String[] ingredients, String time, String course, String cuisine, String rating) {
        this.name = name;
        this.ingredients = ingredients;
        this.time = time;
        this.course = course;
        this.cuisine = cuisine;
        this.rating = rating;
    }

    public Recipe(String name, String[] ingredients, String time, String course, String cuisine) {
        this(name, ingredients, time, course, cuisine,"");
    }

    public String getName() {
        return name;
    }

    public String[] getIngredients() {
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
