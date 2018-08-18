package com.linkerpad.linkerpad.Business


import com.linkerpad.linkerpad.ApiData.input.LoginBody
import com.linkerpad.linkerpad.ApiData.input.RegisterBody
import com.linkerpad.linkerpad.ApiData.output.LoginResponse
import com.linkerpad.linkerpad.ApiData.output.RegisterResponse
import retrofit2.Call
import retrofit2.http.*


/**
 * Created by alihajiloo on 8/11/18.
 */
interface IUserApi {

    @POST("api/account/register")
    fun register(@Body registerBody: RegisterBody): Call<RegisterResponse>

    @POST("api/account/Login")
    fun login(@Body loginBody: LoginBody): Call<LoginResponse>

    /* @FormUrlEncoded
     @POST("api/account/register")
     abstract fun register(@Field("FirstName") FirstName: String,
                           @Field("LastName") LastName: String,
                           @Field("Company") Company: String,
                           @Field("MobileNumber") MobileNumber: String,
                           @Field("EmailAddress") EmailAddress: String,
                           @Field("Password") Password: String): Call<RegisterResponse>*/
    // abstract fun register(@Field("email")  email:String , @Field("password") password:String ): Call<RegisterResponse>


}