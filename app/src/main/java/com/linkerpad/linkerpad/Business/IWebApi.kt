package com.linkerpad.linkerpad.Business

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * Created by alihajiloo on 7/31/18.
 */
class IWebApi {

    val BASE_URL = "http://31.184.132.134/"

    /**
     * Companion object for the factory
     */
    companion object Factory {
        fun create(): IUserApi {
            val retrofit = retrofit2.Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://89.42.211.248/")
                    .build()

            return retrofit.create(IUserApi::class.java);
        }
    }
}