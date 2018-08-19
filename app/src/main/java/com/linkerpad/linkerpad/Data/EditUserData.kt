package com.linkerpad.linkerpad.Data

import com.linkerpad.linkerpad.ApiData.input.EditUserBody

/**
 * Created by alihajiloo on 8/19/18.
 */
data class EditUserData(var firstName: String, var lastName: String, var company: String, var mobileNumber: String) {
    companion object {
        fun setEditedUserData(editUserData: EditUserData): EditUserBody {
            return EditUserBody(editUserData.firstName, editUserData.lastName, editUserData.company, editUserData.mobileNumber)
        }
    }
}