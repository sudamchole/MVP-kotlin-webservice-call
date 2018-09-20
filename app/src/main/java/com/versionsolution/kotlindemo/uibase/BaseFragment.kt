package com.kotlin.kotlinprojectbase.uibase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import com.kotlin.kotlinprojectbase.utilities.SharedPreference
import com.versionsolution.kotlindemo.view.activities.MainActivity
import com.versionsolution.kotlindemo.R


internal class BaseFragment : Fragment() {

    /**
     * Check Internet availability
     */
    val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = activity!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            val networkState = networkInfo.state
            return networkState == NetworkInfo.State.CONNECTED || networkState == NetworkInfo.State.CONNECTING
        }

    /**
     * Show Keyboard method
     */
    fun showSoftKeyboard() {
        val inputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    /**
     * hide keyboard method
     */
    fun hideSoftKeyboard() {

        val inputMethodManager = activity!!.getSystemService(
                Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity!!.currentFocus != null)
            inputMethodManager.hideSoftInputFromWindow(
                    activity!!.currentFocus!!.windowToken, 0)
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

    fun hasValidPassword(editText: EditText): Boolean {
        if (editText.text.toString().length == 0 || editText.text.toString().isEmpty()) {
            editText.error = "REQUIRED"
            return false
        } else if (editText.text.toString().length < 8 || editText.text.toString().length > 8) {
            editText.error = "Please enter password at least 8 charater."
            return false
        }
        return true
    }


    /*  public void showCommonDialog(String title, String msg)
    {
        final AlertDialog dialog=new AlertDialog.Builder(getActivity()).create();
        View view= View.inflate(getContext(), R.layout.dialog_common,null);
        dialog.setView(view);
        CustomRegularTextView dialogMsg=view.findViewById(R.id.dialogMsg);
        CustomRegularTextView tvTitle=view.findViewById(R.id.tvTitle);

        dialogMsg.setText(msg);
        tvTitle.setText(title);
        LinearLayout lytOk=view.findViewById(R.id.lytOk);
        lytOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
*/

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)


    }

    override fun getUserVisibleHint(): Boolean {
        return super.getUserVisibleHint()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun create_account(msg1: String) {
        val alertDialog = android.app.AlertDialog.Builder(context).create()
        val view = View.inflate(context, R.layout.dialog_common, null)
        alertDialog.setView(view)
        alertDialog.setCancelable(false)
//        val msg = view.findViewById(R.id.msg)
//        //ImageView ivClose = view.findViewById(R.id.ivClose);
        val btnok = view.findViewById<Button>(R.id.btnok)
        alertDialog.window!!.setBackgroundDrawable(android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT))
//        msg.setText(msg1)


        btnok.setOnClickListener(View.OnClickListener {
            alertDialog.dismiss()
            SharedPreference().keyLoginStatus
           /* SharedPreference.setKeyUserName(null)
            SharedPreference.setKeyUserEmail(null)
            SharedPreference.setUserId(null)
            SharedPreference.setKeyProfilePic(null)*/
            val intent = Intent(context, MainActivity::class.java)
            (context as MainActivity).startActivity(intent)
            (context as MainActivity).finish()
        })
        alertDialog.show()
    }

    companion object {

        fun hideSoftKeyboard(activity: Activity) {
            val inputMethodManager = activity.getSystemService(
                    Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                    activity.currentFocus!!.windowToken, 0)
        }
    }
}// Required empty public constructor