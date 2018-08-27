package com.linkerpad.linkerpad.ApiData.output

import com.google.gson.annotations.SerializedName
import com.linkerpad.linkerpad.ApiData.Response
import com.linkerpad.linkerpad.Data.MaterialInformationData
import java.util.*

/**
 * Created by alihajiloo on 8/27/18 at 13:00.
 */

/**
 * Created by alihajiloo on 8/26/18.
 */

class MaterialListResponse : Response() {

    @SerializedName("responseObject")
    lateinit var responseObject: ArrayList<MaterialInformationData>
}

//for delete edit create
class MaterialResponse : Response() {
    @SerializedName("responseObject")
    lateinit var responseObject: Objects
}

class GetMaterialInformationResponse : Response() {
    @SerializedName("responseObject")
    lateinit var responseObject: MaterialInformationData
}
