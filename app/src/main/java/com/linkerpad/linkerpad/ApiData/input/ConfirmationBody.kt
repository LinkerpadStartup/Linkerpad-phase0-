package com.linkerpad.linkerpad.ApiData.input

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by alihajiloo on 8/27/18 at 13:01.
 */

data class CreateConfirmationBody(
        @SerializedName("ProjectId")
        @Expose
        var projectId: String,

        @SerializedName("ReportDate")
        @Expose
        var reportDate: String

)

data class DeleteConfirmationBody(


        @SerializedName("ProjectId")
        @Expose
        var ProjectId: String,

        @SerializedName("ReportDate")
        @Expose
        var reportDate: String

)

class EditConfirmationBody(
        @SerializedName("MaterialId")
        @Expose
        var dailyActivityId: String,

        @SerializedName("ProjectId")
        @Expose
        var projectId: String,

        @SerializedName("Title")
        @Expose
        var title: String,

        @SerializedName("ConsumedQuantityUnit")
        @Expose
        var consumedQuantityUnit: String,

        @SerializedName("Description")
        @Expose
        var description: String,

        @SerializedName("ConsumedQuantity")
        @Expose
        var consumedQuantity: Float
)

data class GetConfirmationListBody(
        @SerializedName("ProjectId")
        @Expose
        var projectId: String,

        @SerializedName("ReportDate")
        @Expose
        var reportDate: String
)