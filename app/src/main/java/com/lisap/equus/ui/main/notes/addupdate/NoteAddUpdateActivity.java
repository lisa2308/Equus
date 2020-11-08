package com.lisap.equus.ui.main.notes.addupdate;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.lisap.equus.R;
import com.lisap.equus.data.entities.Note;
import com.lisap.equus.data.firestore.DbNote;
import com.lisap.equus.data.retrofit.RetrofitManager;
import com.lisap.equus.databinding.ActivityNoteAddUpdateBinding;
import com.lisap.equus.data.preferences.SharedPreferencesManager;
import com.lisap.equus.utils.Utils;

import org.json.JSONObject;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteAddUpdateActivity extends AppCompatActivity {
    private ActivityNoteAddUpdateBinding binding;
    private String date, message;
    private Note extraNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add_update);

        binding = ActivityNoteAddUpdateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        extraNote = (Note) getIntent().getSerializableExtra("note");

        if (extraNote == null) {
            setTitle("Ajouter une note");
        } else {
            setTitle("Modifier la note");
            loadData(extraNote);
        }

        setEditDateClickListener();
        setSaveBtnClickListener();
    }

    private void loadData(Note note) {
        binding.activityNoteAddUpdateEditDate.setText(note.getDate());
        binding.activityNoteAddUpdateEditMessage.setText(note.getMessage());
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
        date = binding.activityNoteAddUpdateEditDate.getText().toString();
        message = binding.activityNoteAddUpdateEditMessage.getText().toString();

        if (date.isEmpty()) {
            Toast.makeText(this, "Merci de renseigner une date", Toast.LENGTH_LONG).show();
            return false;
        } else if (message.isEmpty()) {
            Toast.makeText(this, "Merci de renseigner un message", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void setEditDateClickListener() {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener datePickerListener = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            binding.activityNoteAddUpdateEditDate.setText(Utils.getDateInRightFormat(calendar.getTime()));
        };

        binding.activityNoteAddUpdateEditDate.setOnClickListener(view -> {
            new DatePickerDialog(
                    this,
                    datePickerListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });
    }

    private void setSaveBtnClickListener() {
        binding.activityNoteAddUpdateBtnSave.setOnClickListener(view -> {
            if (isFormValid()) {
                showBtnLoading();

                // creation
                if (extraNote == null) {
                    // create new note object
                    Note note = new Note();
                    note.setDate(date);
                    note.setMessage(message);

                    // save note on firebase
                    DbNote.addNoteDocument(
                            SharedPreferencesManager.getStable(this).getIdStable(), note
                    ).addOnSuccessListener(documentReference -> {
                        hideBtnLoading();
                        Toast.makeText(this, "Nouvelle note ajoutée", Toast.LENGTH_LONG).show();
                        sendFirebaseNotification();
                        onBackPressed();
                    }).addOnFailureListener(e -> {
                        hideBtnLoading();
                        Toast.makeText(this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
                    });
                }
                // update
                else {
                    extraNote.setDate(date);
                    extraNote.setMessage(message);

                    DbNote.updateNoteDocument(
                            SharedPreferencesManager.getStable(this).getIdStable(), extraNote
                    ).addOnSuccessListener(documentReference -> {
                        hideBtnLoading();
                        Toast.makeText(this, "Note modifiée", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }).addOnFailureListener(e -> {
                        hideBtnLoading();
                        Toast.makeText(this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
                    });
                }
            }
        });
    }

    private void sendFirebaseNotification() {
        JsonObject data = new JsonObject();
        data.addProperty("/", "/");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("to", "/topics/" + SharedPreferencesManager.getStable(this).getIdStable());
        jsonObject.add("data", data);
        jsonObject.addProperty("priority", "high");
        jsonObject.addProperty("content_available", true);
        jsonObject.addProperty("mutable_content", true);

        RetrofitManager.getInstance().getService().postSendTopicNotificationNewNote(jsonObject).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {}

            @Override
            public void onFailure(Call<Void> call, Throwable t) {}
        });
    }

    private void showBtnLoading() {
        binding.activityNoteAddUpdateBtnSaveText.setVisibility(View.GONE);
        binding.activityNoteAddUpdateBtnSaveProgress.setVisibility(View.VISIBLE);
    }

    private void hideBtnLoading() {
        binding.activityNoteAddUpdateBtnSaveText.setVisibility(View.VISIBLE);
        binding.activityNoteAddUpdateBtnSaveProgress.setVisibility(View.GONE);
    }

}
