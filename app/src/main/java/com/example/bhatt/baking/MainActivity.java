package com.example.bhatt.baking;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.DisplayMetrics;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Gridadpater.ListItemClickListener {


    RecyclerView recyclerView;

    String JSON;

    String DishName;
    int DishTotalingredients;
    int Dishsteps;

    ArrayList<GridData> arraylist = new ArrayList<>();

    Intent intent;

    final public static String DISCOVERY_FUNCATION = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.RecyclerView);


        if (savedInstanceState == null){
            RecipeAsyncTask recipeAsyncTask = new RecipeAsyncTask();
            recipeAsyncTask.execute();
        }else {
            JSON = savedInstanceState.getString("JSON");

                try {
                    arraylist = getjsondata(JSON);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            UpdateUI(arraylist);
        }

        intent = new Intent(MainActivity.this,MainActivity2.class);
    }

    public class RecipeAsyncTask extends AsyncTask<URL,Void,ArrayList<GridData>>{

        @Override
        protected ArrayList<GridData> doInBackground(URL... params) {
            Log.e("doInBackground","doInBackground");

            URL url = null;

            try {
                url = new URL(DISCOVERY_FUNCATION);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            JSON = null;

            try {
                JSON = httprequest(url);

                Log.v("PROBLEM",JSON);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<GridData> DataArray=null;

            try {
                DataArray = getjsondata(JSON);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return DataArray;
        }

        @Override
        protected void onPostExecute(ArrayList<GridData> gridDatas) {
            if (gridDatas == null){
                return;
            }
            UpdateUI(gridDatas);
        }
    }

    private String httprequest(URL url) throws IOException {

        String jsonResponce = "";

        if(url == null){
            return null;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if(urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();

                jsonResponce = readFromStream(inputStream);
            }
        } catch (ProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if ( urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponce;
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

    private ArrayList<GridData> getjsondata(String JSON) throws JSONException {

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

        return arraylist;

//            UpdateUI();
    }

    public void UpdateUI(ArrayList<GridData> arraylist){

        Gridadpater adapter = new Gridadpater(MainActivity.this,arraylist,MainActivity.this);

        double inch;

        inch = screensize();

        if(inch<=6.0){
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
            }
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                int mNoOfColumns = calculateNoOfColumns();
//                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,mNoOfColumns));
                GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this,mNoOfColumns);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
            }
        }else {
            int mNoOfColumns = calculateNoOfColumns();
//            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,mNoOfColumns));
            GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this,mNoOfColumns);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        intent.putExtra("ID",clickedItemIndex);
        intent.putExtra("JSON",JSON);
        startActivity(intent);
    }

    public  int calculateNoOfColumns() {

        float dpWidth =  0.0f;
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            dpWidth = displayMetrics.heightPixels / displayMetrics.density;
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        }

        double size;

        size = screensize();
        int noOfColumns;

        if(size<=6.0){
            noOfColumns = (int) (dpWidth / 150);
        }else {
            noOfColumns = (int) (dpWidth / 300);
        }

        return noOfColumns;
    }
    public double screensize() {

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        double wi=(double)width/(double)dm.xdpi;
        double hi=(double)height/(double)dm.ydpi;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        double screenInches = Math.sqrt(x+y);

        return screenInches;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("JSON",JSON);
    }
}
