package com.linkerpad.linkerpad.Adapters

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ViewTarget
import com.linkerpad.linkerpad.*
import com.linkerpad.linkerpad.ApiData.output.ConfirmationResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.ConfirmationInformationData
import com.linkerpad.linkerpad.Data.MaterialInformationData
import com.linkerpad.linkerpad.Models.ConfirmationViewModel
import kotlinx.android.synthetic.main.confirmation_item.view.*
import kotlinx.android.synthetic.main.materials_item.view.*
import retrofit2.Call
import retrofit2.Response


/**
 * Created by alihajiloo on 8/25/18.
 */

class ConfirmationAdapter(var context: Context, var data: ArrayList<ConfirmationInformationData>, var projectId: String, var token: String, var reportDate: String) : RecyclerView.Adapter<ConfirmationAdapter.ViewHolder>() {

    lateinit var progressDialog: ProgressDialog

    var showcaseView: ShowcaseView? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.confirmation_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.onBind(data[position], position)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(itemModel: ConfirmationInformationData, position: Int) {

            itemView.confirmationNameTv.text = itemModel.firstName + " " + itemModel.lastName
            itemView.confirmationRoleTv.text = if (itemModel.userRole.toInt() == 0) "مسئول" else if (itemModel.userRole.toInt() == 1) "مدیر" else if (itemModel.userRole.toInt() == 2) "سرپرست" else "کارشناس"

            itemView.confirmationCheckBox.isChecked = if (itemModel.confirmationStatus == true) true else false

            itemView.confirmationCheckBox.setOnCheckedChangeListener { compoundButton, b ->

                //Toast.makeText(context, "${itemModel.confirmationStatus} - ${compoundButton.isChecked}", Toast.LENGTH_LONG).show()

                if (compoundButton.isChecked) {
                    //setupProgress()
                    createConfirmation()
                } else {
                    // setupProgress()
                    deleteConfirmation()
                }


            }
        }


        private fun deleteConfirmation() {

            var service: IUserApi = IWebApi.Factory.create()

            var deleteMaterialBody = ConfirmationViewModel.setDeleteConfirmation(projectId, reportDate)

            var call = service.deleteConfirmation(token, deleteMaterialBody)

          //  Toast.makeText(context, "درحال حذف!", Toast.LENGTH_LONG).show()
            call.enqueue(object : retrofit2.Callback<ConfirmationResponse> {
                override fun onFailure(call: Call<ConfirmationResponse>?, t: Throwable?) {
                    //  progressDialog.dismiss()
                    Toast.makeText(context, "خطا, اتصال اینترنت خود را بررسی کنید.", Toast.LENGTH_LONG).show()

                }

                override fun onResponse(call: Call<ConfirmationResponse>?, response: Response<ConfirmationResponse>?) {
                    //  progressDialog.dismiss()

                    if (response!!.code() == 200) {
                        // Toast.makeText(context, "با موفقیت حذف گردید", Toast.LENGTH_LONG).show()

                    } else if (response.code() == 409) {
                         // Toast.makeText(context, "قبلا حذف گردیده است!", Toast.LENGTH_LONG).show()

                    } else {
                      //  Toast.makeText(context, "خطا، مشکلی هنگام پردازش رخ داده!", Toast.LENGTH_LONG).show()

                    }

                }

            })

        }

        private fun createConfirmation() {

            var service: IUserApi = IWebApi.Factory.create()

            var confirmationBody = ConfirmationViewModel.setCreateConfirmationInformation(projectId, reportDate)

            var call = service.createConfirmation(token, confirmationBody)

            call.enqueue(object : retrofit2.Callback<ConfirmationResponse> {
                override fun onFailure(call: Call<ConfirmationResponse>?, t: Throwable?) {
                    //    progressDialog.dismiss()
                    Toast.makeText(context, "خطا, اتصال اینترنت خود را بررسی کنید.", Toast.LENGTH_LONG).show()

                }

                override fun onResponse(call: Call<ConfirmationResponse>?, response: Response<ConfirmationResponse>?) {

                    //  progressDialog.dismiss()

                    if (response!!.code() == 200) {

                        // Toast.makeText(context, "تایید شد!", Toast.LENGTH_LONG).show()


                    } else if (response.code() == 409) {

                        //Toast.makeText(context, "قبلا تایید شده است!", Toast.LENGTH_LONG).show()


                    } else {
                       // Toast.makeText(context, "خطا، مشکلی هنگام پردازش رخ داده!", Toast.LENGTH_LONG).show()

                    }
                }

            })


        }

        private fun setupProgress() {
            progressDialog = ProgressDialog(context)
            progressDialog.setMessage("لطفا شکیبا باشید")
            progressDialog.setCancelable(false)
            progressDialog.isIndeterminate = true
            progressDialog.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress_dialog))
            progressDialog.show()
        }
    }

}