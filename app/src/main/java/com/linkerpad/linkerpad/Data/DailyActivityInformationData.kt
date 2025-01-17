package com.linkerpad.linkerpad.Data

import com.linkerpad.linkerpad.ApiData.input.DeleteDailyActivityBody
import com.linkerpad.linkerpad.ApiData.input.EditDailyActivityBody
import com.linkerpad.linkerpad.ApiData.input.GetDailyActivityListBody

/**
 * Created by alihajiloo on 8/25/18.
 */
data class DailyActivityInformationData(var id: String, var projectId: String, var createdById: String,
                                        var reportDate: String, var title: String, var workloadUnit: String, var description: String, var numberOfCrew: Int,
                                        var workHours: Float, var workload: Float) {
    companion object {
        fun setDailyActivityInformation(dailyActivityInformationData: DailyActivityInformationData): GetDailyActivityListBody {
            return GetDailyActivityListBody(dailyActivityInformationData.projectId, dailyActivityInformationData.reportDate)
        }
    }
}

data class DailyActivityInformationOutput(var status: String, var message: String, var responseObject: DailyActivityInformationData)

data class DailyActivityInput(var dailyActivityId: String, var projectId: String) {
    companion object {
        fun setDeleteDailyActivity(dailyActivityInput: DailyActivityInput): DeleteDailyActivityBody {
            return DeleteDailyActivityBody(dailyActivityInput.dailyActivityId, dailyActivityInput.projectId)
        }

    }
}

data class EditDailyActivityData(
        var dailyActivityId: String,
        var projectId: String,
        var title: String,
        var workloadUnit: String,
        var description: String,
        var numberOfCrew: Int,
        var workHours: Float,
        var workload: Float
) {
    companion object {
        fun setEditDailyActivity(editDailyActivity: EditDailyActivityData): EditDailyActivityBody {
            return EditDailyActivityBody(
                    editDailyActivity.dailyActivityId,
                    editDailyActivity.projectId,
                    editDailyActivity.title,
                    editDailyActivity.workloadUnit,
                    editDailyActivity.description,
                    editDailyActivity.numberOfCrew,
                    editDailyActivity.workHours,
                    editDailyActivity.workload
            )
        }
    }
}