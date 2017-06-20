package com.example.bhatt.baking.AdapterWork;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bhatt.baking.Fragment1;
import com.example.bhatt.baking.GridData;
import com.example.bhatt.baking.Gridadpater;
import com.example.bhatt.baking.MainActivity;
import com.example.bhatt.baking.MainActivity2;
import com.example.bhatt.baking.R;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by bhatt on 06-06-2017.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.MyViewHolder> {

    ArrayList<IngredientsDATA> arraylist = new ArrayList<>();
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView ingredienttext;
        TextView quantityandmeasure;
        ImageView ingredientsimage;


        public MyViewHolder(View itemView) {
            super(itemView);
            ingredienttext = (TextView)itemView.findViewById(R.id.ingredienttext);
            quantityandmeasure = (TextView)itemView.findViewById(R.id.quantityandmeasure);
            ingredientsimage = (ImageView)itemView.findViewById(R.id.ingredientsimage);


        }
    }

        public IngredientsAdapter(Activity activity, ArrayList<IngredientsDATA> ArrayList){
            context = activity;
            arraylist = ArrayList;
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ingredianview, parent, false);

            return new IngredientsAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            IngredientsDATA ingredientsDATA = arraylist.get(position);

            holder.ingredienttext.setText(ingredientsDATA.getingredient());

            Glide.with(context)
                    .load(ingredientsDATA.getimage())
                    .thumbnail(0.5f)
                    .into(holder.ingredientsimage);

            holder.quantityandmeasure.setText(ingredientsDATA.getquantitymeasure());

        }

        @Override
        public int getItemCount() {
            return arraylist.size();
        }


}
