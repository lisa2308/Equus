package com.lisap.equus.data.firestore;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import static com.lisap.equus.utils.Constants.FIREBASE_COLLECTION_NAME_HORSES;
import static com.lisap.equus.utils.Constants.FIREBASE_COLLECTION_NAME_STABLES;

public class DbHorse {

    // --- COLLECTION REFERENCE ---
    public static CollectionReference getHorseCollection(String stableDocumentId) {
        return FirebaseFirestore.getInstance()
                .collection(FIREBASE_COLLECTION_NAME_STABLES)
                .document(stableDocumentId)
                .collection(FIREBASE_COLLECTION_NAME_HORSES);
    }

    // --- GET ---
    public static Task<QuerySnapshot> getHorseDocumentList(String stableDocumentId) {
        return getHorseCollection(stableDocumentId).get();
    }

    public static Task<DocumentSnapshot> getHorseDocument(String stableDocumentId, String horseDocumentId) {
        return getHorseCollection(stableDocumentId).document(horseDocumentId).get();
    }

    // --- UPDATE ---
    public static Task<Void> updateHorseDocument(String stableDocumentId, String horseDocumentId, String column, String value) {
        return getHorseCollection(stableDocumentId).document(horseDocumentId).update(column, value);
    }
}
