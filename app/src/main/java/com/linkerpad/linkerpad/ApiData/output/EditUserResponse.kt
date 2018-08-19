package com.linkerpad.linkerpad.ApiData.output

import com.google.gson.annotations.SerializedName
import com.linkerpad.linkerpad.ApiData.Response
import java.util.*

/**
 * Created by alihajiloo on 8/19/18.
 */
class EditUserResponse : Response() {
    @SerializedName("responseObject")
    lateinit var responseObject: Objects
}