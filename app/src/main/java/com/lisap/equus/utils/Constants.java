package com.lisap.equus.utils;

import com.lisap.equus.BuildConfig;

public class Constants {
    // SHARED PREFERENCES
    public final static long DATA_UPLOAD_MAX_MEMORY_SIZE = 1024 * 1024 * 2;

    // SHARED PREFERENCES
    public static final String PREF_STABLE_ID = "prefStableId";

    // FIREBASE FIRESTORE
    public static final String FIREBASE_COLLECTION_NAME_STABLES = "stables";
    public static final String FIREBASE_COLLECTION_NAME_HORSES = "horses";
    public static final String FIREBASE_COLLECTION_NAME_OWNERS = "owners";
    public static final String FIREBASE_COLLECTION_NAME_NOTES = "notes";
    public static final String FIREBASE_COLLECTION_NAME_CARES = "cares";

    // FIREBASE STORAGE
    public static final String START_URL = "https://firebasestorage.googleapis.com/v0/b/equus-96452.appspot.com/o/";
    public static final String END_URL = "?alt=media";

    // FIREBASE NOTIFICATIONS
    public static final String NOTIFICATION_CHANNEL_ID = "CHANNEL_ID";
}
