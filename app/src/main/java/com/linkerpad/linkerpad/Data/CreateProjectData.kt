package com.linkerpad.linkerpad.Data

import com.linkerpad.linkerpad.ApiData.input.CreateProjectBody

/**
 * Created by alihajiloo on 8/20/18.
 */

data class CreateProjectData(var ProjectPicture: String, var Name: String, var Code: String, var Address: String, var StartDate: String, var EndDate: String) {
    companion object {
        fun setCreateProjectBody(createProjectData: CreateProjectData): CreateProjectBody {


            return CreateProjectBody(createProjectData.ProjectPicture, createProjectData.Name, createProjectData.Code, createProjectData.Address, createProjectData.StartDate, createProjectData.EndDate)
        }
    }
}