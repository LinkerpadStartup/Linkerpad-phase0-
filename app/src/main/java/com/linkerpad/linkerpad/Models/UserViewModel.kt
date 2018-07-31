package com.linkerpad.linkerpad.Models

import com.linkerpad.linkerpad.Data.UsersData


/**
 * Created by alihajiloo on 7/31/18.
 */
data class UserViewModel(var Name: String, var Email: String, var PassWord: String, var CompanyName: String, var PhoneNumber: String) {
    companion object {
        fun getUserViewModel(usersData: UsersData): UserViewModel {
            return UserViewModel(usersData.Name, usersData.Email, usersData.PassWord, usersData.CompanyName, usersData.PhoneNumber)

        }

        fun getUsersData(userViewModel: UserViewModel): UsersData {
            return UsersData(userViewModel.Name, userViewModel.Email, userViewModel.PassWord, userViewModel.CompanyName, userViewModel.PhoneNumber)
        }
    }
}