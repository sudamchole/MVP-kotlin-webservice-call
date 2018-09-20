package com.kotlin.kotlinprojectbase.uibase

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.multidex.MultiDex
import com.kotlin.kotlinprojectbase.utilities.AppDetails

class DemoApplication : Application(), Application.ActivityLifecycleCallbacks {
    private val TAG = DemoApplication::class.java!!.getSimpleName()

    companion object {
        lateinit var worldBestCompanyInstance: DemoApplication
    }

    //private static NetworkChangeReceiver networkChangeReceiver;
    lateinit var context: Context

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        //        LeakCanary.install (this );
    }

    init {
        worldBestCompanyInstance = this
    }
    override fun onCreate() {

        // ACRA.init(this);
        super.onCreate()
        worldBestCompanyInstance = this
        MultiDex.install(this)
        context = this.applicationContext
//        FirebaseApp.initializeApp(context)
        AppDetails.setContext(applicationContext)
        // registerReceiver(networkChangeReceiver,new IntentFilter("Network fall"));
        registerActivityLifecycleCallbacks(this)

        /*  if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);*/

    }

    fun getAppContext(): Context {
        return context
    }


    @Synchronized
    fun getInstance(): DemoApplication {
        return worldBestCompanyInstance
    }


    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}
