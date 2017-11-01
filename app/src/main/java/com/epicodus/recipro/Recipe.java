package com.epicodus.recipro;

import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class Recipe {
    private String id;
    private String name;
    private ArrayList ingredients;
    private String time;
    private String course;
    private String cuisine;
    private String smallImageURL;
    private String largeImageURL;
    private String source;


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

    public Recipe(String name, ArrayList<String> ingredients, String time, String course, String cuisine, String smallImageURL) {
        this.name = name;
        this.ingredients = ingredients;
        this.time = time;
        this.course = course;
        this.cuisine = cuisine;
        this.smallImageURL = smallImageURL;
    }

//    public Recipe(String id, String name, ArrayList<String> ingredients, String time, String course, String cuisine) {
//        this.id = id;
//        this.name = name;
//        this.ingredients = ingredients;
//        this.time = time;
//        this.course = course;
//        this.cuisine = cuisine;
//    }

    public Recipe(String id, String name, ArrayList<String> ingredients, String time, String course, String cuisine, String smallImageURL) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.time = time;
        this.course = course;
        this.cuisine = cuisine;
        this.smallImageURL = smallImageURL;
    }

    //no ingredients constructor
    public Recipe(String id, String name, String time, String course, String cuisine, String smallImageURL) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.course = course;
        this.cuisine = cuisine;
        this.smallImageURL = smallImageURL;
    }

    public String getId() {
        return id;
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

    public String getCuisine() {
        return cuisine;
    }

    public String getSource() {
        return source;
    }

    public String getSmallImageURL() {
        return smallImageURL;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    public void setIngredients(ArrayList ingredients) {
        this.ingredients = ingredients;
    }
}
