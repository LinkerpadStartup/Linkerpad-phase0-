package com.linkerpad.linkerpad.ApiData.output

import com.google.gson.annotations.SerializedName
import com.linkerpad.linkerpad.ApiData.Response
import com.linkerpad.linkerpad.Data.DailyActivityInformationData

/**
 * Created by alihajiloo on 8/25/18.
 */
class DailyActivityListResponse : Response() {

    @SerializedName("responseObject")
    lateinit var responseObject: ArrayList<DailyActivityInformationData>
}
