package com.example.equus.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.equus.databinding.ActivityLoginBinding;
import com.example.equus.entities.Stable;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("coucou");
                Query query = FirebaseFirestore.getInstance()
                        .collection("stables");
                query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        System.out.println("ca va");
                        if (error != null) {
                            // Handle error
                            //...
                            return;
                        }

                        // Convert query snapshot to a list of chats
                        List<Stable> stables = value.toObjects(Stable.class);
                        for (Stable stable : stables){
                            System.out.println(stable);
                        }

                        // Update UI
                        // ...
                    }

                });
            }
        });
    }
}
