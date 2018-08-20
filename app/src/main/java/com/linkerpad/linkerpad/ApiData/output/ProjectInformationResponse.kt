package com.linkerpad.linkerpad.ApiData.output

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.linkerpad.linkerpad.ApiData.Response
import com.linkerpad.linkerpad.Data.ProjectInformationData

/**
 * Created by alihajiloo on 8/20/18.
 */
class ProjectInformationResponse : Response() {

    @SerializedName("responseObject")
    @Expose
    lateinit var responseObject: ProjectInformationData
}

