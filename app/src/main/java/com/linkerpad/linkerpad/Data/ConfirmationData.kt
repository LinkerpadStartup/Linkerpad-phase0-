package com.linkerpad.linkerpad.Data

import com.linkerpad.linkerpad.ApiData.input.*

/**
 * Created by alihajiloo on 8/27/18 at 12:59.
 */

data class CreateConfirmationData(
        var projectId: String,
        var reportDate: String

) {

    companion object {
        fun setCreateConfirmation(createConfirmationData: CreateConfirmationData): CreateConfirmationBody {
            return CreateConfirmationBody(createConfirmationData.projectId, createConfirmationData.reportDate)
        }
    }
}

data class ConfirmationInformationData(
        var confirmationStatus:Boolean,
        var id: String,
        var userRole: String,
        var firstName: String,
        var lastName: String,
        var profilePicture: String,
        var emailAddress: String,
        var mobileNumber: String,
        var company: String
) {
    companion object {
        fun setMaterialInformation(projectId: String, reportDate: String): GetConfirmationListBody {
            return GetConfirmationListBody(projectId, reportDate)
        }
    }
}

data class ConfirmationInformationOutput(var status: String, var message: String, var responseObject: ConfirmationInformationData)

data class ConfirmationInput( var projectId: String,var reportDate: String) {
    companion object {
        fun setDeleteConfirmation(confirmationInput: ConfirmationInput): DeleteConfirmationBody {
            return DeleteConfirmationBody(confirmationInput.projectId, confirmationInput.reportDate)
        }

    }
}

data class EditConfirmationData(
        var materialId: String,
        var projectId: String,
        var title: String,
        var consumedQuantityUnit: String,
        var description: String,
        var consumedQuantity: Float
) {
    companion object {
        fun setEditConfirmation(editMaterialData: EditConfirmationData): EditMaterialBody {
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