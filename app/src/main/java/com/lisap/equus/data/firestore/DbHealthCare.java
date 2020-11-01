package com.lisap.equus.data.firestore;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lisap.equus.data.entities.HealthCare;
import com.lisap.equus.data.entities.Horse;
import com.lisap.equus.data.entities.Note;

import static com.lisap.equus.utils.Constants.FIREBASE_COLLECTION_NAME_CARES;
import static com.lisap.equus.utils.Constants.FIREBASE_COLLECTION_NAME_HORSES;
import static com.lisap.equus.utils.Constants.FIREBASE_COLLECTION_NAME_STABLES;

public class DbHealthCare {

    // --- COLLECTION REFERENCE ---
    public static CollectionReference getHealthCareCollection(String stableDocumentId, String horseDocumentId) {
        return FirebaseFirestore.getInstance()
                .collection(FIREBASE_COLLECTION_NAME_STABLES)
                .document(stableDocumentId)
                .collection(FIREBASE_COLLECTION_NAME_HORSES)
                .document(horseDocumentId)
                .collection(FIREBASE_COLLECTION_NAME_CARES);
    }

    // --- GET ---
    public static Task<QuerySnapshot> getHealthCareDocumentList(String stableDocumentId, String horseDocumentId) {
        return getHealthCareCollection(stableDocumentId, horseDocumentId).get();
    }

    public static Task<DocumentSnapshot> getHealthCareDocument(String stableDocumentId, String horseDocumentId, String healthCareDocumentId) {
        return getHealthCareCollection(stableDocumentId, horseDocumentId).document(healthCareDocumentId).get();
    }

    // --- UPDATE ---
    public static Task<Void> updateHealthCareDocument(String stableDocumentId, String horseDocumentId, HealthCare healthCare) {
        return getHealthCareCollection(stableDocumentId, horseDocumentId).document(healthCare.getHealthCareId()).set(healthCare);
    }

    // --- ADD ---
    public static Task<DocumentReference> addHealthCareDocument(String stableDocumentId, String horseDocumentId, HealthCare healthCare) {
        return getHealthCareCollection(stableDocumentId, horseDocumentId).add(healthCare);
    }

    // --- DELETE ---
    public static Task<Void> deleteHealthCareDocument(String stableDocumentId, String horseDocumentId, String healthCareId) {
        return getHealthCareCollection(stableDocumentId, horseDocumentId).document(healthCareId).delete();
    }

}
