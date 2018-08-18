package com.linkerpad.linkerpad.ApiData.input

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by alihajiloo on 8/18/18.
 */
data class LoginBody(
        @SerializedName("Username")
        @Expose
        var Username: String,

        @SerializedName("Password")
        @Expose
        var Password: String)
