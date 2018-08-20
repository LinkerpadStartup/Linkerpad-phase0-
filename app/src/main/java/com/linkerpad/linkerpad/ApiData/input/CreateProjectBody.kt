package com.linkerpad.linkerpad.ApiData.input

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by alihajiloo on 8/20/18.
 */
data class CreateProjectBody(
        @SerializedName("ProjectPicture")
        @Expose
        var projectPicture: String,

        @SerializedName("Name")
        @Expose
        var projectName: String,

        @SerializedName("Code")
        @Expose
        var projectCode: String,

        @SerializedName("Address")
        @Expose
        var projectAddress: String,

        @SerializedName("StartDate")
        @Expose
        var projectStartDate: String,

        @SerializedName("EndDate")
        @Expose
        var projectEndDate: String
)