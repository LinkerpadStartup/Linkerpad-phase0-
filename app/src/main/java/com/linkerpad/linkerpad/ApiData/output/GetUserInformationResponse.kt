package com.linkerpad.linkerpad.ApiData.output

import com.google.gson.annotations.SerializedName
import com.linkerpad.linkerpad.ApiData.Response

/**
 * Created by alihajiloo on 8/19/18.
 */

class GetUserInformationResponse : Response() {
    @SerializedName("responseObject")
    lateinit var responseObject: ResponseObjectUserInformation
}

data class ResponseObjectUserInformation(var firstName: String, var lastName: String, var profilePicture: Any? = null, var emailAddress: String, var mobileNumber: String, var company: String)