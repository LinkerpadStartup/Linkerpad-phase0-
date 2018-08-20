package com.linkerpad.linkerpad.ApiData.output

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.linkerpad.linkerpad.ApiData.Response
import com.linkerpad.linkerpad.Data.ProjectInformationData


/**
 * Created by alihajiloo on 8/20/18.
 */

class ProjectListResponse : Response() {

    @SerializedName("responseObject")
    lateinit var responseObject: ArrayList<ProjectInformationData>

}