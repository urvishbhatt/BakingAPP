package com.example.bhatt.baking;

/**
 * Created by bhatt on 05-06-2017.
 */

public class GridData {

    String dishName;
    int dishTotalingredients;
    int dishsteps;
    int image;


    public GridData(String dishName, int dishTotalingredients, int dishsteps, int image) {
        this.dishName = dishName;
        this.dishTotalingredients = dishTotalingredients;
        this.dishsteps = dishsteps;
        this.image = image;
    }

    public String getdishname(){  return dishName; }
    public int getdishTotalingredients() { return dishTotalingredients;}
    public int getdishsteps() { return dishsteps; }
    public int getimage() { return image; }
}
