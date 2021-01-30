package com.example.acousticpoc

import co.acoustic.mobile.push.sdk.api.MessagingApi
import co.acoustic.mobile.push.sdk.api.fcm.FcmApi
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage




 class MyFirebaseMessagingService: FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if(FcmApi.isFcmMessage(remoteMessage)) {
            FcmApi.handleMceFcmMessage(getApplicationContext(), remoteMessage);
        }
    }

    override fun onNewToken(token: String) {
        MessagingApi.reportToken(getApplicationContext(), token);
    }
}