package com.kotlin.kotlinprojectbase.presenter

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import com.google.gson.JsonObject
import com.kotlin.kotlinprojectbase.contract.MainContract
import com.kotlin.kotlinprojectbase.model.MainActivityModel
import com.kotlin.kotlinprojectbase.network.ApiClient
import com.kotlin.kotlinprojectbase.network.ApiInterface
import com.kotlin.kotlinprojectbase.uibase.CustomProgressDialog
import com.kotlin.kotlinprojectbase.utilities.CommonUtilities
import com.versionsolution.kotlindemo.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class MainActivityPresenter : MainContract.Presenter  {


    lateinit var mView: MainContract.View
    lateinit var model: MainContract.Model

    var progressDialog: ProgressDialog?=null
    private val commonUtilities = CommonUtilities()

   constructor(view: MainContract.View) {
        mView = view
        initPresenter()
    }

    fun initPresenter() {

        model = MainActivityModel()
        mView.initView()
    }

    override fun onClick(view: Int, s: Array<String>, context: Context, call_status: String) {
        if (call_status == "0") {
            progressDialog = CustomProgressDialog(context, "loading")
            (progressDialog as CustomProgressDialog).getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            (progressDialog as CustomProgressDialog).setIndeterminate(true)
            (progressDialog as CustomProgressDialog).setCancelable(false)
            (progressDialog as CustomProgressDialog).show()
        }

        val retrofit = ApiClient.getClient()
        val requestInterface = retrofit.create(ApiInterface::class.java!!)
        val accessTokenCall: Call<JsonObject>

        when (view) {
              2 -> {

              accessTokenCall = requestInterface.getMyWallet(s[0].toString());
              callMeth(accessTokenCall, context);

          }
        }
    }


    fun callMeth(accessTokenCall: Call<JsonObject>, context: Context) {

        try {


            accessTokenCall.enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>?) {
                    if (progressDialog!!.isShowing())
                        progressDialog!!.dismiss()
                    if (response != null) {
                        val data = model!!.getData(response.body().toString())
                        mView.setViewData(data)

                    }
                }


                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    if (progressDialog!!.isShowing())
                        progressDialog!!.dismiss()
                    commonUtilities.commondialog("OK", context.resources.getString(R.string.app_name), context.resources.getString(R.string.no_internet_message), context)
                }
            })
        } catch (e: Exception) {
            if (progressDialog!!.isShowing())
                progressDialog!!.dismiss()
            commonUtilities.commondialog("OK", context.resources.getString(R.string.app_name), context.resources.getString(R.string.no_internet_message), context)
            Log.i("TAG", "------------------>$e")

        }

    }
}