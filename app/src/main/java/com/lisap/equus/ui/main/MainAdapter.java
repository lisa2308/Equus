package com.lisap.equus.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lisap.equus.R;
import com.lisap.equus.data.entities.Note;
import com.lisap.equus.utils.Constants;
import com.lisap.equus.utils.RecyclerViewHolderListener;
import com.lisap.equus.data.entities.Horse;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {

    List<Horse> horseList;
    RecyclerViewHolderListener listener;

    public MainAdapter(List<Horse> horseList, RecyclerViewHolderListener listener){
        this.horseList = horseList;
        this.listener = listener;
    }

    public static class MainHolder extends RecyclerView.ViewHolder{
        ImageView photo;
        TextView name;

        public MainHolder(View view){
            super(view);
            photo = view.findViewById(R.id.activity_main_item_photo);
            name = view.findViewById(R.id.activity_main_item_name);
        }
    }

    @Override
    public MainHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item,parent,false);
        return new MainHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MainHolder holder, final int position) {
        //position liée à la ligne donc change toute seule//
        final Horse horse = horseList.get(position);

        holder.name.setText(horse.getName());
        Picasso.get().load(
                Constants.START_URL +
                horse.getHorseId() +
                        Constants.END_URL
        ).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(holder.photo);

        holder.itemView.setOnClickListener(view -> {
            listener.onItemClicked(holder, horse, position);
        });

        holder.itemView.setOnLongClickListener(view -> {
            listener.onItemLongClicked(holder, horse, position);
            return true;
        });
    }

    @Override
    public int getItemCount(){
        return horseList.size();
    }

    public void setData(List<Horse> horseList) {
        this.horseList = horseList;
        notifyDataSetChanged();
    }
}

