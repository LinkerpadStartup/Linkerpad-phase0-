package com.linkerpad.linkerpad.Data

import com.linkerpad.linkerpad.ApiData.input.EditProjectBody

/**
 * Created by alihajiloo on 8/25/18.
 */
data class EditProjectData(var id: String, var ProjectPicture: String, var Name: String, var Code: String, var Address: String, var StartDate: String, var EndDate: String) {
    companion object {
        fun setEditProjectBody(editProjectData: EditProjectData): EditProjectBody {


            return EditProjectBody(editProjectData.id, editProjectData.ProjectPicture, editProjectData.Name, editProjectData.Code, editProjectData.Address, editProjectData.StartDate, editProjectData.EndDate)
        }
    }
}