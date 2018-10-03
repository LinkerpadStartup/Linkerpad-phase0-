package com.linkerpad.linkerpad.ApiData.output

import com.google.gson.annotations.SerializedName
import com.linkerpad.linkerpad.ApiData.Response
import com.linkerpad.linkerpad.Data.NoteAndEventInformationData
import java.util.*

/**
 * Created by alihajiloo on 8/27/18 at 13:00.
 */

/**
 * Created by alihajiloo on 8/26/18.
 */

class NoteAndEventListResponse : Response() {

    @SerializedName("responseObject")
    lateinit var responseObject: ArrayList<NoteAndEventInformationData>
}

//for delete edit create
class NoteAndEventResponse : Response() {
    @SerializedName("responseObject")
    lateinit var responseObject: Objects
}

class GetNoteAndEventInformationResponse : Response() {
    @SerializedName("responseObject")
    lateinit var responseObject: NoteAndEventInformationData
}
