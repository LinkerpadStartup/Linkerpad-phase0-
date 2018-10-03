package com.linkerpad.linkerpad.ApiData.input

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by alihajiloo on 8/27/18 at 13:01.
 */

data class CreateNoteAndEventBody(
        @SerializedName("ProjectId")
        @Expose
        var projectId: String,

        @SerializedName("ReportDate")
        @Expose
        var reportDate: String,

        @SerializedName("Title")
        @Expose
        var title: String,

        @SerializedName("Description")
        @Expose
        var description: String

)

data class DeleteNoteAndEventBody(
        @SerializedName("NoteId")
        @Expose
        var UserId: String,

        @SerializedName("ProjectId")
        @Expose
        var ProjectId: String


)

class EditNoteAndEventBody(
        @SerializedName("NoteId")
        @Expose
        var dailyActivityId: String,

        @SerializedName("ProjectId")
        @Expose
        var projectId: String,

        @SerializedName("Title")
        @Expose
        var title: String,

        @SerializedName("Description")
        @Expose
        var description: String
)

data class GetNoteAndEventListBody(
        @SerializedName("ProjectId")
        @Expose
        var projectId: String,

        @SerializedName("ReportDate")
        @Expose
        var reportDate: String
)