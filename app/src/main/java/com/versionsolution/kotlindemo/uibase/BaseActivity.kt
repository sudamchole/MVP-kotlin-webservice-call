package com.kotlin.kotlinprojectbase.uibase

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.kotlin.kotlinprojectbase.contract.AppConstant
import com.kotlin.kotlinprojectbase.contract.AppConstant.BASE_URL.EMAIL_REGEX
import com.kotlin.kotlinprojectbase.utilities.AppDetails

open class BaseActivity : AppCompatActivity(), AppConstant {




    fun isNetworkAvailable(): Boolean {
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo ?: return false
        val networkState = networkInfo.state
        return networkState == NetworkInfo.State.CONNECTED || networkState == NetworkInfo.State.CONNECTING
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onResume() {
        super.onResume()
        activitySetup()
    }

    private fun activitySetup() {
        AppDetails.setActivity(this)
        AppDetails.setContext(this)
    }

    fun closeKeyboard() {
        val inputManager = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
    }


    fun setErrorMsg(msg: String, et: EditText, isRequestFocus: Boolean) {
        val ecolor = Color.RED // whatever color you want
        val fgcspan = ForegroundColorSpan(ecolor)
        val ssbuilder = SpannableStringBuilder(msg)
        ssbuilder.setSpan(fgcspan, 0, msg.length, 0)
        if (isRequestFocus) {
            et.requestFocus()
        }
        et.error = ssbuilder

    }

    fun showToast(message: String) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    fun hasValidText(editText: EditText): Boolean {
        if (editText.text.toString().length == 0 || editText.text.toString().isEmpty()) {
            editText.error = "REQUIRED"
            return false
        }

        return true
    }

    fun hasValidEmail(editText: EditText): Boolean {
        val res = editText.text.toString()
        if (editText.text.toString().length == 0 || editText.text.toString().isEmpty()) {

//            setErrorMsg(resources.getString(R.string.blank_email), editText, true)
            //editText.setError("REQUIRED");
            return false
        }
        if (!res.matches(EMAIL_REGEX.toRegex())) {
            //setErrorMsg(getResources().getString(R.string.valid_email), editText, true);
//            setErrorMsg(resources.getString(R.string.valid_email), editText, true)
            return false
        }

        return true
    }

    //
    fun hasValidMobileNumber(editText: EditText, limit: Int): Boolean {
        val `val` = editText.text.toString()
        if (`val`.length == 0 || `val`.isEmpty()) {
            editText.error = "REQUIRED"
            return false
        } else if (`val`.length > 0 && `val`.length < limit) {
            editText.error = "INVALID"
            return false
        }

        return true
    }

    companion object {


        fun hideSoftKeyboard(activity: Activity) {
            val inputMethodManager = activity.getSystemService(
                    Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                    activity.currentFocus!!.windowToken, 0)
        }
    }

}
