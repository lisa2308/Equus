package com.lisap.equus.data.firestore;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lisap.equus.data.entities.Owner;

import static com.lisap.equus.utils.Constants.FIREBASE_COLLECTION_NAME_OWNERS;
import static com.lisap.equus.utils.Constants.FIREBASE_COLLECTION_NAME_STABLES;

public class DbOwner {
    
    // --- COLLECTION REFERENCE ---
    public static CollectionReference getOwnerCollection(String stableDocumentId) {
        return FirebaseFirestore.getInstance()
                .collection(FIREBASE_COLLECTION_NAME_STABLES)
                .document(stableDocumentId)
                .collection(FIREBASE_COLLECTION_NAME_OWNERS);
    }

    // --- GET ---
    public static Task<QuerySnapshot> getOwnerDocumentList(String stableDocumentId) {
        return getOwnerCollection(stableDocumentId).get();
    }

    public static Task<DocumentSnapshot> getOwnerDocument(String stableDocumentId, String ownerDocumentId) {
        return getOwnerCollection(stableDocumentId).document(ownerDocumentId).get();
    }

    // --- UPDATE ---
    public static Task<Void> updateOwnerDocument(String stableDocumentId, Owner owner) {
        return getOwnerCollection(stableDocumentId).document(owner.getOwnerId()).set(owner);
    }

    // --- ADD ---
    public static Task<DocumentReference> addOwnerDocument(String stableDocumentId, Owner owner) {
        return getOwnerCollection(stableDocumentId).add(owner);
    }

    // --- DELETE ---
    public static Task<Void> deleteOwnerDocument(String stableDocumentId, String ownerId) {
        return getOwnerCollection(stableDocumentId).document(ownerId).delete();
    }
}
