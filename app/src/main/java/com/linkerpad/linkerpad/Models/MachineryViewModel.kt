package com.linkerpad.linkerpad.Models

import com.linkerpad.linkerpad.ApiData.input.CreateMachineryBody
import com.linkerpad.linkerpad.ApiData.input.DeleteMachineryBody
import com.linkerpad.linkerpad.ApiData.input.EditMachineryBody
import com.linkerpad.linkerpad.ApiData.input.GetMachineryListBody
import com.linkerpad.linkerpad.Data.CreateMachineryData
import com.linkerpad.linkerpad.Data.MachineryInformationData
import com.linkerpad.linkerpad.Data.MachineryInformationOutput
import com.linkerpad.linkerpad.Data.MachineryInput
import com.linkerpad.linkerpad.Data.EditMachineryData


/**
 * Created by alihajiloo on 8/25/18.
 */
data class MachineryViewModel(var status: String, var message: String, var id: String, var projectId: String, var createdById: String,
                              var reportDate: String, var title: String, var description: String, var numberOfActiveEquipment: Int,
                              var numberOfDeactiveEquipment: Int, var workHours: Float) {
    companion object {
        fun setCreateMachineryInformation(machineryViewModel: MachineryViewModel): CreateMachineryBody {
            return CreateMachineryData.setCreateMachinery(CreateMachineryData(machineryViewModel.projectId, machineryViewModel.reportDate,
                    machineryViewModel.title, machineryViewModel.description, machineryViewModel.numberOfActiveEquipment, machineryViewModel.numberOfDeactiveEquipment, machineryViewModel.workHours))
        }

        fun setMachineryList(projectId: String, reportDate: String): GetMachineryListBody {
            return MachineryInformationData.setMachineryInformation(projectId, reportDate)
        }

        fun getMachineryInformation(MachineryInformationOutput: MachineryInformationOutput): MachineryInformationData {
            return MachineryInformationData(
                    MachineryInformationOutput.responseObject.id,
                    MachineryInformationOutput.responseObject.projectId,
                    MachineryInformationOutput.responseObject.createdById,
                    MachineryInformationOutput.responseObject.reportDate,
                    MachineryInformationOutput.responseObject.title,
                    MachineryInformationOutput.responseObject.description,
                    MachineryInformationOutput.responseObject.numberOfActiveEquipment,
                    MachineryInformationOutput.responseObject.numberOfDeactiveEquipment,
                    MachineryInformationOutput.responseObject.workHours
            )
        }

        fun setDeleteMachinery(EquipmentId: String, projectId: String): DeleteMachineryBody {
            return MachineryInput.setDeleteMachinery(MachineryInput(EquipmentId, projectId))
        }

        fun setEditMachinery(EquipmentId: String,
                             projectId: String,
                             title: String,
                             description: String,
                             numberOfActiveEquipment: Int,
                             numberOfDeactiveEquipment: Int,
                             workHours: Float): EditMachineryBody {

            return EditMachineryData.setEditMachinery(EditMachineryData(EquipmentId, projectId, title, description, numberOfActiveEquipment, numberOfDeactiveEquipment, workHours))

        }
    }
}