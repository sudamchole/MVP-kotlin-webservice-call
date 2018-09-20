package com.kotlin.kotlinprojectbase.utilities

import android.app.Activity
import android.content.Context
import com.kotlin.kotlinprojectbase.uibase.BaseActivity

object AppDetails {


    private var context: Context? = null
    private var activity: Activity? = null

     fun getContext(): Context {
          return this.context!!
     }

     fun setContext(context: Context) {
          AppDetails.context = context
     }
     fun getActivity(): Activity{
          return  this.activity!!
     }

     fun setActivity(activity: Activity) {
          AppDetails.activity = activity
     }

}
