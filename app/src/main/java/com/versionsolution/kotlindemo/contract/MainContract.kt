package com.kotlin.kotlinprojectbase.contract

import android.content.Context

interface MainContract {

    interface View {
        fun initView()
        fun setViewData(data: String)

    }

    interface Model {

        fun getData(jsonObject: String): String
        //        String setData(String s);
    }

    interface Presenter {

        fun onClick(view: Int, s: Array<String>, context: Context, call_status: String)
    }
}