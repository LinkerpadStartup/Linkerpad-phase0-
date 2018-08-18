package com.linkerpad.linkerpad.Models

import com.linkerpad.linkerpad.ApiData.input.LoginBody
import com.linkerpad.linkerpad.ApiData.input.RegisterBody
import com.linkerpad.linkerpad.Data.LoginInputData
import com.linkerpad.linkerpad.Data.LoginOutputData
import com.linkerpad.linkerpad.Data.UserRegisterData
import ir.mirrajabi.persiancalendar.core.models.PersianDate
import java.sql.Date


/**
 * Created by alihajiloo on 8/18/18.
 */
data class UserInformationViewModel(var status: String, var message: String, var token: String, var expirationDate: String, var firstName: String, var lastName: String, var profilePicture: Any? = null, var emailAddress: String, var mobileNumber: String, var company: String, var password: String) {
    companion object {
        fun getUserInformation(loginOutPut: LoginOutputData): UserInformationViewModel {

/*
            var persianDate : PersianDate = PersianDate((loginOutPut.expirationDate as Date).year,(loginOutPut.expirationDate as Date).month,(loginOutPut.expirationDate as Date).day)
*/

            return UserInformationViewModel(loginOutPut.status, loginOutPut.message, loginOutPut.token, loginOutPut.expirationDate, loginOutPut.userInformationViewModel.firstName, loginOutPut.userInformationViewModel.lastName,
                    loginOutPut.userInformationViewModel.profilePicture, loginOutPut.userInformationViewModel.emailAddress, loginOutPut.userInformationViewModel.mobileNumber, loginOutPut.userInformationViewModel.company,"")
        }

        fun setUsernamePassword(username: String, password: String): LoginBody {
            return LoginInputData.setLoginBody(LoginInputData(username, password))
        }

        fun setRegisterInformation(userInformationViewModel: UserInformationViewModel): RegisterBody {
            return UserRegisterData.setRegisterInformation(UserRegisterData(userInformationViewModel.firstName, userInformationViewModel.lastName, userInformationViewModel.company, userInformationViewModel.mobileNumber, userInformationViewModel.emailAddress, userInformationViewModel.password))
        }

    }


}

