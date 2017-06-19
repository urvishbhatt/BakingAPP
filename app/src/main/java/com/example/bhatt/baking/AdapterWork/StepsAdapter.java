package com.example.bhatt.baking.AdapterWork;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bhatt.baking.GridData;
import com.example.bhatt.baking.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by bhatt on 07-06-2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.MyViewHolder> {

    ArrayList<IngredientsDATA> arrayList = new ArrayList<>();

    public StepsAdapter(ArrayList<IngredientsDATA> arrayList,ListItemClickListener listItemClickListener){
        this.arrayList = arrayList;
        mOnClickListener = listItemClickListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView stepsdiscription;
        ImageView videoimage;


        public MyViewHolder(View itemView) {
            super(itemView);
            stepsdiscription = (TextView)itemView.findViewById(R.id.stepsdiscription);

            if(itemView.findViewById(R.id.stepvideo) != null){
                videoimage = (ImageView)itemView.findViewById(R.id.stepvideo);
            }


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);

        }
    }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.stepsview,parent,false);

            return new StepsAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            IngredientsDATA ingredientsDATA = arrayList.get(position);

            holder.stepsdiscription.setText(ingredientsDATA.getshortDescription());


            if (holder.videoimage != null){

                Log.e("notwork","notwork");

                if((ingredientsDATA.getvideoURL()).isEmpty()){
                    holder.videoimage.setVisibility(View.INVISIBLE);
                }else {
                    holder.videoimage.setVisibility(View.VISIBLE);
                }
            }


        }

        @Override
        public int getItemCount() { return arrayList.size(); }

    public interface ListItemClickListener { void onListItemClick(int clickedItemIndex);   }

    final private ListItemClickListener mOnClickListener;
}
