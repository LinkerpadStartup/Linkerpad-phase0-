package com.linkerpad.linkerpad.Models

import com.linkerpad.linkerpad.ApiData.input.CreateDailyActivityBody
import com.linkerpad.linkerpad.Data.CreateDailyActivityData

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
    }
}