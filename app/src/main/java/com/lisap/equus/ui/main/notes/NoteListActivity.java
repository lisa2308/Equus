package com.lisap.equus.ui.main.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.google.firebase.firestore.DocumentSnapshot;
import com.lisap.equus.R;
import com.lisap.equus.data.entities.Note;
import com.lisap.equus.data.firestore.DbNote;
import com.lisap.equus.databinding.ActivityNoteListBinding;

import com.lisap.equus.ui.main.notes.addupdate.NoteAddUpdateActivity;
import com.lisap.equus.utils.RecyclerViewHolderListener;
import com.lisap.equus.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {
    private ActivityNoteListBinding binding;
    private NoteListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        binding = ActivityNoteListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setTitle("Notes");

        initRecycler();
        setFabClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecyclerData();
    }

    private void initRecycler() {
        RecyclerViewHolderListener listener = new RecyclerViewHolderListener() {
            @Override
            public void onItemClicked(RecyclerView.ViewHolder viewHolder, Object item, int pos) {
                Note note = (Note) item;
            }

            @Override
            public void onItemLongClicked(RecyclerView.ViewHolder viewHolder, Object item, int pos) {
                Note note = (Note) item;

                final FlatDialog flatDialog = new FlatDialog(NoteListActivity.this);
                flatDialog.setTitle("Options")
                        .setFirstButtonText("MODIFIER")
                        .setSecondButtonText("SUPPRIMER")
                        .isCancelable(true)
                        .withFirstButtonListner(view -> {
                            startAddUpdateNoteActivity(note);
                            flatDialog.dismiss();
                        })
                        .withSecondButtonListner(view -> {
                            DbNote.deleteNoteDocument(
                                    SharedPreferencesManager.getStable(NoteListActivity.this).getIdStable(),
                                    note.getNoteId()
                            ).addOnSuccessListener(documentReference -> {
                                Toast.makeText(NoteListActivity.this, "Note supprimée", Toast.LENGTH_LONG).show();
                            }).addOnFailureListener(e -> {
                                Toast.makeText(NoteListActivity.this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
                            });
                            loadRecyclerData();
                            flatDialog.dismiss();
                        })
                        .show();
            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        binding.activityNoteListRecycler.setLayoutManager(gridLayoutManager);
        adapter = new NoteListAdapter(new ArrayList<>(), listener);
        binding.activityNoteListRecycler.setAdapter(adapter);
    }

    private void setFabClickListener() {
        binding.activityNoteListFabAdd.setOnClickListener(view -> {
            startAddUpdateNoteActivity(null);
        });
    }

    private void loadRecyclerData() {
        DbNote.getNoteDocumentList(SharedPreferencesManager.getStable(this).getIdStable())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Note> noteList = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            String uid = document.getId();
                            if (uid == null)
                                return;

                            Note note = document.toObject(Note.class);
                            // add uid to note object
                            note.setNoteId(uid);
                            noteList.add(note);
                        }
                        adapter.setData(noteList);
                    } else {
                        Toast.makeText(this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void startAddUpdateNoteActivity(Note note) {
        Intent intent = new Intent(this, NoteAddUpdateActivity.class);
        intent.putExtra("note", note);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }
}
