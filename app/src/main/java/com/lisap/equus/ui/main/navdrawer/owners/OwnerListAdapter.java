package com.lisap.equus.ui.main.navdrawer.owners;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lisap.equus.R;
import com.lisap.equus.data.entities.Owner;
import com.lisap.equus.utils.RecyclerViewHolderListener;

import java.util.List;

public class OwnerListAdapter extends RecyclerView.Adapter<OwnerListAdapter.OwnerListHolder> {

    List<Owner> ownerList;
    RecyclerViewHolderListener listener;

    public OwnerListAdapter(List<Owner> ownerList,
                       RecyclerViewHolderListener listener){
        this.ownerList = ownerList;
        this.listener = listener;
    }

    public static class OwnerListHolder extends RecyclerView.ViewHolder{
        TextView name, phone;

        public OwnerListHolder(View view){
            super(view);
            name = view.findViewById(R.id.activity_owner_list_item_name);
            phone = view.findViewById(R.id.activity_owner_list_item_phone);
        }
    }

    @Override
    public OwnerListHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_owner_list_item, parent,false);
        return new OwnerListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OwnerListHolder holder, final int position) {
        //position liée à la ligne donc change toute seule//
        final Owner owner = ownerList.get(position);

        holder.name.setText(owner.getFirstname() + " " + owner.getLastname());
        holder.phone.setText(owner.getPhone());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(holder, owner, position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onItemLongClicked(holder, owner, position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount(){
        return ownerList.size();
    }

    public void setData(List<Owner> ownerList) {
        this.ownerList = ownerList;
        notifyDataSetChanged();
    }
}