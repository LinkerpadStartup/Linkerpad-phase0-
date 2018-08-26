package com.linkerpad.linkerpad.Data

import com.linkerpad.linkerpad.ApiData.input.CreateMachineryBody
import com.linkerpad.linkerpad.ApiData.input.DeleteMachineryBody
import com.linkerpad.linkerpad.ApiData.input.EditMachineryBody
import com.linkerpad.linkerpad.ApiData.input.GetMachineryListBody


/**
 * Created by alihajiloo on 8/26/18.
 */
data class CreateMachineryData(
        var projectId: String,
        var reportDate: String,
        var title: String,
        var description: String,
        var numberOfActiveEquipment: Int,
        var numberOfDeactiveEquipment: Int,
        var workHours: Float
) {

    companion object {
        fun setCreateMachinery(createMachineryData: CreateMachineryData): CreateMachineryBody {
            return CreateMachineryBody(createMachineryData.projectId, createMachineryData.reportDate, createMachineryData.title, createMachineryData.description,
                    createMachineryData.numberOfActiveEquipment, createMachineryData.numberOfDeactiveEquipment, createMachineryData.workHours)
        }
    }
}

data class MachineryInformationData(
        var id: String,
        var projectId: String,
        var createdById: String,
        var reportDate: String,
        var title: String,
        var description: String,
        var numberOfActiveEquipment: Int,
        var numberOfDeactiveEquipment: Int,
        var workHours: Float
) {
    companion object {
        fun setMachineryInformation(projectId: String, reportDate: String): GetMachineryListBody {
            return GetMachineryListBody(projectId, reportDate)
        }
    }
}

data class MachineryInformationOutput(var status: String, var message: String, var responseObject: MachineryInformationData)

data class MachineryInput(var equipmentId: String, var projectId: String) {
    companion object {
        fun setDeleteMachinery(MachineryInput: MachineryInput): DeleteMachineryBody {
            return DeleteMachineryBody(MachineryInput.equipmentId, MachineryInput.projectId)
        }

    }
}

data class EditMachineryData(
        var equipmentId: String,
        var projectId: String,
        var title: String,
        var description: String,
        var numberOfActiveEquipment: Int,
        var numberOfDeactiveEquipment: Int,
        var workHours: Float
) {
    companion object {
        fun setEditMachinery(editMachineryData: EditMachineryData): EditMachineryBody {
            return EditMachineryBody(
                    editMachineryData.equipmentId,
                    editMachineryData.projectId,
                    editMachineryData.title,
                    editMachineryData.description,
                    editMachineryData.numberOfActiveEquipment,
                    editMachineryData.numberOfDeactiveEquipment,
                    editMachineryData.workHours
            )
        }
    }
}