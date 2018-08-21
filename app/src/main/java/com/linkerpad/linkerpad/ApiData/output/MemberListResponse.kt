package com.linkerpad.linkerpad.ApiData.output

import com.google.gson.annotations.SerializedName
import com.linkerpad.linkerpad.ApiData.Response
import com.linkerpad.linkerpad.Data.MemberInformationData

/**
 * Created by alihajiloo on 8/21/18.
 */
class MemberListResponse : Response() {
    @SerializedName("responseObject")
    lateinit var responseObject: ArrayList<MemberInformationData>
}