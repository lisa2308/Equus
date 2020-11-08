package com.lisap.equus.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;
import com.lisap.equus.R;
import com.lisap.equus.data.firestore.DbStable;
import com.lisap.equus.databinding.ActivityLoginBinding;
import com.lisap.equus.data.entities.Stable;
import com.google.firebase.firestore.DocumentSnapshot;
import com.lisap.equus.ui.main.MainActivity;
import com.lisap.equus.data.preferences.SharedPreferencesManager;
import com.lisap.equus.utils.Utils;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check if user is already logged in
        if (SharedPreferencesManager.getStable(this) != null) {
            startMainActivity();
            return;
        }

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.activityLoginBtnLogin.setOnClickListener(vi -> {

            final View view1 = getLayoutInflater().inflate(R.layout.activity_login_dialog, null);
            EditText editText = view1.findViewById(R.id.activity_login_dialog_edit);

            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogCustom);
            builder.setTitle(getString(R.string.password));
            builder.setMessage(getString(R.string.pleaseEnterPassword));
            builder.setView(view1);
            builder.setPositiveButton(getString(R.string.ok), (dialog, whichButton) -> {});
            builder.setNegativeButton(getString(R.string.cancel), (dialog, whichButton) -> dialog.dismiss());

            AlertDialog alert = builder.create();
            alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            alert.show();

            alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                if (!editText.getText().toString().isEmpty()) {
                    String password = editText.getText().toString();

                    DbStable.getStableDocumentList().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                String stableEncodedPassword = document.getString("password");
                                if (Utils.isPasswordValid(stableEncodedPassword, password)) {
                                    String uid = document.getId();
                                    Stable stable = document.toObject(Stable.class);
                                    // add uid to stable object
                                    stable.setIdStable(uid);

                                    // save stable in shared preferences
                                    SharedPreferencesManager.putStable(LoginActivity.this, stable);

                                    // subscribe to FCM topic
                                    FirebaseMessaging.getInstance().subscribeToTopic(uid);
                                    startMainActivity();
                                    alert.dismiss();
                                    return;
                                }
                            }

                            Toast.makeText(LoginActivity.this, getString(R.string.invalidPassword), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.pleaseEnterPassword), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    public void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
