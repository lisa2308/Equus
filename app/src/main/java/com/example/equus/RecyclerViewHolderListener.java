package com.example.equus;

import androidx.recyclerview.widget.RecyclerView;

public interface RecyclerViewHolderListener <T, VH extends RecyclerView.ViewHolder> {
    void onItemClicked(VH vh, T item, int pos);
}