package com.lisap.equus.ui.main.addupdatehorse;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.lisap.equus.data.entities.Horse;
import com.lisap.equus.data.entities.Owner;
import com.lisap.equus.data.firestore.DbHorse;
import com.lisap.equus.data.firestore.DbOwner;
import com.lisap.equus.databinding.ActivityHorseAddUpdateBinding;
import com.lisap.equus.utils.Constants;
import com.lisap.equus.data.preferences.SharedPreferencesManager;
import com.lisap.equus.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.lisap.equus.utils.Constants.DATA_UPLOAD_MAX_MEMORY_SIZE;

public class AddUpdateHorseActivity extends AppCompatActivity {

    private ActivityHorseAddUpdateBinding binding;
    private String name;
    private File fileUpload = null;
    private Horse extraHorse;
    String ownerId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHorseAddUpdateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        extraHorse = (Horse) getIntent().getSerializableExtra("horse");

        if (extraHorse == null) {
            setTitle("Ajouter un cheval");
        } else {
            setTitle("Modifier cheval");
            loadData(extraHorse);
        }

        setHorseImageListener();
        setSaveBtnClickListener();
        loadOwnerList();
    }

    private void loadData(Horse extraHorse) {
        binding.activityHorseAddUpdateName.setText(extraHorse.getName());
        Picasso.get().load(
                Constants.START_URL +
                        extraHorse.getHorseId() +
                        Constants.END_URL
        ).into(binding.activityHorseAddUpdateImage);
    }

    private void setHorseImageListener() {
        binding.activityHorseAddUpdateImage.setOnClickListener(view -> {
            askPermissionAndOpenGallery();
        });
    }

    private void loadOwnerList() {
        showOwnerListProgress();

        DbOwner.getOwnerDocumentList(SharedPreferencesManager.getStable(this).getIdStable())
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    List<Owner> ownerList = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        String uid = document.getId();
                        if (uid == null)
                            return;

                        Owner owner = document.toObject(Owner.class);
                        // add uid to owner object
                        owner.setOwnerId(uid);
                        ownerList.add(owner);
                    }
                    setSpinner(ownerList);
                    hideOwnerListProgress();
                } else {
                    Toast.makeText(this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
                    hideOwnerListProgress();
                }
            });
    }

    private void setSpinner(List<Owner> ownerList) {
        // add empty owner at the beginning of the list
        Owner owner = new Owner();
        owner.setOwnerId(null);
        owner.setFirstname("");
        owner.setLastname("");
        owner.setPhone("");
        ownerList.add(0, owner);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ownerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.activityHorseAddUpdateSpinner.setAdapter(adapter);
        binding.activityHorseAddUpdateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Owner owner = (Owner) parent.getSelectedItem();
                ownerId = owner.getOwnerId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        if (extraHorse != null) {
            for (Owner ow : ownerList) {
                if (ow.getOwnerId() == null)
                    continue;
                if (ow.getOwnerId().equals(extraHorse.getOwnerId())) {
                    binding.activityHorseAddUpdateSpinner.setSelection(ownerList.indexOf(ow));
                }
            }
        }
    }

    private void showOwnerListProgress() {
        binding.activityHorseAddUpdateSpinnerProgress.setVisibility(View.VISIBLE);
        binding.activityHorseAddUpdateSpinnerRelative.setVisibility(View.GONE);
        binding.activityHorseAddUpdateBtnSave.setVisibility(View.GONE);
    }

    private void hideOwnerListProgress() {
        binding.activityHorseAddUpdateSpinnerProgress.setVisibility(View.GONE);
        binding.activityHorseAddUpdateSpinnerRelative.setVisibility(View.VISIBLE);
        binding.activityHorseAddUpdateBtnSave.setVisibility(View.VISIBLE);
    }

    private boolean isFormValid() {
        name = binding.activityHorseAddUpdateName.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, "Merci de renseigner un nom", Toast.LENGTH_LONG).show();
            return false;
        } else if (fileUpload == null && extraHorse == null) {
            Toast.makeText(this, "Merci d'ajouter une image", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void setSaveBtnClickListener() {
        binding.activityHorseAddUpdateBtnSave.setOnClickListener(view -> {
            if (isFormValid()) {
                showBtnLoading();

                // creation
                if (extraHorse == null) {
                    Uri file = Uri.fromFile(fileUpload);

                    // create new horse object
                    Horse horse = new Horse();
                    horse.setName(Utils.capitalize(name));
                    horse.setStopped(false);
                    horse.setOwnerId(ownerId);

                    // save horse on firebase
                    DbHorse.addHorseDocument(
                            SharedPreferencesManager.getStable(this).getIdStable(), horse
                    ).addOnSuccessListener(documentReference -> {
                        String horseId = documentReference.getId();

                        // upload file
                        FirebaseStorage.getInstance().getReference(horseId).putFile(file).addOnSuccessListener(taskSnapshot -> {
                            hideBtnLoading();
                            Toast.makeText(this, "Nouveau cheval ajouté", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }).addOnFailureListener(exception -> {
                            hideBtnLoading();
                            Toast.makeText(this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
                        });
                    }).addOnFailureListener(e -> {
                        hideBtnLoading();
                        Toast.makeText(this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
                    });
                }
                // update
                else {
                    extraHorse.setName(Utils.capitalize(name));
                    extraHorse.setOwnerId(ownerId);

                    DbHorse.updateHorseDocument(
                            SharedPreferencesManager.getStable(this).getIdStable(), extraHorse
                    ).addOnSuccessListener(documentReference -> {

                        // don't upload file if user didn't change it
                        if (fileUpload != null) {
                            Uri file = Uri.fromFile(fileUpload);
                            String horseId = extraHorse.getHorseId();

                            // upload file
                            FirebaseStorage.getInstance().getReference(horseId).putFile(file).addOnSuccessListener(taskSnapshot -> {
                                hideBtnLoading();

                                // invalidate cache for image
                                Picasso.get().invalidate(
                                    Constants.START_URL +
                                            horseId +
                                            Constants.END_URL
                                );

                                Toast.makeText(this, "Cheval modifié", Toast.LENGTH_LONG).show();
                                onBackPressed();
                            }).addOnFailureListener(exception -> {
                                hideBtnLoading();
                                Toast.makeText(this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
                            });
                        } else {
                            hideBtnLoading();
                            Toast.makeText(this, "Cheval modifié", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }
                    }).addOnFailureListener(e -> {
                        hideBtnLoading();
                        Toast.makeText(this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
                    });
                }
            }
        });
    }

    private void showBtnLoading() {
        binding.activityHorseAddUpdateBtnSaveText.setVisibility(View.GONE);
        binding.activityHorseAddUpdateBtnSaveProgress.setVisibility(View.VISIBLE);
    }

    private void hideBtnLoading() {
        binding.activityHorseAddUpdateBtnSaveText.setVisibility(View.VISIBLE);
        binding.activityHorseAddUpdateBtnSaveProgress.setVisibility(View.GONE);
    }

    private void askPermissionAndOpenGallery() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
                    
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                ImagePicker.create(AddUpdateHorseActivity.this)
                        .returnMode(ReturnMode.ALL)
                        .folderMode(true)
                        .toolbarFolderTitle("Galerie")
                        .toolbarImageTitle("Sélectionnez une image")
                        .toolbarArrowColor(Color.WHITE)
                        .includeVideo(false)
                        .single()
                        .start();
            }
            
            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            fileUpload = new File(image.getPath());

            if (fileUpload.length() > DATA_UPLOAD_MAX_MEMORY_SIZE) {
                Toast.makeText(this, "Image trop volumineuse", Toast.LENGTH_SHORT).show();
            } else {
                binding.activityHorseAddUpdateImage.setImageURI(Uri.fromFile(fileUpload));
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
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