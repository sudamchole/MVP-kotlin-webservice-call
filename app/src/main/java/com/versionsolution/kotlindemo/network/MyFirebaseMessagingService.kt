///*
//package com.worldbestcompany.wbc.notification
//
//import android.content.Context
//import android.content.Intent
//import org.json.JSONException
//import com.facebook.FacebookSdk.getApplicationContext
//import android.text.TextUtils
//import android.support.v4.content.LocalBroadcastManager
//import android.util.Log
//import org.json.JSONObject
//import com.google.firebase.messaging.RemoteMessage
//import com.google.firebase.messaging.FirebaseMessagingService
//import com.kotlin.kotlinprojectbase.R
//
//
//class MyFirebaseMessagingService : FirebaseMessagingService() {
//
//    private var notificationUtils: NotificationUtils? = null
//
//    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
//        Log.e(TAG, "From: " + remoteMessage!!.data)
//
//        if (remoteMessage == null)
//            return
//
//        // Check if message contains a notification payload.
//        if (remoteMessage.data != null) {
//            Log.e(TAG, "Notification Body: " + remoteMessage.data)
//            val json = JSONObject(remoteMessage.data)
//            handleNotification(json)
//        }
//
//        // Check if message contains a data payload.
//*/
///* try {
//            val json = JSONObject(remoteMessage.notification!!.body.toString())
//            handleDataMessage(json)
//        } catch (e: Exception) {
//            Log.e(TAG, "Exception: " + e.message)
//        }*//*
//*/
///*
//
//
//    }
//
//    private fun handleNotification(json: JSONObject) {
//        if (!NotificationUtils.isAppIsInBackground(applicationContext)) {
//            // app is in foreground, broadcast the push message
//            val data = JSONObject(json.getString("body"))
////            Js data = json.getString("body")
//            val title = data.getString("title")
//            val message = data.getString("description")
////            val isBackground = data.getBoolean("is_background")
////            val imageUrl = data.getString("image")
////            val timestamp = data.getString("timestamp")
////            val payload = data.getJSONObject("payload")
//
//            val link = data.getString("link")
//            val privacy = data.getString("privacy")
//            val template_id = data.getString("template_id")
//            val notification_count = data.getString("notification_count")
//            val type = data.getString("type")
//
//
//            val pushNotification = Intent(Config.PUSH_NOTIFICATION)
//            pushNotification.putExtra("message", message)
//            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification)
//
//          *//*
//
//*/
///*  val resultIntent = Intent(applicationContext, WebViewActivity::class.java)
//            resultIntent.putExtra("pass", "FCM");
//            resultIntent.putExtra("url",link);
//            resultIntent.putExtra("Tittle",resources.getString(R.string.create_template))
//            showNotificationMessage(applicationContext, title, message, type, resultIntent)*//*
//*/
///*
//
//            // play notification sound
//            val notificationUtils = NotificationUtils(applicationContext)
//            notificationUtils.playNotificationSound()
//        } else {
//            // If the app is in background, firebase itself handles the notification
//            val data = JSONObject(json.getString("body"))
//            val title = data.getString("title")
//            val message = data.getString("description")
////            val isBackground = data.getBoolean("is_background")
////            val imageUrl = data.getString("image")
////            val timestamp = data.getString("timestamp")
////            val payload = data.getJSONObject("payload")
//
//            val link = data.getString("link")
//            val privacy = data.getString("privacy")
//            val template_id = data.getString("template_id")
//            val notification_count = data.getString("notification_count")
//            val type = data.getString("type")
//
//
//            val pushNotification = Intent(Config.PUSH_NOTIFICATION)
//            pushNotification.putExtra("message", message)
//            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification)
//
//          *//*
//
//*/
///*  val resultIntent = Intent(applicationContext, WebViewActivity::class.java)
//            resultIntent.putExtra("pass", "FCM");
//            resultIntent.putExtra("url",link);
//            showNotificationMessage(applicationContext, title, message, type, resultIntent)*//*
//*/
///*
//
//            // play notification sound
//            val notificationUtils = NotificationUtils(applicationContext)
//            notificationUtils.playNotificationSound()
//        }
//    }
//
//    private fun handleDataMessage(json: JSONObject) {
//        Log.e(TAG, "push json: " + json.toString())
//
//        try {
////            val data = json.getJSONObject("data")
//
//            val title = json.getString("title")
//            val message = json.getString("description")
////            val isBackground = json.getBoolean("is_background")
////            val imageUrl = json.getString("image")
////            val timestamp = json.getString("timestamp")
////            val payload = json.getJSONObject("payload")
//
//            val link = json.getString("link")
//            val privacy = json.getString("privacy")
//            val template_id = json.getString("template_id")
//            val notification_count = json.getString("notification_count")
//            val type = json.getString("type")
//
//
//            Log.e(TAG, "title: $title")
//            Log.e(TAG, "message: $message")
////            Log.e(TAG, "isBackground: $isBackground")
//            Log.e(TAG, "link:  $link")
//            Log.e(TAG, "privacy: $privacy")
//            Log.e(TAG, "template_id: $template_id")
//            Log.e(TAG,"notification_count: $notification_count")
//            Log.e(TAG, "type: $type")
//
//            val pushNotification = Intent(Config.PUSH_NOTIFICATION)
//            pushNotification.putExtra("message", message)
//            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification)
//
//           *//*
//
//*/
///* val resultIntent = Intent(applicationContext, WebViewActivity::class.java)
//            resultIntent.putExtra("pass", "FCM");
//            resultIntent.putExtra("url",link);
//            showNotificationMessage(applicationContext, title, message, type, resultIntent)*//*
//*/
///*
//
//            // play notification sound
//            val notificationUtils = NotificationUtils(applicationContext)
//            notificationUtils.playNotificationSound()
//
//            *//*
//
//*/
///*if (!NotificationUtils.isAppIsInBackground(applicationContext)) {
//                // app is in foreground, broadcast the push message
//                val pushNotification = Intent(Config.PUSH_NOTIFICATION)
//                pushNotification.putExtra("message", message)
//                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification)
//
//                val resultIntent = Intent(applicationContext, MainHomeActivity::class.java)
//                resultIntent.putExtra("message", message);
//                showNotificationMessage(applicationContext, title, message, type, resultIntent)
//                // play notification sound
//                val notificationUtils = NotificationUtils(applicationContext)
//                notificationUtils.playNotificationSound()
//            } else {
//                // app is in background, show the notification in notification tray
//                val resultIntent = Intent(applicationContext, MainHomeActivity::class.java)
//                resultIntent.putExtra("message", message)
//
//                // check for image attachment
//                showNotificationMessage(applicationContext, title, message, type, resultIntent)
//
//            }*//*
//*/
///*
//
//        } catch (e: JSONException) {
//            Log.e(TAG, "Json Exception: " + e.message)
//        } catch (e: Exception) {
//            Log.e(TAG, "Exception: " + e.message)
//        }
//
//    }
//
//    *//*
//
//*/
///**
//     * Showing notification with text only
//     *//*
//*/
///*
//
//    private fun showNotificationMessage(context: Context, title: String, message: String, timeStamp: String, intent: Intent) {
//        notificationUtils = NotificationUtils(context)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        notificationUtils!!.showNotificationMessage(title, message, timeStamp, intent)
//    }
//
//    *//*
//
//*/
///**
//     * Showing notification with text and image
//     *//*
//*/
///*
//
//    private fun showNotificationMessageWithBigImage(context: Context, title: String, message: String, timeStamp: String, intent: Intent, imageUrl: String) {
//        notificationUtils = NotificationUtils(context)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        notificationUtils!!.showNotificationMessage(title, message, timeStamp, intent, imageUrl)
//    }
//
//    companion object {
//
//        private val TAG = MyFirebaseMessagingService::class.java.simpleName
//    }
//}
//*//*
//
//*/
///*
//{"to":"dYdcl90xKZk:APA91bHx5wFLhFNekatSN3_5iFbmzYLq4ZNxMNDbFY4HeEmIwpXADvDakMnugOgBRjY1Rx3s7K4P8svTKyIPL48msE4HgX1jptYBzolSUk0HAc0tXSuffyxJANge-o6Ljovuak4KAsrJ","data":{"title":"[add title]","body":{ "title": "WBC",
//    "description": "User has changed Privacy.",
//    "notification_count": 23,
//    "notification_id": 62,
//    "link": "http://localhost:3001/#!/is/komal_M1528182778316",
//    "template_id": 78,
//    "privacy": 1,
//    "type": "privacy"
//}},"priority":"high"}*//*
//*/
///*
//
//*/
