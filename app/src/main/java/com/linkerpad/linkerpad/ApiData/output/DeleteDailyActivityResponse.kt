package com.linkerpad.linkerpad.ApiData.output

import com.google.gson.annotations.SerializedName
import com.linkerpad.linkerpad.ApiData.Response
import java.util.*

/**
 * Created by alihajiloo on 8/26/18.
 */

class DeleteDailyActivityResponse:Response(){
    @SerializedName("responseObject")
    lateinit var responseObject: Objects
}