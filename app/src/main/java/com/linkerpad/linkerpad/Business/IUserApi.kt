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
    fun getEquipmentInformation(@Header("authorization") authorization: String, @Query("equipmentId") projectId: String, @Query("projectId") equipmentId: String): Call<GetMachineryInformationResponse>

    @POST("api/equipment/DeleteEquipment")
    fun deleteEquipment(@Header("authorization") authorization: String, @Body deleteMachineryBody: DeleteMachineryBody): Call<MachineryResponse>

    @POST("api/equipment/EditEquipment")
    fun editEquipment(@Header("authorization") authorization: String, @Body editMachineryBody: EditMachineryBody): Call<MachineryResponse>

    //Material
    @POST("api/material/CreateMaterial")
    fun createMaterial(@Header("authorization") authorization: String, @Body createMaterialBody: CreateMaterialBody): Call<MaterialResponse>

    @GET("api/material/GetProjectMaterialList")
    fun getProjectMaterialList(@Header("authorization") authorization: String, @Query("ProjectId") projectId: String, @Query("ReportDate") reportDate: String): Call<MaterialListResponse>

    @GET("api/material/GetMaterial")
    fun getMaterialInformation(@Header("authorization") authorization: String, @Query("projectId") projectId: String, @Query("materialId") materialId: String): Call<GetMaterialInformationResponse>

    @POST("api/material/DeleteMaterial")
    fun deleteMaterial(@Header("authorization") authorization: String, @Body deleteMaterialBody: DeleteMaterialBody): Call<MaterialResponse>

    @POST("api/material/EditMaterial")
    fun editMaterial(@Header("authorization") authorization: String, @Body editMaterialBody: EditMaterialBody): Call<MaterialResponse>

    //Confirmation
    @POST("api/confirmation/CreateConfirmation")
    fun createConfirmation(@Header("authorization") authorization: String, @Body createConfirmationBody: CreateConfirmationBody): Call<ConfirmationResponse>

    @GET("api/confirmation/GetProjectConfirmationList")
    fun getProjectConfirmationList(@Header("authorization") authorization: String, @Query("ProjectId") projectId: String, @Query("ReportDate") reportDate: String): Call<ConfirmationListResponse>

    @POST("api/confirmation/DeleteConfirmation")
    fun deleteConfirmation(@Header("authorization") authorization: String, @Body deleteConfirmationBody: DeleteConfirmationBody): Call<ConfirmationResponse>

    //Note And Event
    @POST("api/Note/CreateNote")
    fun createNote(@Header("authorization") authorization: String, @Body createNoteAndEventBody: CreateNoteAndEventBody): Call<NoteAndEventResponse>

    @GET("api/Note/GetProjectNoteList")
    fun getProjectNoteList(@Header("authorization") authorization: String, @Query("ProjectId") projectId: String, @Query("ReportDate") reportDate: String): Call<NoteAndEventListResponse>

    @GET("api/Note/GetNote")
    fun getNotesInformation(@Header("authorization") authorization: String, @Query("projectId") projectId: String, @Query("noteId") materialId: String): Call<GetNoteAndEventInformationResponse>

    @POST("api/Note/DeleteNote")
    fun deleteNote(@Header("authorization") authorization: String, @Body deleteNoteAndEventBody: DeleteNoteAndEventBody): Call<NoteAndEventResponse>

    @POST("api/Note/EditNote")
    fun editNote(@Header("authorization") authorization: String, @Body editNoteAndEventBody: EditNoteAndEventBody): Call<NoteAndEventResponse>

}