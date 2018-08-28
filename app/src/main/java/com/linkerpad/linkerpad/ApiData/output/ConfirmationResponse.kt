package com.linkerpad.linkerpad.ApiData.output

import com.google.gson.annotations.SerializedName
import com.linkerpad.linkerpad.ApiData.Response
import com.linkerpad.linkerpad.Data.ConfirmationInformationData
import com.linkerpad.linkerpad.Data.MaterialInformationData
import java.util.*

/**
 * Created by alihajiloo on 8/27/18 at 13:00.
 */

/**
 * Created by alihajiloo on 8/26/18.
 */

class ConfirmationListResponse : Response() {

    @SerializedName("responseObject")
    lateinit var responseObject: ArrayList<ConfirmationInformationData>
}

//for delete edit create
class ConfirmationResponse : Response() {
    @SerializedName("responseObject")
    lateinit var responseObject: Objects
}

class GetConfirmationInformationResponse : Response() {
    @SerializedName("responseObject")
    lateinit var responseObject: ConfirmationInformationData
}
