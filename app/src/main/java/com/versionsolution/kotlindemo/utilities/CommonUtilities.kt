package com.kotlin.kotlinprojectbase.utilities

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import com.versionsolution.kotlindemo.R
import kotlinx.android.synthetic.main.white_listview_popup_dialog.*
import java.util.regex.Matcher
import java.util.regex.Pattern

internal class CommonUtilities {
    fun commondialog(btnName: String, title1: String, msg1: String, context: Context) {
        val alertDialog = AlertDialog.Builder(context).create()
        val view = View.inflate(context, R.layout.dialog_common, null)
        alertDialog.setView(view)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val title = view.findViewById(R.id.title) as TextView
        val msg = view.findViewById(R.id.msg) as TextView
        val ivClose = view.findViewById(R.id.ivClose) as Button
        //ImageView img = view.findViewById(R.id.img);
        val btnok = view.findViewById(R.id.btnok) as Button
        //final CustomFontEditText edt_emailId = view.findViewById(R.id.edt_emailId);

        title.setText(title1)
        msg.setText(msg1)
        btnok.setText(btnName)
        //img.setImageResource(img1);


        ivClose.setOnClickListener(View.OnClickListener { alertDialog.dismiss() })

        btnok.setOnClickListener(View.OnClickListener {
            //isValid(edt_emailId.getText().toString(),){}
            alertDialog.dismiss()
        })
        alertDialog.show()
    }

    fun commondialog(btnName: String, title1: String, msg1: String, img1: Int, context: Context) {
        val alertDialog = AlertDialog.Builder(context).create()
        val view = View.inflate(context, R.layout.dialog_common, null)
        alertDialog.setView(view)
        val title = view.findViewById(R.id.title) as TextView
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val msg = view.findViewById(R.id.msg) as TextView
        val btnok = view.findViewById(R.id.btnok) as Button
        val img = view.findViewById(R.id.img) as ImageView
        btnok.setText(btnName)
        title.setText(title1)
        msg.setText(msg1)
//        img.setImageResource(img1)
        img.resources.getDrawable(img1)

        btnok.setOnClickListener(View.OnClickListener { alertDialog.dismiss() })
        alertDialog.show()
    }


    // Show single button white pop up with ListView content
    fun displayListViewWhiteDialog(activity: Activity, dialogHeader: String,
                                   dialogBody: ArrayAdapter<*>): Dialog {
        val dialog = Dialog(activity, R.style.DialogSlideAnimation)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.white_listview_popup_dialog)
        (dialog.findViewById(R.id.dialogHeading) as TextView).text = dialogHeader
        (dialog.findViewById(R.id.notifyText) as ListView).adapter = dialogBody
       // dialog.button_ok.setOnClickListener(View.OnClickListener { dialog.dismiss() })

        dialog.show()
        // Grab the window of the dialog, and change the width
        val lp = WindowManager.LayoutParams()
        val window = dialog.window
        lp.copyFrom(window!!.attributes)
        // This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = lp
        return dialog
    }

    companion object {


        fun isEmailValid(email: String, context: Context): Boolean {
            val pattern: Pattern
            val matcher: Matcher
            val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
            pattern = Pattern.compile(EMAIL_PATTERN)
            matcher = pattern.matcher(email)
            return matcher.matches()
        }

        fun hasValidEmail(editText: EditText, context: Context): Boolean {
            val email = editText.text.toString()
            if (editText.text.toString().length == 0 || editText.text.toString().isEmpty()) {
                editText.error = "REQUIRED"
                return false
            }
            return true
        }
    }
}
