package com.kotlin.kotlinprojectbase.utilities

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.kotlin.kotlinprojectbase.contract.AppConstant
import com.kotlin.kotlinprojectbase.contract.AppConstant.BASE_URL.APP_PATH
import com.versionsolution.kotlindemo.R
import java.io.File
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

internal class AppUtils : AppConstant {
    companion object {

        lateinit var internetDialog: AlertDialog
        lateinit var dialog: AlertDialog
        var simpleDialog: AlertDialog? = null
        lateinit var connectionDialog: AlertDialog
        var pop_name: TextView? = null
        var pop_text: TextView? = null
        var pop_close: Button? = null
        lateinit var value: String
        var time: String? = null

        private var endDate: Date? = null
        private var dayEnd: Int = 0
        private var dfTo: DateFormat? = null
        private var diffDays: Int = 0

        var TYPE_WIFI = 1
        var TYPE_MOBILE = 2
        var TYPE_NOT_CONNECTED = 0


        fun getDeviceID(context: Context): String? {
            var device_id: String? = null
            device_id = Settings.Secure.getString(context.contentResolver,
                    Settings.Secure.ANDROID_ID)
            return if (device_id != null) {
                device_id
            } else null
        }

        /**
         * setErrorMsg for customizing validation error message dialog.
         */
        fun setErrorMsg(msg: String, et: EditText, isRequestFocus: Boolean) {
            // whatever color you want
            val errorColor = Color.RED
            val foregroundColorSpan = ForegroundColorSpan(errorColor)
            val spannableStringbuilder = SpannableStringBuilder(msg)
            spannableStringbuilder.setSpan(foregroundColorSpan, 0, msg.length, 0)
            if (isRequestFocus) {
                et.requestFocus()
            }
            et.error = spannableStringbuilder
        }

        fun getCircleBitmap(bitmap: Bitmap): Bitmap {
            val output = Bitmap.createBitmap(bitmap.width,
                    bitmap.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(output)

            val color = Color.RED
            val paint = Paint()
            val rect = Rect(0, 0, bitmap.width, bitmap.height)
            val rectF = RectF(rect)

            paint.isAntiAlias = true
            canvas.drawARGB(0, 0, 0, 0)
            paint.color = color
            canvas.drawOval(rectF, paint)

            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(bitmap, rect, rect, paint)

            bitmap.recycle()

            return output
        }


        val years: ArrayList<Int>
            get() {
                val years = ArrayList<Int>()
                val presentYear = Calendar.getInstance().get(Calendar.YEAR)
                for (i in presentYear downTo 1997) {
                    years.add(i)
                }
                return years
            }

        /**
         * Alertdialog for internet connectivity
         */
        fun internetDialog(context: Context): Dialog {

            val factory = LayoutInflater.from(context)
            val deleteDialogView = factory.inflate(
                    R.layout.dialog_internet_layout, null)

            internetDialog = AlertDialog.Builder(context).create()
            internetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            internetDialog.window!!.setBackgroundDrawable(
                    ColorDrawable(Color.TRANSPARENT))

            internetDialog.setView(deleteDialogView)
            internetDialog.setCancelable(true)
            internetDialog.setCanceledOnTouchOutside(false)
            deleteDialogView.setOnClickListener(
                    View.OnClickListener {
                        internetDialog.dismiss()
                        context.startActivity(Intent(
                                Settings.ACTION_SETTINGS))
                    })
            deleteDialogView.setOnClickListener(
                    View.OnClickListener { internetDialog.dismiss() })
            internetDialog.show()

            return internetDialog
        }

        fun ConnectionError(context: Context, msg: String): Dialog {

            val dialog_text: TextView
            val factory = LayoutInflater.from(context)
            val deleteDialogView = factory.inflate(
                    R.layout.dialog_internet_layout, null)

            connectionDialog = AlertDialog.Builder(context).create()
            connectionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            connectionDialog.window!!.setBackgroundDrawable(
                    ColorDrawable(Color.TRANSPARENT))

            connectionDialog.setView(deleteDialogView)
            connectionDialog.setCancelable(true)
            dialog_text = connectionDialog.findViewById(R.id.dialog_text) as TextView
            // dialog_text.setText(msg);
            connectionDialog.setCanceledOnTouchOutside(false)
            deleteDialogView.setOnClickListener(
                    View.OnClickListener {
                        connectionDialog.dismiss()
                        context.startActivity(Intent(
                                Settings.ACTION_SETTINGS))
                    })
            deleteDialogView.setOnClickListener(
                    View.OnClickListener { connectionDialog.dismiss() })
            connectionDialog.show()

            return connectionDialog
        }


        /* public static Dialog simpleDialog(final Context context, final String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View notification_view = inflater.inflate(R.layout.dialog_simple_layout, null);
        simpleDialog = new AlertDialog.Builder(context).create();
        simpleDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        simpleDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
//        simpleDialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;

        simpleDialog.setView(notification_view);
        simpleDialog.setCancelable(true);
        simpleDialog.setCanceledOnTouchOutside(false);

        pop_name = (TextView) notification_view.findViewById(R.id.notification_name);
        pop_text = (TextView) notification_view.findViewById(R.id.notification_text);
        pop_close = (Button) notification_view.findViewById(R.id.notification_close);

        pop_name.setText(R.string.app_name);
        pop_name.setTypeface(Typeface.DEFAULT_BOLD);
        pop_text.setText(msg);
        pop_close.setText("OK");
        pop_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleDialog.dismiss();
            }
        });
        simpleDialog.show();
        return simpleDialog;
    }
*/
        /**
         * To check internet connection
         */
        fun isConnectingToInternet(_context: Context): Boolean {
            val connectivity = _context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivity != null) {
                val info = connectivity.allNetworkInfo
                if (info != null) {
                    for (i in info.indices) {
                        if (info[i].state == NetworkInfo.State.CONNECTED) {
                            return true
                        }
                    }
                }
            }
            return false
        }

        fun isEmpty(edit_text_value: EditText): Boolean {
            return if (edit_text_value.text.toString().trim { it <= ' ' }.length > 0) {
                false
            } else {
                true
            }
        }

        fun isValidEmailId(email: String): Boolean {

            return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches()
        }

        fun showMessage(c: Context, s: String) {
            val aBuilder = AlertDialog.Builder(c)
            aBuilder.setMessage(s)
            aBuilder.setCancelable(false)
                    .setPositiveButton("Ok") { dialog, which -> dialog.dismiss() }
            dialog = aBuilder.create()
            dialog.show()
        }

        fun hideSoftKeyboard(activity: Activity) {
            val inputMethodManager = activity.getSystemService(
                    Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                    activity.currentFocus!!.windowToken, 0)
        }

        fun isDeviceSupportCamera(context: Context): Boolean {
            return if (context.packageManager.hasSystemFeature(
                            PackageManager.FEATURE_CAMERA)) {
                // this device has a camera
                true
            } else {
                // no camera on this device
                false
            }
        }

        fun getDateWithTime(date: String, flag: Boolean): String {
            val dateTime: String
            val date_split = date.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val date_time = date_split[0].split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val dfFrom = SimpleDateFormat("yyyy-MM-dd")
            try {
                endDate = dfFrom.parse(date_time[0])
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            dayEnd = endDate!!.date

            if (dayEnd == 1 || dayEnd == 21 || dayEnd == 31)
                dfTo = SimpleDateFormat("EE d'st' MMM")
            else if (dayEnd == 2 || dayEnd == 22)
                dfTo = SimpleDateFormat("EE d'nd' MMM")
            else if (dayEnd == 3 || dayEnd == 23)
                dfTo = SimpleDateFormat("EE d'rd' MMM")
            else
                dfTo = SimpleDateFormat("EE d'th' MMM")

            if (flag)
                dateTime = dfTo!!.format(endDate) + " " + date_time[1]
            else
                dateTime = dfTo!!.format(endDate)
            return dateTime
        }

        fun convert24To12(time: String): String {
            try {
                val sdf = SimpleDateFormat("H:mm")
                val dateObj = sdf.parse(time)
                value = SimpleDateFormat("K:mm a").format(dateObj)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return value
        }

        fun getDuration(fromDate: String, toDate: String): String {
            val from_date_split = fromDate.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val from_date_time = from_date_split[0].split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val to_date_split = toDate.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val to_date_time = to_date_split[0].split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            val format = "yyyy-MM-dd HH:mm:ss"
            val sdf = SimpleDateFormat(format)

            try {
                val from = sdf.parse(from_date_time[0] + " " + from_date_time[1])
                val to = sdf.parse(to_date_time[0] + " " + to_date_time[1])
                val diff = to.time - from.time
                diffDays = (diff / (24 * 60 * 60 * 1000)).toInt()
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return diffDays.toString() + " Days"
        }


        val outputMediaFile: File?
            get() {
                val mediaStorageDir = File(APP_PATH)

                if (!mediaStorageDir.exists()) {
                    if (!mediaStorageDir.mkdirs()) {
                        return null
                    }
                }

                val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                return File(mediaStorageDir.getPath() + File.separator +
                        "IMG_" + timeStamp + ".jpg")
            }

        fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
                for (permission in permissions) {
                    if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                        return false
                    }
                }
            }
            return true
        }

        fun getDayMonthDate(date: String): String {
            val date_time = date.split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val dfFrom = SimpleDateFormat("yyyy-MM-dd")
            try {
                endDate = dfFrom.parse(date_time[0])
                dfTo = SimpleDateFormat("EEEE, MMM d")
                value = dfTo!!.format(endDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return value
        }


        fun getConnectivityStatus(context: Context): Int {
            val cm = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = cm.activeNetworkInfo
            if (null != activeNetwork) {
                if (activeNetwork.type == ConnectivityManager.TYPE_WIFI)
                    return TYPE_WIFI

                if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
                    return TYPE_MOBILE
            }
            return TYPE_NOT_CONNECTED
        }

        fun getConnectivityStatusString(context: Context): String? {
            val conn = AppUtils.getConnectivityStatus(context)
            var status: String? = null
            if (conn == AppUtils.TYPE_WIFI) {
                status = "Wifi enabled"
            } else if (conn == AppUtils.TYPE_MOBILE) {
                status = "Mobile data enabled"
            } else if (conn == AppUtils.TYPE_NOT_CONNECTED) {
                status = "Not connected to Internet"
            }
            return status
        }
    }
}
