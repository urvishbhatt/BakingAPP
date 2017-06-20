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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView nama,ingredian,steps;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.dishimageviewid);
            nama = (TextView)itemView.findViewById(R.id.dishname);
            ingredian = (TextView)itemView.findViewById(R.id.ingrediantext);
            steps = (TextView)itemView.findViewById(R.id.stepstext);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

    Gridadpater(Context context, ArrayList<GridData> arraylist, ListItemClickListener listener) {
        this.context = context;
        this.arraylist = arraylist;
        mOnClickListener = listener;
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

        Glide.with(context)
                .load(gridData.getimage())
                .thumbnail(0.5f)
                .into(holder.imageView);

//        holder.imageView.setImageResource(gridData.getimage());

    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    interface ListItemClickListener { void onListItemClick(int clickedItemIndex); }

    final private ListItemClickListener mOnClickListener;

}
