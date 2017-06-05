package com.example.bhatt.baking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.RecyclerView) RecyclerView recyclerView;

    InputStream inputStream;
    String JSON;

    int DishID;
    String DishName;
    int DishTotalingredients;
    int Dishsteps;

    ArrayList<GridData> arraylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getinputStream();

        try {
            getjsondata();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    private void getinputStream(){
        inputStream = getResources().openRawResource(R.raw.bakingappjson);
        try {
            JSON = readFromStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder builder = new StringBuilder();

        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader readre = new BufferedReader(inputStreamReader);
            String line = readre.readLine();
            while (line != null){
                builder.append(line);
                line = readre.readLine();
            }
        }
        return builder.toString();
    }
    private void getjsondata() throws JSONException {

        int image = 0;

        JSONArray jsonarray = new JSONArray(JSON);

            if (jsonarray.length()>0){

                for(int i=0;jsonarray.length()>i;i++){

                    JSONObject getdata = jsonarray.getJSONObject(i);

                    DishName = getdata.getString("name");

                    JSONArray getingredients = getdata.getJSONArray("ingredients");
                    DishTotalingredients = getingredients.length();

                    JSONArray getsteps = getdata.getJSONArray("steps");
                    Dishsteps = getsteps.length();

                    Log.e("getjsondata",DishName+String.valueOf(DishTotalingredients)+String.valueOf(Dishsteps));

                    if( i == 0){
                         image = R.drawable.nutellapie;
                    }if (i == 1){
                        image = R.drawable.rsz_brownies;
                    }if (i == 2){
                        image = R.drawable.rsz_yellowcake;
                    }if (i == 3){
                        image = R.drawable.rsz_cheesecake;
                    }

                    arraylist.add(new GridData(DishName,DishTotalingredients,Dishsteps,image));
                }
            }


        UpdateUI();
    }
    public void UpdateUI(){

//        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.RecyclerView);

        Gridadpater adapter = new Gridadpater(MainActivity.this,arraylist);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
