package com.linkerpad.linkerpad.Data

import com.linkerpad.linkerpad.ApiData.input.AddMemberBody

/**
 * Created by alihajiloo on 8/20/18.
 */
data class MemberData(var projectId: String, var emailAddress: String){
    companion object {
        fun setAddMemberBody(memberData: MemberData):AddMemberBody{
            return AddMemberBody(memberData.projectId,memberData.emailAddress)
        }
    }
}