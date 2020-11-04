package com.lisap.equus.ui.main.details;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.google.firebase.firestore.DocumentSnapshot;
import com.lisap.equus.R;
import com.lisap.equus.data.entities.HealthCare;
import com.lisap.equus.data.entities.Horse;
import com.lisap.equus.data.entities.Note;
import com.lisap.equus.data.entities.Owner;
import com.lisap.equus.data.firestore.DbHealthCare;
import com.lisap.equus.data.firestore.DbHorse;
import com.lisap.equus.data.firestore.DbNote;
import com.lisap.equus.data.firestore.DbOwner;
import com.lisap.equus.databinding.ActivityHorseDetailsBinding;
import com.lisap.equus.ui.main.MainActivity;
import com.lisap.equus.ui.main.MainAdapter;
import com.lisap.equus.ui.main.notes.NoteListActivity;
import com.lisap.equus.utils.Constants;
import com.lisap.equus.utils.RecyclerViewHolderListener;
import com.lisap.equus.utils.SharedPreferencesManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class HorseDetailsActivity extends AppCompatActivity {

    private ActivityHorseDetailsBinding binding;
    private Horse extraHorse;
    private String specialist;
    private HorseDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHorseDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        extraHorse = (Horse) getIntent().getSerializableExtra("horse");

        initRecycler();

        setFabStoppedListener(extraHorse);
        setSpinner();
        setBtnAddHealthCareClickListener();

        loadHorseData(extraHorse);
        loadOwnerData(extraHorse);
        loadHealthCareData(extraHorse);
    }

    private void loadHorseData(Horse extraHorse) {
        Picasso.get().load(
                Constants.START_URL +
                        extraHorse.getHorseId() +
                        Constants.END_URL
        ).into(binding.activityHorseDetailsPhoto);

        binding.activityHorseDetailsTxtName.setText(extraHorse.getName());
    }

    private void loadOwnerData(Horse extraHorse) {

        if (extraHorse.getOwnerId() != null) {
            DbOwner.getOwnerDocument(
                    SharedPreferencesManager.getStable(this).getIdStable(),
                    extraHorse.getOwnerId()
            ).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.getId() == null)
                        return;

                    Owner owner = document.toObject(Owner.class);
                    binding.activityHorseDetailsOwnerName.setText(owner.getFirstname() + " " + owner.getLastname());
                    binding.activityHorseDetailsOwnerTel.setText(owner.getPhone());
                } else {
                    Toast.makeText(this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            binding.activityHorseDetailsOwnerName.setText("Cheval d'écurie");
            binding.activityHorseDetailsOwnerTel.setVisibility(View.GONE);
        }
    }

    private void loadHealthCareData(Horse extraHorse) {
        DbHealthCare.getHealthCareDocumentList(
                SharedPreferencesManager.getStable(this).getIdStable(),
                extraHorse.getHorseId()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<HealthCare> healthCareList = new ArrayList<>();
                for (DocumentSnapshot document : task.getResult()) {
                    String uid = document.getId();
                    if (uid == null)
                        return;

                    HealthCare healthCare = document.toObject(HealthCare.class);
                    // add uid to note object
                    healthCare.setHealthCareId(uid);
                    healthCareList.add(healthCare);
                }
                adapter.setData(healthCareList);
            } else {
                Toast.makeText(this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initRecycler() {
        RecyclerViewHolderListener listener = new RecyclerViewHolderListener() {
            @Override
            public void onItemClicked(RecyclerView.ViewHolder viewHolder, Object item, int pos) {}

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemLongClicked(RecyclerView.ViewHolder viewHolder, Object item, int pos) {
                HealthCare healthCare = (HealthCare) item;

                final FlatDialog flatDialog = new FlatDialog(HorseDetailsActivity.this);
                flatDialog.setTitle("Options")
                    .setFirstButtonText("SUPPRIMER")
                        .setBackgroundColor(getColor(R.color.colorAccentLight))
                        .setFirstButtonColor(getColor(R.color.colorPrimaryLight))
                        .setSecondButtonColor(getColor(R.color.colordelete))
                        .setTitleColor(getColor(R.color.colorPrimaryDark))
                    .isCancelable(true)
                    .withFirstButtonListner(view -> {
                        DbHealthCare.deleteHealthCareDocument(
                                SharedPreferencesManager.getStable(HorseDetailsActivity.this).getIdStable(),
                                extraHorse.getHorseId(),
                                healthCare.getHealthCareId()
                        ).addOnSuccessListener(documentReference -> {
                            Toast.makeText(HorseDetailsActivity.this, "Soin supprimé", Toast.LENGTH_LONG).show();
                        }).addOnFailureListener(e -> {
                            Toast.makeText(HorseDetailsActivity.this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
                        });

                        loadHealthCareData(extraHorse);
                        flatDialog.dismiss();
                    })
                    .show();
            }
        };

        adapter = new HorseDetailsAdapter(new ArrayList<>(), listener);
        binding.activityHorseDetailsRecycler.setAdapter(adapter);
        binding.activityHorseDetailsRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.activityHorseDetailsRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void setSpinner() {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.spinnerItems, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.activityHorseDetailsSpinner.setAdapter(adapter);
        binding.activityHorseDetailsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                specialist = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void setFabStoppedListener(Horse extraHorse) {

        if (extraHorse.isStopped()) {
            binding.activityHorseDetailsFabStopped.setSelected(true);
            binding.activityHorseDetailsFabStopped.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(HorseDetailsActivity.this,R.color.colorIncident)));
        } else {
            binding.activityHorseDetailsFabStopped.setSelected(false);
            binding.activityHorseDetailsFabStopped.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(HorseDetailsActivity.this, R.color.colorAccentLight)));
        }

        binding.activityHorseDetailsFabStopped.setOnClickListener(view -> {
            if (binding.activityHorseDetailsFabStopped.isSelected()) {
                binding.activityHorseDetailsFabStopped.setSelected(false);
                binding.activityHorseDetailsFabStopped.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(HorseDetailsActivity.this, R.color.colorAccentLight)));

                extraHorse.setStopped(false);
            } else {
                binding.activityHorseDetailsFabStopped.setSelected(true);
                binding.activityHorseDetailsFabStopped.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(HorseDetailsActivity.this,R.color.colorIncident)));

                extraHorse.setStopped(true);
            }

            // update horse
            DbHorse.updateHorseDocument(
                    SharedPreferencesManager.getStable(this).getIdStable(),
                    extraHorse
            );
        });
    }

    private void setBtnAddHealthCareClickListener() {
        binding.activityHorseDetailsCareBtnAdd.setOnClickListener(view -> {
            if (formIsValid()) {
                showBtnLoading();
                HealthCare healthCare = new HealthCare();
                healthCare.setHorseId(extraHorse.getHorseId());
                healthCare.setProblem(binding.activityHorseDetailsEditProblem.getText().toString());
                healthCare.setSpecialist(specialist);

                // save health care on firebase
                DbHealthCare.addHealthCareDocument(
                        SharedPreferencesManager.getStable(this).getIdStable(),
                        extraHorse.getHorseId(),
                        healthCare
                ).addOnSuccessListener(documentReference -> {
                    hideBtnLoading();
                    binding.activityHorseDetailsEditProblem.setText("");
                    Toast.makeText(this, "Nouveau soin ajouté", Toast.LENGTH_LONG).show();

                    loadHealthCareData(extraHorse);
                }).addOnFailureListener(e -> {
                    hideBtnLoading();
                    Toast.makeText(this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private boolean formIsValid() {
        if (!binding.activityHorseDetailsEditProblem.getText().toString().isEmpty()) {
            return true;
        } else {
            Toast.makeText(this, "Merci de spécifier le problème", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void showBtnLoading() {
        binding.activityHorseDetailsCareBtnAdd.setVisibility(View.GONE);
        binding.activityHorseDetailsCareProgress.setVisibility(View.VISIBLE);
    }

    private void hideBtnLoading() {
        binding.activityHorseDetailsCareBtnAdd.setVisibility(View.VISIBLE);
        binding.activityHorseDetailsCareProgress.setVisibility(View.GONE);
    }
}








