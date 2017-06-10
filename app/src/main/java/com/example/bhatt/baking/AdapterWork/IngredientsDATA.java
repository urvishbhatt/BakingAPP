package com.example.bhatt.baking.AdapterWork;

/**
 * Created by bhatt on 06-06-2017.
 */

public class IngredientsDATA {

    String quantity;
    String measure;
    String ingredient;
    int image;

    String shortDescription;
    String description;
    String videoURL;



    public IngredientsDATA(String quantity, String measure, String ingredient, int image) {

        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
        this.image = image;
    }

    public IngredientsDATA(String shortDescription, String description, String videoURL) {
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
    }



    public String getquantitymeasure() { return quantity+measure;}
    public String getingredient() { return ingredient; }
    public int getimage() { return image; }

    public String getshortDescription(){ return shortDescription; }
    public String getdescription() { return description; }
    public String getvideoURL() { return videoURL; }

}
