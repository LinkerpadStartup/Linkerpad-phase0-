package com.linkerpad.linkerpad.Data

import com.linkerpad.linkerpad.ApiData.input.AddMemberBody

/**
 * Created by alihajiloo on 8/20/18.
 */
data class MemberInformationData(var userRole: Int,
                                 var firstName: String,
                                 var lastName: String,
                                 var profilePicture: String,
                                 var emailAddress: String,
                                 var mobileNumber: String,
                                 var company: String)

data class MemberData(var projectId: String, var emailAddress: String, var userRole: Int) {
    companion object {
        fun setAddMemberBody(memberData: MemberData): AddMemberBody {
            return AddMemberBody(memberData.projectId, memberData.emailAddress, memberData.userRole)
        }
    }
}