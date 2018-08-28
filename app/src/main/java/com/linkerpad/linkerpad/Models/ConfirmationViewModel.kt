package com.linkerpad.linkerpad.Models

import com.linkerpad.linkerpad.ApiData.input.*
import com.linkerpad.linkerpad.Data.*

/**
 * Created by alihajiloo on 8/27/18 at 12:58.
 */

data class ConfirmationViewModel(
        var status: String,
        var message: String,
        var projectId: String,
        var reportDate: String,
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
        fun setCreateConfirmationInformation(projectId: String, reportDate: String): CreateConfirmationBody {
            return CreateConfirmationData.setCreateConfirmation(CreateConfirmationData(projectId, reportDate))
        }


        fun getConfirmationInformation(confirmationInformationOutput: ConfirmationInformationOutput): ConfirmationInformationData {
            return ConfirmationInformationData(
                    confirmationInformationOutput.responseObject.confirmationStatus,
                    confirmationInformationOutput.responseObject.id,
                    confirmationInformationOutput.responseObject.userRole,
                    confirmationInformationOutput.responseObject.firstName,
                    confirmationInformationOutput.responseObject.lastName,
                    confirmationInformationOutput.responseObject.profilePicture,
                    confirmationInformationOutput.responseObject.emailAddress,
                    confirmationInformationOutput.responseObject.mobileNumber,
                    confirmationInformationOutput.responseObject.company
            )
        }

        fun setDeleteConfirmation(projectId: String, reportDate: String): DeleteConfirmationBody {
            return ConfirmationInput.setDeleteConfirmation(ConfirmationInput(projectId, reportDate))
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