package com.lisap.equus.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lisap.equus.R;
import com.lisap.equus.utils.RecyclerViewHolderListener;
import com.lisap.equus.data.entities.Horse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerHolder> {

    List<Horse> horseList;
    RecyclerViewHolderListener listener;

    public RecyclerViewAdapter(List<Horse> horseList, RecyclerViewHolderListener listener){
        this.horseList = horseList;
        this.listener = listener;
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        ImageView photo;
        TextView name;
        TextView proprietaire;
        TextView telProprietaire;


        public RecyclerHolder(View view){
            super(view);
            photo = view.findViewById(R.id.activity_main_item_photo);
            name = view.findViewById(R.id.activity_main_item_name);
            proprietaire = view.findViewById(R.id.activity_horse_details_proprietaire);
            telProprietaire = view.findViewById(R.id.activity_horse_details_numTel);
        }
    }

    @Override
    public RecyclerHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item,parent,false);
        return new RecyclerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerHolder holder, final int position) {

        //position liée à la ligne donc change toute seule//
        final Horse horse = horseList.get(position);

        holder.name.setText(horse.getName());
        Picasso.get().load(horse.getPhoto()).into(holder.photo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(holder,horse,position);
            }
        });
    }
    @Override
    public int getItemCount(){
        return horseList.size();
    }
}

