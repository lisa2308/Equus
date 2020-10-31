package com.lisap.equus.data.firestore;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lisap.equus.data.entities.Note;

import static com.lisap.equus.utils.Constants.FIREBASE_COLLECTION_NAME_CARE;
import static com.lisap.equus.utils.Constants.FIREBASE_COLLECTION_NAME_HORSES;

public class DbHealthCare {
    // --- COLLECTION REFERENCE ---
    public static CollectionReference getNoteCollection(String stableDocumentId) {
        return FirebaseFirestore.getInstance()
                .collection(FIREBASE_COLLECTION_NAME_HORSES)
                .document(stableDocumentId)
                .collection(FIREBASE_COLLECTION_NAME_CARE);
    }

}
