package com.example.bhatt.baking;

import android.content.Context;
import android.content.DialogInterface;
import android.renderscript.Sampler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.ConnectException;
import java.util.ArrayList;

import butterknife.BindView;


/**
 * Created by bhatt on 05-06-2017.
 */

public class Gridadpater extends RecyclerView.Adapter<Gridadpater.MyViewHolder> {

    ArrayList<GridData> arraylist = new ArrayList<>();
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder  {

        public ImageView imageView;
        public TextView nama,ingredian,steps;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.dishimageviewid);
            nama = (TextView)itemView.findViewById(R.id.dishname);
            ingredian = (TextView)itemView.findViewById(R.id.ingrediantext);
            steps = (TextView)itemView.findViewById(R.id.stepstext);
        }


    }

    public Gridadpater(Context context, ArrayList<GridData> arraylist) {
        this.context = context;
        this.arraylist = arraylist;
    }
    @Override
    public Gridadpater.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridview, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(Gridadpater.MyViewHolder holder, int position) {
        GridData gridData = arraylist.get(position);

        holder.nama.setText(gridData.getdishname());
        holder.ingredian.setText(String.valueOf(gridData.getdishTotalingredients()));
        holder.steps.setText(String.valueOf(gridData.getdishsteps()));
        holder.imageView.setImageResource(gridData.getimage());



        final int post = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,String.valueOf(post),Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return arraylist.size();
    }



}
