package com.linkerpad.linkerpad.Business


import com.linkerpad.linkerpad.ApiData.input.*
import com.linkerpad.linkerpad.ApiData.output.*
import retrofit2.Call
import retrofit2.http.*
import com.linkerpad.linkerpad.ApiData.output.MachineryResponse


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


    //Daily Activity
    @POST("api/dailyactivity/CreateDailyActivity")
    fun createDailyActivity(@Header("authorization") authorization: String, @Body createDailyActivityBody: CreateDailyActivityBody): Call<CreateDailyActivityResponse>

    @GET("api/dailyactivity/GetProjectDailyActivityList")
    fun getProjectDailyActivityList(@Header("authorization") authorization: String, @Query("ProjectId") projectId: String, @Query("ReportDate") reportDate: String): Call<DailyActivityListResponse>

    @GET("api/dailyactivity/GetDailyActivity")
    fun getDailyActivityInformation(@Header("authorization") authorization: String, @Query("projectId") projectId: String, @Query("dailyActivityId") dailyActivityId: String): Call<GetDailyActivityInformationResponse>

    @POST("api/dailyactivity/DeleteDailyActivity")
    fun deleteDailyActivity(@Header("authorization") authorization: String, @Body deleteDailyActivityBody: DeleteDailyActivityBody): Call<DeleteDailyActivityResponse>

    @POST("api/dailyactivity/EditDailyActivity")
    fun editDailyActivity(@Header("authorization") authorization: String, @Body editDailyActivityBody: EditDailyActivityBody): Call<EditDailyActivityResponse>


    //Equipment
    @POST("api/equipment/CreateEquipment")
    fun createEquipment(@Header("authorization") authorization: String, @Body createMachineryBody: CreateMachineryBody): Call<MachineryResponse>

    @GET("api/equipment/GetProjectEquipmentList")
    fun getProjectEquipmentList(@Header("authorization") authorization: String, @Query("ProjectId") projectId: String, @Query("ReportDate") reportDate: String): Call<MachineryListResponse>

    @GET("api/equipment/GetEquipment")
    fun getEquipmentInformation(@Header("authorization") authorization: String, @Query("projectId") projectId: String, @Query("equipmentId") dailyActivityId: String): Call<GetDailyActivityInformationResponse>

    @POST("api/equipment/DeleteEquipment")
    fun deleteEquipment(@Header("authorization") authorization: String, @Body deleteMachineryBody: DeleteMachineryBody): Call<MachineryResponse>

    @POST("api/equipment/EditEquipment")
    fun editEquipment(@Header("authorization") authorization: String, @Body editMachineryBody: EditMachineryBody): Call<MachineryResponse>


}