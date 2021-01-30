package com.example.acousticpoc

import android.annotation.TargetApi
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import co.acoustic.mobile.push.sdk.api.MceApplication
import co.acoustic.mobile.push.sdk.api.MceSdk
import co.acoustic.mobile.push.sdk.api.MceSdkConfiguration
import co.acoustic.mobile.push.sdk.util.Logger
import org.json.JSONArray
import org.json.JSONException


class PoCApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val appKey = "<YOUR APP KEY>"
        val senderId = "<YOUR SENDER ID>"
        val mceSdkConfiguration = MceSdkConfiguration(appKey, senderId)

        mceSdkConfiguration.baseUrl = "<YOUR BASE URL>"

        mceSdkConfiguration.messagingService = MceSdkConfiguration.MessagingService.fcm
        mceSdkConfiguration.isGroupNotificationsByAttribution = true

        mceSdkConfiguration.logBufferSize = 10
        mceSdkConfiguration.isLogFile = true
        mceSdkConfiguration.logIterationDurationInHours = 1
        mceSdkConfiguration.logLevel = Logger.LogLevel.error

        mceSdkConfiguration.metricTimeInterval = 300

        mceSdkConfiguration.isSessionsEnabled = true
        mceSdkConfiguration.sessionTimeout = 20
        mceSdkConfiguration.isUseFileImageCache = true
        mceSdkConfiguration.isUseInMemoryImageCache = true
        mceSdkConfiguration.fileImageCacheCapacityInMB = 200
        mceSdkConfiguration.inMemoryImageCacheCapacityInMB = 20


        val syncConfiguration =
            mceSdkConfiguration.locationConfiguration.syncConfiguration

        syncConfiguration.locationResponsiveness = 300
        syncConfiguration.syncInterval = 300
        syncConfiguration.syncRadius = 100000
        syncConfiguration.minLocationsForSearch = 1
        syncConfiguration.maxLocationsForSearch = 20

        val iBeaconConfiguration =
            mceSdkConfiguration.locationConfiguration.getiBeaconConfiguration()
        iBeaconConfiguration.uuid = "<YOUR UUID>"
        iBeaconConfiguration.beaconForegroundScanDuration = 5
        iBeaconConfiguration.beaconForegroundScanInterval = 30
        iBeaconConfiguration.beaconBackgroundScanDuration = 30
        iBeaconConfiguration.beaconBackgroundScanInterval = 300


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(applicationContext)
        }
    }
    @TargetApi(26)
    private fun createNotificationChannel(context:Context) {
        val MY_SAMPLE_NOTIFICATION_CHANNEL_ID = "PoC"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var channel = notificationManager.getNotificationChannel(MY_SAMPLE_NOTIFICATION_CHANNEL_ID)
        if (channel == null)
        {
            val name = context.getString(R.string.notif_channel_name)
            val description = context.getString(R.string.notif_channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            channel = NotificationChannel(MY_SAMPLE_NOTIFICATION_CHANNEL_ID, name, importance)
            channel.setDescription(description)
            val notificationsPreference = MceSdk.getNotificationsClient().getNotificationsPreference()
            notificationsPreference.setNotificationChannelId(context, MY_SAMPLE_NOTIFICATION_CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
    }
}