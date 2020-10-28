package com.lisap.equus.ui.main.navdrawer.owner.addupdate;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.lisap.equus.R;
import com.lisap.equus.data.entities.Owner;
import com.lisap.equus.data.firestore.DbOwner;
import com.lisap.equus.databinding.ActivityOwnerAddUpdateBinding;
import com.lisap.equus.databinding.ActivityOwnerListBinding;
import com.lisap.equus.utils.SharedPreferencesManager;

public class OwnerAddUpdateActivity extends AppCompatActivity {

    private ActivityOwnerAddUpdateBinding binding;
    private String lastname, firstname, phone;
    private Owner extraOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_add_update);

        binding = ActivityOwnerAddUpdateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        extraOwner = (Owner) getIntent().getSerializableExtra("owner");

        if (extraOwner == null) {
            setTitle("Ajouter un propriétaire");
        } else {
            setTitle("Modifier propriétaire");
            loadData(extraOwner);
        }

        setSaveBtnClickListener();
    }

    private void loadData(Owner owner) {
        binding.activityOwnerAddUpdateEditLastname.setText(owner.getLastname());
        binding.activityOwnerAddUpdateEditFirstname.setText(owner.getFirstname());
        binding.activityOwnerAddUpdateEditPhone.setText(owner.getPhone());
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

    private boolean isFormValid() {
        lastname = binding.activityOwnerAddUpdateEditLastname.getText().toString();
        firstname = binding.activityOwnerAddUpdateEditFirstname.getText().toString();
        phone = binding.activityOwnerAddUpdateEditPhone.getText().toString();

        if (lastname.isEmpty()) {
            Toast.makeText(this, "Merci de renseigner un nom", Toast.LENGTH_LONG).show();
            return false;
        } else if (firstname.isEmpty()) {
            Toast.makeText(this, "Merci de renseigner un prénom", Toast.LENGTH_LONG).show();
            return false;
        } else if (phone.isEmpty()) {
            Toast.makeText(this, "Merci de renseigner un numéro de téléphone", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void setSaveBtnClickListener() {
        binding.activityOwnerAddUpdateBtnSave.setOnClickListener(view -> {
            if (isFormValid()) {
                showBtnLoading();

                // creation
                if (extraOwner == null) {
                    // create new owner object
                    Owner owner = new Owner();
                    owner.setLastname(lastname);
                    owner.setFirstname(firstname);
                    owner.setPhone(phone);

                    // save owner on firebase
                    DbOwner.addOwnerDocument(
                            SharedPreferencesManager.getStable(this).getIdStable(), owner
                    ).addOnSuccessListener(documentReference -> {
                        hideBtnLoading();
                        Toast.makeText(this, "Nouveau propriétaire ajouté", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }).addOnFailureListener(e -> {
                        hideBtnLoading();
                        Toast.makeText(this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
                    });
                }
                // update
                else {
                    extraOwner.setLastname(lastname);
                    extraOwner.setFirstname(firstname);
                    extraOwner.setPhone(phone);

                    DbOwner.updateOwnerDocument(
                            SharedPreferencesManager.getStable(this).getIdStable(), extraOwner
                    ).addOnSuccessListener(documentReference -> {
                        hideBtnLoading();
                        Toast.makeText(this, "Propriétaire mofifié", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }).addOnFailureListener(e -> {
                        hideBtnLoading();
                        Toast.makeText(this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
                    });
                }
            }
        });
    }

    private void showBtnLoading() {
        binding.activityOwnerAddUpdateBtnSaveText.setVisibility(View.GONE);
        binding.activityOwnerAddUpdateBtnSaveProgress.setVisibility(View.VISIBLE);
    }

    private void hideBtnLoading() {
        binding.activityOwnerAddUpdateBtnSaveText.setVisibility(View.VISIBLE);
        binding.activityOwnerAddUpdateBtnSaveProgress.setVisibility(View.GONE);
    }
}
