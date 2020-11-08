package com.lisap.equus.data.fcm;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.lisap.equus.data.preferences.SharedPreferencesManager;

public class FCMManager {

    public static void sendNotificationToTopic(Context context, String key, int value) {
//        String topic = SharedPreferencesManager.getStable(context).getIdStable();
//
//        // See documentation on defining a message payload.
//        RemoteMessage message = new RemoteMessage.Builder("").setTopic(topic).build();
//
//        // Send a message to the devices subscribed to the provided topic
//        FirebaseMessaging.getInstance().send(message);
    }
}
