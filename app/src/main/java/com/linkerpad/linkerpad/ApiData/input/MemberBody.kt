package com.linkerpad.linkerpad.ApiData.input

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by alihajiloo on 10/14/18 at 17:24.
 */
class ChangeUserRoleBody(

        @SerializedName("UserId")
        @Expose
        var userId: String,

        @SerializedName("ProjectId")
        @Expose
        var projectId: String,

        @SerializedName("UserRole")
        @Expose
        var userRole: Int
)