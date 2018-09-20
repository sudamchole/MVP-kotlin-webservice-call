package com.kotlin.kotlinprojectbase.contract

import android.os.Environment
import java.io.File

 interface AppConstant {
     object BASE_URL {
         val BASE_URL: String
             get() = "https://studytutorial.in/"
         val APP_PATH: String
             get() = Environment.getExternalStorageDirectory().toString() + File.separator + "FCT"
         val PROFILE_PICTURE_IMAGE_PATH: String get()  = APP_PATH + File.separator + "profile" + ".jpg"
         val EMAIL_REGEX: String get() = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"

         //String APP_PATH = Environment.getExternalStorageDirectory() + File.separator + "WBC";
         // String PROFILE_PICTURE_IMAGE_PATH = APP_PATH + File.separator + "profile_avatar" + ".jpg";




         val PERMISSION_REQUEST_CODE_CAMERA_STORAGE: Int
             get() = 1

         val CAMERA_REQUEST_AVATAR: Int get() = 1015
         val GALLERY_REQUEST_AVATAR: Int get() = 1005

         val STATUS_SUCCESS: String get() = "success"
         val STATUS_ERROR: String get()  = "error"
         val DEVICE_NAME: String get() =  "Android"

         val FACEBOOK_MEDIA: String get()  = "Facebook"
         val GMAIL_MEDIA: String get() =  "Gmail"
         val STATUS_FAILED : String get() = "failed"
         val INACTIVE_MSG : String get() = "Your account has been deactivated. Please contact WBC team."
         val NO_USER_FOUND : String get() = "No user found."

         val PRIVACY_NOTIFICATION : String get() = "privacy"
         val COMMENT_NOTIFICATION : String get() = "comment"
     }

//    public static final String BASE_URL="http://www.xyz.com/webservice.php/";


 }