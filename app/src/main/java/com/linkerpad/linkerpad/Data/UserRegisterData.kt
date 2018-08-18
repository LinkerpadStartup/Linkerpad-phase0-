package com.linkerpad.linkerpad.Data

import com.linkerpad.linkerpad.ApiData.input.RegisterBody

/**
 * Created by alihajiloo on 8/18/18.
 */
data class UserRegisterData(var FirstName: String, var LastName: String, var Company: String, var MobileNumber: String, var EmailAddress: String, var Password: String) {
    companion object {
        fun setRegisterInformation(userRegisterData: UserRegisterData): RegisterBody {
            return RegisterBody(userRegisterData.FirstName, userRegisterData.LastName, userRegisterData.Company,
                    userRegisterData.MobileNumber, userRegisterData.EmailAddress,
                    userRegisterData.Password)
        }
    }
}