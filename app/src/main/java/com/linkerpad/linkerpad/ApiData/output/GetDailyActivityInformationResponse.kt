package com.linkerpad.linkerpad.ApiData.output

import com.google.gson.annotations.SerializedName
import com.linkerpad.linkerpad.ApiData.Response
import com.linkerpad.linkerpad.Data.DailyActivityInformationData

/**
 * Created by alihajiloo on 8/26/18.
 */
class GetDailyActivityInformationResponse:Response(){
    @SerializedName("responseObject")
lateinit var responseObject:DailyActivityInformationData
}

