package com.linkerpad.linkerpad.Data

import com.linkerpad.linkerpad.ApiData.input.*

/**
 * Created by alihajiloo on 8/27/18 at 12:59.
 */

data class CreateNoteAndEventData(
        var projectId: String,
        var reportDate: String,
        var title: String,
        var description: String
) {

    companion object {
        fun setCreateNoteAndEvent(createNoteAndEventData: CreateNoteAndEventData): CreateNoteAndEventBody {
            return CreateNoteAndEventBody(
                    createNoteAndEventData.projectId,
                    createNoteAndEventData.reportDate,
                    createNoteAndEventData.title,
                    createNoteAndEventData.description
            )
        }
    }
}

data class NoteAndEventInformationData(
        var id: String,
        var projectId: String,
        var createdById: String,
        var reportDate: String,
        var title: String,
        var description: String
) {
    companion object {
        fun setNoteAndEventInformation(projectId: String, reportDate: String): GetNoteAndEventListBody {
            return GetNoteAndEventListBody(projectId, reportDate)
        }
    }
}

data class NoteAndEventInformationOutput(var status: String, var message: String, var responseObject: NoteAndEventInformationData)

data class NoteAndEventInput(var noteId: String, var projectId: String) {
    companion object {
        fun setDeleteNoteAndEvent(NoteAndEventInput: NoteAndEventInput): DeleteNoteAndEventBody {
            return DeleteNoteAndEventBody(NoteAndEventInput.noteId, NoteAndEventInput.projectId)
        }

    }
}

data class EditNoteAndEventData(
        var noteId: String,
        var projectId: String,
        var title: String,
        var description: String
) {
    companion object {
        fun setEditNoteAndEvent(editNoteAndEventData: EditNoteAndEventData): EditNoteAndEventBody {
            return EditNoteAndEventBody(
                    editNoteAndEventData.noteId,
                    editNoteAndEventData.projectId,
                    editNoteAndEventData.title,
                    editNoteAndEventData.description
            )
        }
    }
}