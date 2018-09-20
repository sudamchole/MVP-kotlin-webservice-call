package com.kotlin.kotlinprojectbase.network

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {

    @FormUrlEncoded
    @POST("post.php")
    abstract fun getMyWallet(@Field("name") user_id: String): Call<JsonObject>

}