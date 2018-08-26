package com.linkerpad.linkerpad.ApiData.input

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by alihajiloo on 8/26/18.
 */
data class DeleteDailyActivityBody(
        @SerializedName("DailyActivityId")
        @Expose
        var UserId: String,

        @SerializedName("ProjectId")
        @Expose
        var ProjectId: String


)