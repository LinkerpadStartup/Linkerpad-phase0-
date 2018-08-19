package com.linkerpad.linkerpad.ApiData.output

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.linkerpad.linkerpad.ApiData.Response


/**
 * Created by alihajiloo on 8/18/18.
 */
class LoginResponse : Response() {

    @SerializedName("responseObject")
    lateinit var responseObject: ResponseObject

}

data class ResponseObject(var token: String, var expirationDate: String, var userInformationViewModel: userInformationResponseLogin)

data class userInformationResponseLogin(var firstName: String, var lastName: String, var profilePicture: Any? = null, var emailAddress: String, var mobileNumber: String, var company: String)