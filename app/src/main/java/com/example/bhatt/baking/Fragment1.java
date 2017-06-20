package com.example.bhatt.baking;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bhatt.baking.AdapterWork.IngredientsAdapter;
import com.example.bhatt.baking.AdapterWork.IngredientsDATA;
import com.example.bhatt.baking.AdapterWork.StepsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;


/**
 * Created by bhatt on 12-06-2017.
 */

public class Fragment1 extends Fragment implements StepsAdapter.ListItemClickListener{


    /****************************************/
    // app crash when use @BindView for fragment
//    @BindView(R.id.ingredianList) RecyclerView recyclerView1;
//    @BindView(R.id.stepsList) RecyclerView recyclerView2;
    /*******************************************/

    RecyclerView recyclerView1 , recyclerView2;
    ArrayList<IngredientsDATA> list1 = new ArrayList<>();
    ArrayList<IngredientsDATA> list2 = new ArrayList<>();

    InputStream inputStream;
    String JSON;

    int ID;

    View fragment1;

    String description;
    String videourl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        fragment1 = inflater.inflate(R.layout.fragment1,container,false);



        recyclerView1 = (RecyclerView)fragment1.findViewById(R.id.ingredianList);
        recyclerView2 = (RecyclerView)fragment1.findViewById(R.id.stepsList);

        Activity activity = getActivity();


        ID = activity.getIntent().getIntExtra("ID",1);

        getinputStream();
        try {
            getjsondata();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return fragment1;
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

        String quantity;
        String measure;
        String ingredient;
        int image = 0;

        int id;
        String shortDescription;
        String description;
        String videoURL;

        JSONArray jsonarray = new JSONArray(JSON);

        JSONObject getdata = jsonarray.getJSONObject(ID);

        JSONArray josnarrayingredients = getdata.getJSONArray("ingredients");
        JSONArray josnarraysteps = getdata.getJSONArray("steps");

        if(josnarrayingredients!=null){

            for (int i=0;josnarrayingredients.length()>i;i++){

                JSONObject currentobj = josnarrayingredients.getJSONObject(i);

                ingredient = currentobj.getString("ingredient");
                measure = currentobj.getString("measure");
//                quantity = currentobj.getInt("quantity");

                if((currentobj.getDouble("quantity") % 1) != 0){
                    Double que =  currentobj.getDouble("quantity");
                    quantity = Double.toString(que);

                }else {
                    int que = currentobj.getInt("quantity");
                    quantity = Integer.toString(que);
                }

                Log.e("JSON",quantity+"  "+measure+"  "+ingredient);

                switch (measure){

                    case "CUP" : image = R.drawable.cup;
                        break;

                    case "TBLSP" : image = R.drawable.tblsp;
                        break;

                    case "K" : image = R.drawable.k;
                        break;

                    case "G" : image = R.drawable.g;
                        break;

                    case "OZ" : image = R.drawable.oz;
                        break;

                    case "UNIT" : image = R.drawable.unit;
                        break;

                }
                list1.add(new IngredientsDATA(quantity,measure,ingredient,image));
            }
        }


        if (josnarraysteps != null){

            for (int i=0;josnarraysteps.length()>i;i++){

                JSONObject currentobj1 = josnarraysteps.getJSONObject(i);

                shortDescription = currentobj1.getString("shortDescription");
                description = currentobj1.getString("description");
                videoURL = currentobj1.getString("videoURL");

                Log.e("JSON",shortDescription+" "+description+" "+videoURL);

                list2.add(new IngredientsDATA(shortDescription,description,videoURL));
            }
        }

        UpdateUI();

    }
    public void UpdateUI(){

        Activity activity = getActivity();

        IngredientsAdapter adapter = new IngredientsAdapter(activity,list1);
        StepsAdapter adapter1 = new StepsAdapter(list2,this);



        final LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setAdapter(adapter);


        final LinearLayoutManager layoutManager2 = new LinearLayoutManager(activity);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setAdapter(adapter1);

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        IngredientsDATA ingredientsDATA = list2.get(clickedItemIndex);

        description = ingredientsDATA.getdescription();
        videourl = ingredientsDATA.getvideoURL();

        Activity activity = getActivity();

        Toast.makeText(activity,description,Toast.LENGTH_SHORT).show();

        Fragment2 Fragment = (Fragment2) getFragmentManager().findFragmentById(R.id.fragment2);


        if(MainActivity2.issecondfragment){
            Fragment.update(description,videourl,0);
        }else {
            Intent intent = new Intent(activity,MainActivity3.class);
            intent.putExtra("description",description);
            intent.putExtra("videourl",videourl);
            startActivity(intent);
        }
    }
}
