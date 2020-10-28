package com.lisap.equus.ui.main.navdrawer.owner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lisap.equus.R;
import com.lisap.equus.data.entities.Owner;
import com.lisap.equus.data.entities.Stable;
import com.lisap.equus.data.firestore.DbOwner;
import com.lisap.equus.databinding.ActivityLoginBinding;
import com.lisap.equus.databinding.ActivityOwnerListBinding;
import com.lisap.equus.ui.login.LoginActivity;
import com.lisap.equus.ui.main.navdrawer.owner.addupdate.OwnerAddUpdateActivity;
import com.lisap.equus.utils.RecyclerViewHolderListener;
import com.lisap.equus.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class OwnerListActivity extends AppCompatActivity {

    private ActivityOwnerListBinding binding;
    private OwnerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_list);

        binding = ActivityOwnerListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setTitle("Agenda");

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
                Owner owner = (Owner) item;
                makeCall(owner.getPhone());
            }

            @Override
            public void onItemLongClicked(RecyclerView.ViewHolder viewHolder, Object item, int pos) {
                Owner owner = (Owner) item;

                final FlatDialog flatDialog = new FlatDialog(OwnerListActivity.this);
                flatDialog.setTitle("Options")
                    .setFirstButtonText("MODIFIER")
                    .setSecondButtonText("SUPPRIMER")
                    .isCancelable(true)
                    .withFirstButtonListner(view -> {
                        startAddUpdateOwnerActivity(owner);
                        flatDialog.dismiss();
                    })
                    .withSecondButtonListner(view -> {
                        DbOwner.deleteOwnerDocument(
                            SharedPreferencesManager.getStable(OwnerListActivity.this).getIdStable(),
                            owner.getOwnerId()
                        ).addOnSuccessListener(documentReference -> {
                            Toast.makeText(OwnerListActivity.this, "Propriétaire supprimé", Toast.LENGTH_LONG).show();
                        }).addOnFailureListener(e -> {
                            Toast.makeText(OwnerListActivity.this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
                        });
                        loadRecyclerData();
                        flatDialog.dismiss();
                    })
                    .show();
            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        binding.activityOwnerListRecycler.setLayoutManager(gridLayoutManager);
        adapter = new OwnerListAdapter(new ArrayList<>(), listener);
        binding.activityOwnerListRecycler.setAdapter(adapter);
    }

    private void setFabClickListener() {
        binding.activityOwnerListFabAdd.setOnClickListener(view -> {
            startAddUpdateOwnerActivity(null);
        });
    }

    private void loadRecyclerData() {
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
                    adapter.setData(ownerList);
                } else {
                    Toast.makeText(this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
                }
            });
    }

    private void startAddUpdateOwnerActivity(Owner owner) {
        Intent intent = new Intent(this, OwnerAddUpdateActivity.class);
        intent.putExtra("owner", owner);
        startActivity(intent);
    }

    private void makeCall(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
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