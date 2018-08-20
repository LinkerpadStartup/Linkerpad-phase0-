package com.linkerpad.linkerpad.Models

import com.linkerpad.linkerpad.ApiData.input.AddMemberBody
import com.linkerpad.linkerpad.Data.MemberData

/**
 * Created by alihajiloo on 8/20/18.
 */
data class MemberViewModel(var projectId: String, var emailAddress: String) {
    companion object {
        fun setAddMemberToProject(projectId: String, emailAddress: String): AddMemberBody {
            return MemberData.setAddMemberBody(MemberData(projectId, emailAddress))
        }
    }
}