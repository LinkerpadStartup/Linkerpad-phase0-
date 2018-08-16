package com.linkerpad.linkerpad.Models

import com.linkerpad.linkerpad.Data.UsersData
import java.util.*


data class userInformationViewModel(var firstName: String, var lastName: String, var profilePicture: Any?=null, var emailAddress: String, var mobileNumber: String, var company: String)
/**
 * Created by alihajiloo on 7/31/18.
 */
/*data class userInformationViewModel(var FirstName: String, var LastName: String, var Company: String, var MobileNumber: String, var EmailAddress: String, var Password: String) {
    companion object {
        fun getUserViewModel(usersData: UsersData): userInformationViewModel {
            return userInformationViewModel(usersData.FirstName, usersData.LastName, usersData.Company, usersData.MobileNumber, usersData.EmailAddress, usersData.Password)
        }

        fun getUsersData(userViewModel: userInformationViewModel): UsersData {
            return UsersData(userViewModel.FirstName, userViewModel.LastName, userViewModel.Company, userViewModel.MobileNumber, userViewModel.EmailAddress, userViewModel.Password)
        }
    }
}*/