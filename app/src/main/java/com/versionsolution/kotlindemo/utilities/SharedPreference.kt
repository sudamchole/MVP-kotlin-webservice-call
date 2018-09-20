package com.kotlin.kotlinprojectbase.utilities

import com.kotlin.kotlinprojectbase.contract.AppConstant

class SharedPreference : SharedPreferenceCore(), AppConstant {

        val KEY_LOGIN_STATUS : String get() =  "login_status"


      var keyLoginStatus: String
            get() = this!!.getString(KEY_LOGIN_STATUS, "")!!
            set(keyLoginStatus) {
                setString(KEY_LOGIN_STATUS, keyLoginStatus)
            }


}
