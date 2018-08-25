package com.linkerpad.linkerpad.ApiData.input

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by alihajiloo on 8/25/18.
 */
data class RemoveMemberBody(
        @SerializedName("UserId")
        @Expose
        var UserId: String,

        @SerializedName("ProjectId")
        @Expose
        var ProjectId: String


)