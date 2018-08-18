package com.linkerpad.linkerpad.Data

import com.linkerpad.linkerpad.ApiData.input.LoginBody
import com.linkerpad.linkerpad.ApiData.output.userInformationResponse

/**
 * Created by alihajiloo on 8/18/18.
 */

data class LoginOutputData(var status: String, var message: String, var token: String, var expirationDate: String, var userInformationViewModel: userInformationResponse)
data class LoginInputData(var username:String , var password:String){
    companion object {
        fun setLoginBody(loginInputData: LoginInputData):LoginBody{
            return LoginBody(loginInputData.username , loginInputData.password)
        }
    }
}