package com.epicodus.recipro;

public class Recipe {
    private String name;
    private String[] ingredients;
    private String time;
    private String course;
    private String flavor;
    private String cuisine;
    private String rating;

    public Recipe(String name, String[] ingredients, String time, String course) {
        this.name = name;
        this.ingredients = ingredients;
        this.time = time;
        this.course = course;
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
