package com.linkerpad.linkerpad.Data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by alihajiloo on 7/31/18.
 */

/*
data class UsersData(var FirstName: String, var LastName: String, var Company: String, var MobileNumber: String, var EmailAddress: String, var Password: String) {
*/
data class UsersData (
    @SerializedName("FirstName")
    @Expose
    var FirstName: String,

    @SerializedName("LastName")
    @Expose
    var LastName: String ,

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

class RegisterResponse {
    @SerializedName("token")
    var token: String = ""
}

