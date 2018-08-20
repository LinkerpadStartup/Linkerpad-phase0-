package com.linkerpad.linkerpad.Business


import com.linkerpad.linkerpad.ApiData.input.CreateProjectBody
import com.linkerpad.linkerpad.ApiData.input.EditUserBody
import com.linkerpad.linkerpad.ApiData.input.LoginBody
import com.linkerpad.linkerpad.ApiData.input.RegisterBody
import com.linkerpad.linkerpad.ApiData.output.*
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

    @POST("api/project/CreateProject")
    fun createProject(@Header("authorization") authorization: String, @Body createProjectBody: CreateProjectBody): Call<CreateProjectResponse>

    @GET("api/project/GetProjectList")
    fun getProjectList(@Header("authorization") authorization: String): Call<ProjectListResponse>
}