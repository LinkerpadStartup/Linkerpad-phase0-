package com.linkerpad.linkerpad.Models

import com.linkerpad.linkerpad.ApiData.input.AddMemberBody
import com.linkerpad.linkerpad.ApiData.input.RemoveMemberBody
import com.linkerpad.linkerpad.Data.MemberData

/**
 * Created by alihajiloo on 8/20/18.
 */
data class MemberViewModel(var projectId: String, var emailAddress: String, var userRole: Int) {
    companion object {
        fun setAddMemberToProject(projectId: String, emailAddress: String, userRole: Int): AddMemberBody {
            return MemberData.setAddMemberBody(MemberData("",projectId, emailAddress, userRole))
        }

        fun removeMember(projectId: String, id: String): RemoveMemberBody {
            return MemberData.setRemoveMemberBody(MemberData(id, projectId, "",0))
        }
    }
}