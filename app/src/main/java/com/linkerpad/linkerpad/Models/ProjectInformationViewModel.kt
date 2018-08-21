package com.linkerpad.linkerpad.Models

import com.linkerpad.linkerpad.ApiData.input.CreateProjectBody
import com.linkerpad.linkerpad.Data.CreateProjectData
import com.linkerpad.linkerpad.Data.JalaliCalendar
import com.linkerpad.linkerpad.Data.ProjectInformationData

/**
 * Created by alihajiloo on 8/20/18.
 */

data class ProjectInformationViewModel(var status: String, var message: String, var projectPicture: String, var id: String, var name: String, var code: String, var address: String, var startDate: String, var endDate: String, var maximunmMember: String) {

    companion object {
        fun setCreateProjectInformation(projectInformationViewModel: ProjectInformationViewModel): CreateProjectBody {
            var startDate: String = ""
            if (projectInformationViewModel.startDate.toString() != "") {
                var jalaliStart: JalaliCalendar.YearMonthDate = JalaliCalendar.YearMonthDate(projectInformationViewModel.startDate.toString().split("/")[0].toInt(), projectInformationViewModel.startDate.toString().split("/")[1].toInt(), projectInformationViewModel.startDate.toString().split("/")[2].toInt())
                var grogorianStart: JalaliCalendar.YearMonthDate = JalaliCalendar.jalaliToGregorian(jalaliStart)
                startDate = grogorianStart.toString().replace("/", "-") + "T00:00"

            }
            var endDate: String = ""
            if (projectInformationViewModel.endDate.toString() != "") {
                var jalaliEnd: JalaliCalendar.YearMonthDate = JalaliCalendar.YearMonthDate(projectInformationViewModel.endDate.toString().split("/")[0].toInt(), projectInformationViewModel.endDate.toString().split("/")[1].toInt(), projectInformationViewModel.endDate.toString().split("/")[2].toInt())
                var grogorianEnd: JalaliCalendar.YearMonthDate = JalaliCalendar.jalaliToGregorian(jalaliEnd)
                endDate = grogorianEnd.toString().replace("/", "-") + "T00:00"
            }
            return CreateProjectData.setCreateProjectBody(CreateProjectData(projectInformationViewModel.projectPicture, projectInformationViewModel.name, projectInformationViewModel.code, projectInformationViewModel.address, startDate, endDate))
        }

        fun getProjectInformation(projectInformationData: ProjectInformationData): ProjectInformationViewModel {
            return ProjectInformationViewModel("", "", projectInformationData.projectPicture, projectInformationData.id, projectInformationData.name, projectInformationData.code, projectInformationData.address, projectInformationData.startDate, projectInformationData.endDate, projectInformationData.maximunmMember
            )
        }
    }

}