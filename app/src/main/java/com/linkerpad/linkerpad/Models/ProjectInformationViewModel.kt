package com.linkerpad.linkerpad.Models

import com.linkerpad.linkerpad.ApiData.input.CreateProjectBody
import com.linkerpad.linkerpad.Data.CreateProjectData
import com.linkerpad.linkerpad.Data.JalaliCalendar

/**
 * Created by alihajiloo on 8/20/18.
 */

data class ProjectInformationViewModel(var status: String, var message: String, var projectPicture: String, var id: String, var name: String, var code: String, var address: String, var startDate: String, var endDate: String, var maximunmMember: String) {

    companion object {
        fun setCreateProjectInformation(projectInformationViewModel: ProjectInformationViewModel): CreateProjectBody {

            var jalaliStart: JalaliCalendar.YearMonthDate = JalaliCalendar.YearMonthDate(projectInformationViewModel.startDate.toString().split("/")[0].toInt(), projectInformationViewModel.startDate.toString().split("/")[1].toInt(), projectInformationViewModel.startDate.toString().split("/")[2].toInt())
            var jalaliEnd: JalaliCalendar.YearMonthDate = JalaliCalendar.YearMonthDate(projectInformationViewModel.endDate.toString().split("/")[0].toInt(), projectInformationViewModel.endDate.toString().split("/")[1].toInt(), projectInformationViewModel.endDate.toString().split("/")[2].toInt())
            var grogorianStart: JalaliCalendar.YearMonthDate = JalaliCalendar.jalaliToGregorian(jalaliStart)
            var grogorianEnd: JalaliCalendar.YearMonthDate = JalaliCalendar.jalaliToGregorian(jalaliEnd)
            var startDate:String = grogorianStart.toString().replace("/","-")+"T00:00"
            var endDate:String = grogorianEnd.toString().replace("/","-")+"T00:00"

            return CreateProjectData.setCreateProjectBody(CreateProjectData(projectInformationViewModel.projectPicture, projectInformationViewModel.name, projectInformationViewModel.code, projectInformationViewModel.address, startDate, endDate))
        }
    }

}