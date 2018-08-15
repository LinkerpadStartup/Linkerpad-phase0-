package com.linkerpad.linkerpad.Business

import com.linkerpad.linkerpad.Data.LoginResponse
import com.linkerpad.linkerpad.Data.RegisterResponse
import com.linkerpad.linkerpad.Data.UsersData
import com.linkerpad.linkerpad.Data.UsersDataLogin
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*


/**
 * Created by alihajiloo on 8/11/18.
 */
interface IUserApi {

    @POST("api/account/register")
    abstract fun register(@Body usersData: UsersData): Call<RegisterResponse>

    @POST("api/account/Login")
    abstract fun login(@Body usersDataLogin: UsersDataLogin): Call<LoginResponse>

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