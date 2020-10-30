package com.lisap.equus.ui.main.details;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.lisap.equus.R;
import com.lisap.equus.data.entities.Horse;
import com.lisap.equus.databinding.ActivityHorseDetailsBinding;
import com.lisap.equus.utils.Constants;
import com.squareup.picasso.Picasso;


public class HorseDetailsActivity extends AppCompatActivity {

    private ActivityHorseDetailsBinding binding;
    private Horse extraHorse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHorseDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        extraHorse = (Horse) getIntent().getSerializableExtra("horse");

        loadData(extraHorse);
        setFabEditListener();
        setFabStoppedListener();
    }

    private void loadData(Horse extraHorse) {
        Picasso.get().load(
                Constants.START_URL +
                        extraHorse.getHorseId() +
                        Constants.END_URL
        ).into(binding.activityHorseDetailsPhoto);

        binding.activityHorseDetailsName.setText(extraHorse.getName());
    }

    private void setFabEditListener() {

    }

    private void setFabStoppedListener() {
        binding.activityHorseDetailsFabStopped.setOnClickListener(view -> {
            if (binding.activityHorseDetailsFabStopped.isSelected()) {
                binding.activityHorseDetailsFabStopped.setSelected(false);
                binding.activityHorseDetailsFabStopped.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(HorseDetailsActivity.this, R.color.colorAccentLight)));
            } else {
                binding.activityHorseDetailsFabStopped.setSelected(true);
                binding.activityHorseDetailsFabStopped.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(HorseDetailsActivity.this,R.color.colorIncident)));

            }
        });
    }
}








