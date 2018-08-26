package com.linkerpad.linkerpad.Models

import com.linkerpad.linkerpad.ApiData.input.CreateDailyActivityBody
import com.linkerpad.linkerpad.ApiData.input.DeleteDailyActivityBody
import com.linkerpad.linkerpad.ApiData.input.EditDailyActivityBody
import com.linkerpad.linkerpad.ApiData.input.GetDailyActivityListBody
import com.linkerpad.linkerpad.Data.*

/**
 * Created by alihajiloo on 8/25/18.
 */
data class DailyActivityViewModel(var status: String, var message: String, var id: String, var projectId: String, var createdById: String,
                                  var reportDate: String, var title: String, var workloadUnit: String, var description: String, var numberOfCrew: Int,
                                  var workHours: Float, var workload: Float) {
    companion object {
        fun setCreateDailyActivityInformation(dailyActivityViewModel: DailyActivityViewModel): CreateDailyActivityBody {
            return CreateDailyActivityData.setCreateDailyBody(CreateDailyActivityData(dailyActivityViewModel.projectId, dailyActivityViewModel.reportDate,
                    dailyActivityViewModel.title, dailyActivityViewModel.workloadUnit, dailyActivityViewModel.description, dailyActivityViewModel.numberOfCrew, dailyActivityViewModel.workHours, dailyActivityViewModel.workload))
        }

        fun setDailyActivityList(projectId: String, reportDate: String): GetDailyActivityListBody {
            return DailyActivityInformationData.setDailyActivityInformation(DailyActivityInformationData(projectId = projectId, reportDate = reportDate, id = "", createdById = "", title = "",
                    description = "", numberOfCrew = 0, workHours = 0F, workload = 0F, workloadUnit = ""))
        }

        fun getDailyAcivityInformation(dailyActivityInformationOutput: DailyActivityInformationOutput): DailyActivityInformationData {
            return DailyActivityInformationData(dailyActivityInformationOutput.responseObject.id, dailyActivityInformationOutput.responseObject.projectId, dailyActivityInformationOutput.responseObject.createdById, dailyActivityInformationOutput.responseObject.reportDate, dailyActivityInformationOutput.responseObject.title, dailyActivityInformationOutput.responseObject.workloadUnit, dailyActivityInformationOutput.responseObject.description, dailyActivityInformationOutput.responseObject.numberOfCrew, dailyActivityInformationOutput.responseObject.workHours, dailyActivityInformationOutput.responseObject.workload)
        }

        fun setDeleteDailyActivity(dailyActivityId: String, projectId: String): DeleteDailyActivityBody {
            return DailyActivityInput.setDeleteDailyActivity(DailyActivityInput(dailyActivityId, projectId))
        }

        fun setEditDailyActivity(dailyActivityId: String,
                                 projectId: String,
                                 title: String,
                                 workloadUnit: String,
                                 description: String,
                                 numberOfCrew: Int,
                                 workHours: Float,
                                 workload: Float): EditDailyActivityBody {

            return EditDailyActivityData.setEditDailyActivity(EditDailyActivityData(dailyActivityId, projectId, title, workloadUnit, description, numberOfCrew, workHours, workload))

        }
    }
}