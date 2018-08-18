package com.linkerpad.linkerpad.ApiData.input

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by alihajiloo on 8/18/18.
 */
data class RegisterBody(
        @SerializedName("FirstName")
        @Expose
        var FirstName: String,

        @SerializedName("LastName")
        @Expose
        var LastName: String,

        @SerializedName("Company")
        @Expose
        var Company: String,

        @SerializedName("MobileNumber")
        @Expose
        var MobileNumber: String,

        @SerializedName("EmailAddress")
        @Expose
        var EmailAddress: String,

        @SerializedName("Password")
        @Expose
        var Password: String)
