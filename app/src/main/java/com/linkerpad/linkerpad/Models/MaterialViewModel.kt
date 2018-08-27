package com.linkerpad.linkerpad.Models

import com.linkerpad.linkerpad.ApiData.input.CreateMaterialBody
import com.linkerpad.linkerpad.ApiData.input.DeleteMaterialBody
import com.linkerpad.linkerpad.ApiData.input.EditMaterialBody
import com.linkerpad.linkerpad.Data.*

/**
 * Created by alihajiloo on 8/27/18 at 12:58.
 */

data class MaterialViewModel(
        var status: String,
        var message: String,
        var id: String,
        var projectId: String,
        var createdById: String,
        var reportDate: String,
        var title: String,
        var consumedQuantityUnit: String,
        var description: String,
        var consumedQuantity: Float
) {
    companion object {
        fun setCreateMaterialInformation(materialViewModel: MaterialViewModel): CreateMaterialBody {
            return CreateMaterialData.setCreateMaterial(CreateMaterialData(materialViewModel.projectId, materialViewModel.reportDate,
                    materialViewModel.title, materialViewModel.consumedQuantityUnit, materialViewModel.description, materialViewModel.consumedQuantity))
        }


        fun getMaterialInformation(materialInformationOutput: MaterialInformationOutput): MaterialInformationData {
            return MaterialInformationData(
                    materialInformationOutput.responseObject.id,
                    materialInformationOutput.responseObject.projectId,
                    materialInformationOutput.responseObject.createdById,
                    materialInformationOutput.responseObject.reportDate,
                    materialInformationOutput.responseObject.title,
                    materialInformationOutput.responseObject.consumedQuantityUnit,
                    materialInformationOutput.responseObject.description,
                    materialInformationOutput.responseObject.consumedQuantity
            )
        }

        fun setDeleteMaterial(materialId: String, projectId: String): DeleteMaterialBody {
            return MaterialInput.setDeleteMaterial(MaterialInput(materialId, projectId))
        }

        fun setEditMaterial(materialId: String,
                             projectId: String,
                             title: String,
                             consumedQuantityUnit: String,
                             description: String,
                             consumedQuantity: Float): EditMaterialBody {

            return EditMaterialData.setEditMaterial(EditMaterialData(
                    materialId,
                    projectId,
                    title,
                    consumedQuantityUnit,
                    description,
                    consumedQuantity))

        }
    }
}