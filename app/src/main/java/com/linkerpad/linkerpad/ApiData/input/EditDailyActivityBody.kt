package com.linkerpad.linkerpad.ApiData.input

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by alihajiloo on 8/26/18.
 */
class EditDailyActivityBody(
        @SerializedName("DailyActivityId")
        @Expose
        var dailyActivityId: String,


        @SerializedName("ProjectId")
        @Expose
        var projectId: String,

        @SerializedName("Title")
        @Expose
        var title: String,

        @SerializedName("WorkloadUnit")
        @Expose
        var workloadUnit: String,

        @SerializedName("Description")
        @Expose
        var description: String,

        @SerializedName("NumberOfCrew")
        @Expose
        var numberOfCrew: Int,

        @SerializedName("WorkHours")
        @Expose
        var workHours: Float,
        @SerializedName("Workload")
        @Expose
        var workload: Float
)