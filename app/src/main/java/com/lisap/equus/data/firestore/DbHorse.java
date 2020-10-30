package com.lisap.equus.data.firestore;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lisap.equus.data.entities.Horse;
import com.lisap.equus.data.entities.Owner;

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
    public static Task<Void> updateHorseDocument(String stableDocumentId, Horse horse) {
        return getHorseCollection(stableDocumentId).document(horse.getHorseId()).set(horse);
    }

    // --- ADD ---
    public static Task<DocumentReference> addHorseDocument(String stableDocumentId, Horse horse) {
        return getHorseCollection(stableDocumentId).add(horse);
    }

    // --- DELETE ---
    public static Task<Void> deleteHorseDocument(String stableDocumentId, String horseId) {
        return getHorseCollection(stableDocumentId).document(horseId).delete();
    }
}
