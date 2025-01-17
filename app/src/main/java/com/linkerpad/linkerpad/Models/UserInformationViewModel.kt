package com.linkerpad.linkerpad.Models

import com.linkerpad.linkerpad.ApiData.input.EditUserBody
import com.linkerpad.linkerpad.ApiData.input.LoginBody
import com.linkerpad.linkerpad.ApiData.input.RegisterBody
import com.linkerpad.linkerpad.Data.*
import ir.mirrajabi.persiancalendar.core.models.PersianDate
import java.sql.Date


/**
 * Created by alihajiloo on 8/18/18.
 */
data class UserInformationViewModel(
        var status: String,
        var message: String,
        var token: String,
        var expirationDate: String,
        var firstName: String,
        var lastName: String,
        var profilePicture: String? = null,
        var emailAddress: String,
        var mobileNumber: String,
        var company: String,
        var password: String,
        var skill: String? = null
) {
    companion object {
        fun getUserInformation(loginOutPut: LoginOutputData): UserInformationViewModel {

/*
            var persianDate : PersianDate = PersianDate((loginOutPut.expirationDate as Date).year,(loginOutPut.expirationDate as Date).month,(loginOutPut.expirationDate as Date).day)
*/

            return UserInformationViewModel(
                    loginOutPut.status,
                    loginOutPut.message,
                    loginOutPut.token,
                    loginOutPut.expirationDate,
                    loginOutPut.userInformationViewModel.firstName,
                    loginOutPut.userInformationViewModel.lastName,
                    loginOutPut.userInformationViewModel.profilePicture,
                    loginOutPut.userInformationViewModel.emailAddress,
                    loginOutPut.userInformationViewModel.mobileNumber,
                    loginOutPut.userInformationViewModel.company,
                    ""
            )
        }

        fun setUsernamePassword(username: String, password: String): LoginBody {
            return LoginInputData.setLoginBody(LoginInputData(username, password))
        }

        fun setRegisterInformation(userInformationViewModel: UserInformationViewModel): RegisterBody {
            return UserRegisterData.setRegisterInformation(UserRegisterData(
                    userInformationViewModel.firstName,
                    userInformationViewModel.lastName,
                    userInformationViewModel.company,
                    userInformationViewModel.mobileNumber,
                    userInformationViewModel.emailAddress,
                    userInformationViewModel.password
            ))
        }

        fun getUserInformation(userInformationOutputData: UserInformationOutputData): UserInformationViewModel {
            return UserInformationViewModel(
                    userInformationOutputData.status,
                    userInformationOutputData.message,
                    firstName = userInformationOutputData.responseObject.firstName,
                    lastName = userInformationOutputData.responseObject.lastName,
                    emailAddress = userInformationOutputData.responseObject.emailAddress,
                    mobileNumber = userInformationOutputData.responseObject.mobileNumber,
                    company = userInformationOutputData.responseObject.company,
                    expirationDate = "",
                    token = "",
                    password = "",
                    profilePicture = userInformationOutputData.responseObject.profilePicture,
                    skill = userInformationOutputData.responseObject.skill
            )
        }

        fun setUserEditedInformation(firstName: String, lastName: String, company: String, mobileNumber: String, profilePicture: String?, skill: String?): EditUserBody {
            return EditUserData.setEditedUserData(EditUserData(firstName, lastName, company, mobileNumber, profilePicture, skill))
        }

    }


}

