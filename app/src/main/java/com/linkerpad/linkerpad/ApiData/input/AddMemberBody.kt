package com.linkerpad.linkerpad.ApiData.input

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by alihajiloo on 8/20/18.
 */
data class AddMemberBody(
        @SerializedName("ProjectId")
        @Expose
        var ProjectId: String,

        @SerializedName("EmailAddress")
        @Expose
        var EmailAddress: String
)