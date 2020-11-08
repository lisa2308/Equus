package com.lisap.equus.data.firestore;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import static com.lisap.equus.utils.Constants.FIREBASE_COLLECTION_NAME_STABLES;

public class DbStable {

    // --- COLLECTION REFERENCE ---
    public static CollectionReference getStableCollection() {
        return FirebaseFirestore.getInstance().collection(FIREBASE_COLLECTION_NAME_STABLES);
    }

    // --- GET ---
    public static Task<QuerySnapshot> getStableDocumentList() {
        return getStableCollection().get();
    }

    public static Task<QuerySnapshot> getStableDocument(String password) {
        return getStableCollection().whereEqualTo("password", password).limit(1).get();
    }
}
