package com.linkerpad.linkerpad.Data

import com.linkerpad.linkerpad.ApiData.input.CreateDailyActivityBody

/**
 * Created by alihajiloo on 8/25/18.
 */

data class CreateDailyActivityData(var projectId : String ,
                                   var reportDate:String , var title:String , var workloadUnit:String , var description:String , var numberOfCrew:Int ,
                                   var workHours:Float , var workload:Float){

    companion object {
        fun setCreateDailyBody(createDailyActivityData: CreateDailyActivityData):CreateDailyActivityBody{
            return CreateDailyActivityBody(createDailyActivityData.projectId,createDailyActivityData.reportDate , createDailyActivityData.title,createDailyActivityData.workloadUnit,
                    createDailyActivityData.description,createDailyActivityData.numberOfCrew , createDailyActivityData.workHours , createDailyActivityData.workload )
        }
    }
}