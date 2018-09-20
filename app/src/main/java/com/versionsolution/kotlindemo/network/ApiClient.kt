package com.kotlin.kotlinprojectbase.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.kotlin.kotlinprojectbase.contract.AppConstant
import com.kotlin.kotlinprojectbase.contract.AppConstant.BASE_URL.BASE_URL

class ApiClient : AppConstant {
    companion object {

        var retrofit: Retrofit? = null

        // if (retrofit == null) {
        //}
        fun getClient(): Retrofit {

            // if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
            //}

            return retrofit!!

        }

        fun getClientNew(): Retrofit {
            if (retrofit == null) {
                val okHttpClient = OkHttpClient.Builder()
                        .readTimeout(5, TimeUnit.MINUTES)
                        .connectTimeout(5, TimeUnit.MINUTES)
                        .build()
                retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
            }

            //            retrofit = new Retrofit.Builder().baseUrl(BASE_URLNew).addConverterFactory(GsonConverterFactory.create()).build();


            return retrofit!!

        }

    }
}
