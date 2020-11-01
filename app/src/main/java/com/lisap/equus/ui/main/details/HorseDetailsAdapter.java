package com.lisap.equus.ui.main.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lisap.equus.R;
import com.lisap.equus.data.entities.HealthCare;
import com.lisap.equus.data.entities.Note;
import com.lisap.equus.utils.RecyclerViewHolderListener;

import java.util.List;


public class HorseDetailsAdapter extends RecyclerView.Adapter<HorseDetailsAdapter.HorseDetailsHolder>  {

    List<HealthCare> healthCareList;
    RecyclerViewHolderListener listener;

    public HorseDetailsAdapter(List<HealthCare> healthCareList,
                               RecyclerViewHolderListener listener){
        this.healthCareList = healthCareList;
        this.listener = listener;
    }

    public static class HorseDetailsHolder extends RecyclerView.ViewHolder{
        TextView specialist, problem;

        public HorseDetailsHolder(View view){
            super(view);
            specialist = view.findViewById(R.id.activity_horse_details_item_txt_specialist);
            problem = view.findViewById(R.id.activity_horse_details_item_txt_problem);
        }
    }

    @Override
    public HorseDetailsHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_horse_details_item, parent,false);
        return new HorseDetailsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HorseDetailsHolder holder, final int position) {
        //position liée à la ligne donc change toute seule//
        final HealthCare healthCare = healthCareList.get(position);

        holder.specialist.setText(healthCare.getSpecialist());
        holder.problem.setText(healthCare.getProblem());

        holder.itemView.setOnClickListener(view -> {
            listener.onItemClicked(holder, healthCare, position);
        });

        holder.itemView.setOnLongClickListener(view -> {
            listener.onItemLongClicked(holder, healthCare, position);
            return true;
        });
    }

    @Override
    public int getItemCount(){
        return healthCareList.size();
    }

    public void setData(List<HealthCare> healthCareList) {
        this.healthCareList = healthCareList;
        notifyDataSetChanged();
    }
}
