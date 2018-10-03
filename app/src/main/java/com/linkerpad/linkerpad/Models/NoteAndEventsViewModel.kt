package com.linkerpad.linkerpad.Models

import com.linkerpad.linkerpad.ApiData.input.*
import com.linkerpad.linkerpad.Data.*

/**
 * Created by alihajiloo on 8/27/18 at 12:58.
 */

data class NoteAndEventsViewModel(
        var status: String,
        var message: String,
        var id: String,
        var projectId: String,
        var createdById: String,
        var reportDate: String,
        var title: String,
        var description: String
) {
    companion object {
        fun setCreateNoteAndEventInformation(noteAndEventViewModel: NoteAndEventsViewModel): CreateNoteAndEventBody {
            return CreateNoteAndEventData.setCreateNoteAndEvent(CreateNoteAndEventData(
                    noteAndEventViewModel.projectId,
                    noteAndEventViewModel.reportDate,
                    noteAndEventViewModel.title,
                    noteAndEventViewModel.description
            ))
        }


        fun getNoteAndEventInformation(NoteAndEventInformationOutput: NoteAndEventInformationOutput): NoteAndEventInformationData {
            return NoteAndEventInformationData(
                    NoteAndEventInformationOutput.responseObject.id,
                    NoteAndEventInformationOutput.responseObject.projectId,
                    NoteAndEventInformationOutput.responseObject.createdById,
                    NoteAndEventInformationOutput.responseObject.reportDate,
                    NoteAndEventInformationOutput.responseObject.title,
                    NoteAndEventInformationOutput.responseObject.description
            )
        }

        fun setDeleteNoteAndEvent(notelId: String, projectId: String): DeleteNoteAndEventBody {
            return NoteAndEventInput.setDeleteNoteAndEvent(NoteAndEventInput(notelId, projectId))
        }

        fun setEditNoteAndEvent(materialId: String,
                                projectId: String,
                                title: String,
                                description: String): EditNoteAndEventBody {

            return EditNoteAndEventData.setEditNoteAndEvent(EditNoteAndEventData(
                    materialId,
                    projectId,
                    title,
                    description))

        }
    }
}