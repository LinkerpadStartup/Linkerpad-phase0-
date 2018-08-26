package com.linkerpad.linkerpad.Data

import com.linkerpad.linkerpad.ApiData.input.GetDailyActivityListBody

/**
 * Created by alihajiloo on 8/25/18.
 */
data class DailyActivityInformationData(var id: String, var projectId: String, var createdById: String,
                                        var reportDate: String, var title: String, var workloadUnit: String, var description: String, var numberOfCrew: Int,
                                        var workHours: Float, var workload: Float){
    companion object {
        fun setDailyActivityInformation(dailyActivityInformationData: DailyActivityInformationData):GetDailyActivityListBody{
            return GetDailyActivityListBody(dailyActivityInformationData.projectId,dailyActivityInformationData.reportDate)
        }
    }
}

data class DailyActivityInformationOutput(var status: String, var message: String, var responseObject: DailyActivityInformationData)
