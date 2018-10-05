package com.linkerpad.linkerpad.ApiData.input

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by alihajiloo on 8/19/18.
 */
data class EditUserBody(
        @SerializedName("FirstName")
        @Expose
        var firstName: String,

        @SerializedName("LastName")
        @Expose
        var lastName: String,

        @SerializedName("Company")
        @Expose
        var company: String,

        @SerializedName("MobileNumber")
        @Expose
        var mobileNumber: String,

        @SerializedName("ProfilePicture")
        @Expose
        var profilePicture: String?=null


)