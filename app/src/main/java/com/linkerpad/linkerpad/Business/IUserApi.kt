package com.linkerpad.linkerpad.Business


import com.linkerpad.linkerpad.ApiData.input.*
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

    @GET("api/project/GetProjectInformation")
    fun getProjectInforamtion(@Header("authorization") authorization: String, @Query("projectId") projectId: String): Call<ProjectInformationResponse>

    @POST("api/ProjectTeam/AddMemberToProject")
    fun addMemberToProject(@Header("authorization") authorization: String, @Body addMemberBody: AddMemberBody): Call<AddMemberResponse>

    @GET("api/ProjectTeam/GetProjectMemberList")
    fun getProjectMemberList(@Header("authorization") authorization: String, @Query("projectId") projectId: String): Call<MemberListResponse>

    @POST("api/project/EditProject")
    fun editProject(@Header("authorization") authorization: String, @Body editProjectBody: EditProjectBody): Call<EditProjectResponse>

    @POST("api/projectTeam/RemoveMemberFromProject")
    fun removeMember(@Header("authorization") authorization: String, @Body removeMemberBody: RemoveMemberBody): Call<RemoveMemberResponse>

    @POST("api/dailyactivity/CreateDailyActivity")
    fun createDailyActivity(@Header("authorization") authorization: String, @Body createDailyActivityBody: CreateDailyActivityBody): Call<CreateDailyActivityResponse>

    @GET("api/dailyactivity/GetProjectDailyActivityList")
    fun getProjectDailyActivityList(@Header("authorization") authorization: String,@Query("ProjectId") projectId: String,@Query("ReportDate") reportDate:String): Call<DailyActivityListResponse>

}