package com.kotlin.kotlinprojectbase.utilities

import com.kotlin.kotlinprojectbase.contract.AppConstant

class SharedPreference : SharedPreferenceCore(), AppConstant {



        /* GCM REGISTRATION ID */
        var keyGcmRegistrationId = "gcm_registration_id"
        ////////////////////////////////////////////////////////////////

        var keyAppVersion = "app_version"
        //////////////////////////////////////////////////////

        var keyFirebaseInstanceId = "firebase_id"


        var keyAppIsOpen = "app_is_open"
        var keyIsLogin = "is_logged_in"

        var keyDeviceToken = "device_token"

        var keyImeiNo = "imei_no"
        var keyFacebookloginId = "facebook_id"
        var keyFacebookloginEmail = "facebook_email"
        var facebookIsClick = 0
        var keyAccessToken = "access_token"
        val KEY_LOGIN_STATUS : String get() =  "login_status"

        var keyDomainId = "domain_id"

        var keyCategoryId = "category_id"



      var keyLoginStatus: String
            get() = this!!.getString(KEY_LOGIN_STATUS, "")!!
            set(keyLoginStatus) {
                setString(KEY_LOGIN_STATUS, keyLoginStatus)
            }
    /*
          private val KEY_USER_ID = "user_id"
          private val KEY_USER_NAME = "user_name"
          private val KEY_USER_EMAIL = "user_email"
          private val KEY_PROFILE_PIC = "profile_pic"

          var keyUserEmail: String
              get() = getString(KEY_USER_EMAIL, null)
              set(keyUserEmail) {
                  setString(KEY_USER_EMAIL, keyUserEmail)
              }

          var keyProfilePic: String
              get() = getString(KEY_PROFILE_PIC, null)
              set(keyProfilePic) {
                  setString(KEY_PROFILE_PIC, keyProfilePic)
              }
          var isLogin: Boolean
              get() = getBoolean(keyIsLogin, false)
              set(value) {
                  setBoolean(keyIsLogin, value)
              }
          var userId: String
              get() = getString(KEY_USER_ID, null)
              set(keyUserId) {
                  setString(KEY_USER_ID, keyUserId)
              }

          var keyUserId: String
              get() = getString(KEY_USER_ID, null)
              set(keyUserId) {
                  setString(KEY_USER_ID, keyUserId)
              }

          var keyUserName: String
              get() = getString(KEY_USER_NAME, null)
              set(keyUserName) {
                  setString(KEY_USER_NAME, keyUserName)
              }*/

}
