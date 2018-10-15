package com.linkerpad.linkerpad.ApiData.output

import com.google.gson.annotations.SerializedName
import com.linkerpad.linkerpad.ApiData.Response
import com.linkerpad.linkerpad.Data.MachineryInformationData
import java.util.*

/**
 * Created by alihajiloo on 8/26/18.
 */


//for delete edit create
class MemberResponse : Response() {
    @SerializedName("responseObject")
    lateinit var responseObject: Objects
}


