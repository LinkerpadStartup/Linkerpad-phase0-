package com.linkerpad.linkerpad.Data

import com.linkerpad.linkerpad.ApiData.input.AddMemberBody
import com.linkerpad.linkerpad.ApiData.input.RemoveMemberBody

/**
 * Created by alihajiloo on 8/20/18.
 */
data class MemberInformationData(var userRole: Int,
                                 var id: String,
                                 var firstName: String,
                                 var lastName: String,
                                 var profilePicture: String,
                                 var emailAddress: String,
                                 var mobileNumber: String,
                                 var company: String,
                                 var skill:String?)

data class MemberData(var id:String,var projectId: String, var emailAddress: String, var userRole: Int) {
    companion object {
        fun setAddMemberBody(memberData: MemberData): AddMemberBody {
            return AddMemberBody(memberData.projectId, memberData.emailAddress, memberData.userRole)
        }

        fun setRemoveMemberBody(memberData: MemberData): RemoveMemberBody {
            return RemoveMemberBody(memberData.id, memberData.projectId)
        }
    }
}