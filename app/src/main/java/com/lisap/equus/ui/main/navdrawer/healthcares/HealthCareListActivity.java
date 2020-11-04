package com.lisap.equus.ui.main.navdrawer.healthcares;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.google.firebase.firestore.DocumentSnapshot;
import com.lisap.equus.R;
import com.lisap.equus.data.entities.HealthCare;
import com.lisap.equus.data.entities.Horse;
import com.lisap.equus.data.entities.Owner;
import com.lisap.equus.data.firestore.DbHealthCare;
import com.lisap.equus.data.firestore.DbHorse;
import com.lisap.equus.data.firestore.DbOwner;
import com.lisap.equus.databinding.ActivityHealthcareListBinding;
import com.lisap.equus.databinding.ActivityOwnerListBinding;
import com.lisap.equus.ui.main.MainActivity;
import com.lisap.equus.ui.main.MainAdapter;
import com.lisap.equus.ui.main.details.HorseDetailsActivity;
import com.lisap.equus.ui.main.navdrawer.owners.addupdate.OwnerAddUpdateActivity;
import com.lisap.equus.utils.RecyclerViewHolderListener;
import com.lisap.equus.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class HealthCareListActivity extends AppCompatActivity {

    private ActivityHealthcareListBinding binding;
    private HealthCareListAdapter adapter;
    private List<Horse> horseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthcare_list);

        binding = ActivityHealthcareListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setTitle("Soins des chevaux");

        initRecycler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        horseList.clear();
        loadRecyclerData();
    }

    private void initRecycler() {
        RecyclerViewHolderListener listener = new RecyclerViewHolderListener() {
            @Override
            public void onItemClicked(RecyclerView.ViewHolder viewHolder, Object item, int pos) {
                Horse horse = (Horse) item;
                Intent i = new Intent(HealthCareListActivity.this, HorseDetailsActivity.class);
                i.putExtra("horse", horse);
                startActivity(i);
            }

            @Override
            public void onItemLongClicked(RecyclerView.ViewHolder viewHolder, Object item, int pos) {}
        };

        horseList = new ArrayList<>();
        adapter = new HealthCareListAdapter(horseList, listener);
        binding.activityHealthcareListRecycler.setAdapter(adapter);
        binding.activityHealthcareListRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.activityHealthcareListRecycler.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    private void loadRecyclerData() {
        DbHorse.getHorseDocumentList(SharedPreferencesManager.getStable(this).getIdStable())
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        String uid = document.getId();
                        if (uid == null)
                            return;

                        Horse horse = document.toObject(Horse.class);
                        horse.setHorseId(uid);

                        shouldAddHorseToList(horse);
                    }
                } else {
                    Toast.makeText(this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
                }
            });
    }

    private void shouldAddHorseToList(Horse horse) {
        DbHealthCare.getHealthCareDocumentList(
                SharedPreferencesManager.getStable(this).getIdStable(),
                horse.getHorseId()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (!task.getResult().isEmpty()) {
                    horseList.add(horse);
                    adapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
            }
        });
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