package com.linkerpad.linkerpad.ApiData

import com.google.gson.annotations.SerializedName

/**
 * Created by alihajiloo on 8/18/18.
 */

open class Response {
    @SerializedName("status")
    var status: String = ""

    @SerializedName("message")
    var message: String = ""

}
