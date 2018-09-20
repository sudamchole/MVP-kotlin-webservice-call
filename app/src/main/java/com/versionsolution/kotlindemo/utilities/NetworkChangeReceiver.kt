package com.kotlin.kotlinprojectbase.utilities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

internal class NetworkChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val status = AppUtils.getConnectivityStatusString(context)

        Toast.makeText(context, status, Toast.LENGTH_SHORT).show()
        AppUtils.internetDialog(context)
    }
}
