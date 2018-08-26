package com.linkerpad.linkerpad.ApiData.input

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by alihajiloo on 8/26/18.
 */
data class CreateMachineryBody(
        @SerializedName("ProjectId")
        @Expose
        var projectId: String,

        @SerializedName("ReportDate")
        @Expose
        var reportDate: String,

        @SerializedName("Title")
        @Expose
        var title: String,

        @SerializedName("Description")
        @Expose
        var description: String,

        @SerializedName("numberOfActiveEquipment")
        @Expose
        var numberOfActiveEquipment: Int,

        @SerializedName("numberOfDeactiveEquipment")
        @Expose
        var numberOfDeactiveEquipment: Int,

        @SerializedName("WorkHours")
        @Expose
        var workHours: Float

)

data class DeleteMachineryBody(
        @SerializedName("EquipmentId")
        @Expose
        var UserId: String,

        @SerializedName("ProjectId")
        @Expose
        var ProjectId: String


)

class EditMachineryBody(
        @SerializedName("EquipmentId")
        @Expose
        var dailyActivityId: String,

        @SerializedName("ProjectId")
        @Expose
        var projectId: String,

        @SerializedName("Title")
        @Expose
        var title: String,

        @SerializedName("Description")
        @Expose
        var description: String,

        @SerializedName("numberOfActiveEquipment")
        @Expose
        var numberOfActiveEquipment: Int,

        @SerializedName("numberOfDeactiveEquipment")
        @Expose
        var numberOfDeactiveEquipment: Int,

        @SerializedName("WorkHours")
        @Expose
        var WorkHours: Float
)

data class GetMachineryListBody(
        @SerializedName("ProjectId")
        @Expose
        var projectId: String,

        @SerializedName("ReportDate")
        @Expose
        var reportDate: String
)