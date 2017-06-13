package com.example.bhatt.baking;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
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

public class MainActivity2 extends AppCompatActivity  {



    Fragment1 fragment1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){

            Log.e("savedInstanceState","savedInstanceState1");

            fragment1 = new Fragment1();
            fragment1.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.fragment1,fragment1,"fragment1").commit();

        }else {
            Log.e("savedInstanceState","savedInstanceState2");

            fragment1 = (Fragment1)this.getFragmentManager().findFragmentByTag("fragment1");

            if (fragment1 != null){
                fragment1 = new Fragment1();
                fragment1.setArguments(getIntent().getExtras());
                getFragmentManager().beginTransaction().add(R.id.fragment1,fragment1,"fragment1").commit();
            }
        }
    }



}
