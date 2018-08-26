package com.linkerpad.linkerpad.ApiData.output

import com.google.gson.annotations.SerializedName
import com.linkerpad.linkerpad.ApiData.Response
import com.linkerpad.linkerpad.Data.MachineryInformationData
import java.util.*

/**
 * Created by alihajiloo on 8/26/18.
 */

class MachineryListResponse : Response() {

    @SerializedName("responseObject")
    lateinit var responseObject: ArrayList<MachineryInformationData>
}

//for delete edit create
class MachineryResponse : Response() {
    @SerializedName("responseObject")
    lateinit var responseObject: Objects
}

class GetMachineryInformationResponse : Response() {
    @SerializedName("responseObject")
    lateinit var responseObject: MachineryInformationData
}
