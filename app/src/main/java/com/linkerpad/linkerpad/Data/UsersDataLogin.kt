package com.linkerpad.linkerpad.Data

import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.linkerpad.linkerpad.Models.ResponseObject
import org.json.JSONObject
import java.io.Reader
import java.util.*

/**
 * Created by alihajiloo on 7/31/18.
 */

/*
data class UsersData(var FirstName: String, var LastName: String, var Company: String, var MobileNumber: String, var EmailAddress: String, var Password: String) {
*/
data class UsersDataLogin(
        @SerializedName("Username")
        @Expose
        var Username: String,

        @SerializedName("Password")
        @Expose
        var Password: String)


/* @SerializedName("email")
  @Expose
  var email: String = ""

  @SerializedName("password")
  @Expose
  var password: String = ""*/


/*
data class Result(val resultCode: Int, val token: String, val expirationDate: String, val ListItem: List<UsersData>)
//  data class Result (val total_count: Int, val incomplete_results: Boolean, val items: List<User>)
*/

class LoginResponse {
    @SerializedName("status")
    var status: String = ""

    @SerializedName("message")
    var message: String = ""

    @SerializedName("responseObject")
    lateinit var responseObject:ResponseObject
}
