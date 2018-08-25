package com.linkerpad.linkerpad.Models

import com.linkerpad.linkerpad.ApiData.input.CreateProjectBody
import com.linkerpad.linkerpad.ApiData.input.EditProjectBody
import com.linkerpad.linkerpad.Data.CreateProjectData
import com.linkerpad.linkerpad.Data.EditProjectData
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
            var startDate: String = ""
            if (projectInformationData.startDate.toString() != "") {
                projectInformationData.startDate = projectInformationData.startDate.replace("T00:00:00", "")
                var gregorianStart: JalaliCalendar.YearMonthDate = JalaliCalendar.YearMonthDate(projectInformationData.startDate.toString().split("-")[0].toInt(), projectInformationData.startDate.toString().split("-")[1].toInt(), projectInformationData.startDate.toString().split("-")[2].toInt())
                var jalaliStart: JalaliCalendar.YearMonthDate = JalaliCalendar.gregorianToJalali(gregorianStart)
                startDate = jalaliStart.toString().replace("-", "/")

            }
            var endDate: String = ""
            if (projectInformationData.endDate.toString() != "") {
                projectInformationData.endDate = projectInformationData.endDate.replace("T00:00:00", "")
                var gregorianEnd: JalaliCalendar.YearMonthDate = JalaliCalendar.YearMonthDate(projectInformationData.endDate.toString().split("-")[0].toInt(), projectInformationData.endDate.toString().split("-")[1].toInt(), projectInformationData.endDate.toString().split("-")[2].toInt())
                var jalaliEnd: JalaliCalendar.YearMonthDate = JalaliCalendar.gregorianToJalali(gregorianEnd)
                endDate = jalaliEnd.toString().replace("-", "/")
            }
            return ProjectInformationViewModel("", "", projectInformationData.projectPicture, projectInformationData.id, projectInformationData.name, projectInformationData.code, projectInformationData.address, startDate, endDate, projectInformationData.maximunmMember
            )
        }

        fun setEditProjectInformation(projectInformationViewModel: ProjectInformationViewModel): EditProjectBody {
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
            return EditProjectData.setEditProjectBody(EditProjectData(projectInformationViewModel.id,projectInformationViewModel.projectPicture, projectInformationViewModel.name, projectInformationViewModel.code, projectInformationViewModel.address, startDate, endDate))
        }
    }

}