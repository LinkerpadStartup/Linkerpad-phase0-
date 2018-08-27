package com.linkerpad.linkerpad.Data

import com.linkerpad.linkerpad.ApiData.input.CreateMaterialBody
import com.linkerpad.linkerpad.ApiData.input.DeleteMaterialBody
import com.linkerpad.linkerpad.ApiData.input.EditMaterialBody
import com.linkerpad.linkerpad.ApiData.input.GetMaterialListBody

/**
 * Created by alihajiloo on 8/27/18 at 12:59.
 */

data class CreateMaterialData(
        var projectId: String,
        var reportDate: String,
        var title: String,
        var consumedQuantityUnit: String,
        var description: String,
        var consumedQuantity: Float
) {

    companion object {
        fun setCreateMaterial(createMaterialData: CreateMaterialData): CreateMaterialBody {
            return CreateMaterialBody(createMaterialData.projectId, createMaterialData.reportDate, createMaterialData.title, createMaterialData.consumedQuantityUnit,
                    createMaterialData.description, createMaterialData.consumedQuantity)
        }
    }
}

data class MaterialInformationData(
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
        fun setMaterialInformation(projectId: String, reportDate: String): GetMaterialListBody {
            return GetMaterialListBody(projectId, reportDate)
        }
    }
}

data class MaterialInformationOutput(var status: String, var message: String, var responseObject: MaterialInformationData)

data class MaterialInput(var materialId: String, var projectId: String) {
    companion object {
        fun setDeleteMaterial(materialInput: MaterialInput): DeleteMaterialBody {
            return DeleteMaterialBody(materialInput.materialId, materialInput.projectId)
        }

    }
}

data class EditMaterialData(
        var materialId: String,
        var projectId: String,
        var title: String,
        var consumedQuantityUnit: String,
        var description: String,
        var consumedQuantity: Float
) {
    companion object {
        fun setEditMaterial(editMaterialData: EditMaterialData): EditMaterialBody {
            return EditMaterialBody(
                    editMaterialData.materialId,
                    editMaterialData.projectId,
                    editMaterialData.title,
                    editMaterialData.consumedQuantityUnit,
                    editMaterialData.description,
                    editMaterialData.consumedQuantity
            )
        }
    }
}