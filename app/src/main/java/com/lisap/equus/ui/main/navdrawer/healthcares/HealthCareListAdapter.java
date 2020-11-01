package com.lisap.equus.ui.main.navdrawer.healthcares;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lisap.equus.R;
import com.lisap.equus.data.entities.Horse;
import com.lisap.equus.data.entities.Owner;
import com.lisap.equus.utils.RecyclerViewHolderListener;

import java.util.List;

public class HealthCareListAdapter extends RecyclerView.Adapter<HealthCareListAdapter.HealthCareListHolder> {

    List<Horse> horseList;
    RecyclerViewHolderListener listener;

    public HealthCareListAdapter(List<Horse> horseList,
                                 RecyclerViewHolderListener listener){
        this.horseList = horseList;
        this.listener = listener;
    }

    public static class HealthCareListHolder extends RecyclerView.ViewHolder{
        TextView name;

        public HealthCareListHolder(View view){
            super(view);
            name = view.findViewById(R.id.activity_healthcare_list_item_name);
        }
    }

    @Override
    public HealthCareListHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_healthcare_list_item, parent,false);
        return new HealthCareListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HealthCareListHolder holder, final int position) {
        //position liée à la ligne donc change toute seule//
        final Horse horse = horseList.get(position);

        holder.name.setText(horse.getName());
        holder.itemView.setOnClickListener(view -> listener.onItemClicked(holder, horse, position));
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