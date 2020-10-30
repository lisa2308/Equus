package com.lisap.equus.ui.main.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lisap.equus.R;
import com.lisap.equus.data.entities.Note;
import com.lisap.equus.utils.RecyclerViewHolderListener;

import java.util.List;


public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteListHolder>  {

    List<Note> noteList;
    RecyclerViewHolderListener listener;

    public NoteListAdapter(List<Note> noteList,
                           RecyclerViewHolderListener listener){
        this.noteList = noteList;
        this.listener = listener;
    }

    public static class NoteListHolder extends RecyclerView.ViewHolder{
        TextView date, message;

        public NoteListHolder(View view){
            super(view);
            date = view.findViewById(R.id.activity_note_list_item_date);
            message = view.findViewById(R.id.activity_note_list_item_message);
        }
    }

    @Override
    public NoteListAdapter.NoteListHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_note_list_item, parent,false);
        return new NoteListAdapter.NoteListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NoteListAdapter.NoteListHolder holder, final int position) {
        //position liée à la ligne donc change toute seule//
        final Note note = noteList.get(position);

        holder.date.setText(note.getDate());
        holder.message.setText(note.getMessage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(holder, note, position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onItemLongClicked(holder, note, position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount(){
        return noteList.size();
    }

    public void setData(List<Note> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }

}
