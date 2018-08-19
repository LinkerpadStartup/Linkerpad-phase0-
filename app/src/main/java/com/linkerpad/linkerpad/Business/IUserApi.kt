package com.linkerpad.linkerpad.Business


import com.linkerpad.linkerpad.ApiData.input.EditUserBody
import com.linkerpad.linkerpad.ApiData.input.LoginBody
import com.linkerpad.linkerpad.ApiData.input.RegisterBody
import com.linkerpad.linkerpad.ApiData.output.EditUserResponse
import com.linkerpad.linkerpad.ApiData.output.LoginResponse
import com.linkerpad.linkerpad.ApiData.output.RegisterResponse
import com.linkerpad.linkerpad.ApiData.output.GetUserInformationResponse
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

    @GET("api/account/GetUserInformation")
    fun getUserInformation(@Header("authorization") authorization: String): Call<GetUserInformationResponse>

    @POST("api/account/EditUserInformation")
    fun editUserInformation(@Header("authorization") authorization: String, @Body editUserBody: EditUserBody): Call<EditUserResponse>

}