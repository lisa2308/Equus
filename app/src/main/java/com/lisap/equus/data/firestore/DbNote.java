package com.lisap.equus.data.firestore;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lisap.equus.data.entities.Note;
import com.lisap.equus.data.entities.Owner;

import static com.lisap.equus.utils.Constants.FIREBASE_COLLECTION_NAME_NOTES;
import static com.lisap.equus.utils.Constants.FIREBASE_COLLECTION_NAME_STABLES;

public class DbNote {
    // --- COLLECTION REFERENCE ---
    public static CollectionReference getNoteCollection(String stableDocumentId) {
        return FirebaseFirestore.getInstance()
                .collection(FIREBASE_COLLECTION_NAME_STABLES)
                .document(stableDocumentId)
                .collection(FIREBASE_COLLECTION_NAME_NOTES);
    }

    // --- GET ---
    public static Task<QuerySnapshot> getNoteDocumentList(String stableDocumentId) {
        return getNoteCollection(stableDocumentId).get();
    }

    public static Task<DocumentSnapshot> getNoteDocument(String stableDocumentId, String noteDocumentId) {
        return getNoteCollection(stableDocumentId).document(noteDocumentId).get();
    }

    // --- UPDATE ---
    public static Task<Void> updateNoteDocument(String stableDocumentId, Note note) {
        return getNoteCollection(stableDocumentId).document(note.getNoteId()).set(note);
    }

    // --- ADD ---
    public static Task<DocumentReference> addNoteDocument(String stableDocumentId, Note note) {
        return getNoteCollection(stableDocumentId).add(note);
    }

    // --- DELETE ---
    public static Task<Void> deleteNoteDocument(String stableDocumentId, String noteId) {
        return getNoteCollection(stableDocumentId).document(noteId).delete();
    }
}


