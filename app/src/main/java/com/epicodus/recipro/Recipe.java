package com.epicodus.recipro;

import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class Recipe {
    private String id;
    private String name;
    private ArrayList<String> ingredients;
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

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getSmallImageURL() {
        return smallImageURL;
    }

    public void setSmallImageURL(String smallImageURL) {
        this.smallImageURL = smallImageURL;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
