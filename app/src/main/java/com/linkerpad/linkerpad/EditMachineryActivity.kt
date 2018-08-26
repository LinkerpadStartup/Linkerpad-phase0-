package com.linkerpad.linkerpad

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.linkerpad.linkerpad.ApiData.output.GetMachineryInformationResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.MachineryInformationOutput
import com.linkerpad.linkerpad.Models.MachineryViewModel
import kotlinx.android.synthetic.main.edit_machinery_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditMachineryActivity : AppCompatActivity() {

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_machinery_layout)

        setSupportActionBar(toolbar)

        var equipmentId = intent.getStringExtra("id")
        var projectId = intent.getStringExtra("projectId")

        setupProgress()
        getMachineryInformation(equipmentId, projectId)

        //back clicked
        editMachineryBackIcon.setOnClickListener {
            var intent = Intent(this@EditMachineryActivity, MachineryActivity::class.java)
            intent.putExtra("projectId", projectId)
            startActivity(intent)
            this@EditMachineryActivity.finish()
        }
    }

    private fun getMachineryInformation(projectId: String, equipmentId: String) {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getEquipmentInformation(getToken(), projectId, equipmentId)

        call.enqueue(object : Callback<GetMachineryInformationResponse> {
            override fun onFailure(call: Call<GetMachineryInformationResponse>?, t: Throwable?) {
                progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<GetMachineryInformationResponse>?, response: Response<GetMachineryInformationResponse>?) {
                progressDialog.dismiss()

                var machineryInformationData = MachineryViewModel.getMachineryInformation(MachineryInformationOutput(response!!.body()!!.status, response.body()!!.status, response!!.body()!!.responseObject))

                if (response!!.code() == 200) {
                    TitleMachineryEdt.setText(machineryInformationData.title)
                    countActiveMachineryEdt.setText(machineryInformationData.numberOfActiveEquipment.toString())
                    countDeactiveMachineryEdt.setText(machineryInformationData.numberOfDeactiveEquipment.toString())
                    timeCountMachineryEdt.setText(machineryInformationData.workHours.toString())
                    descriptionMachineryEdt.setText(machineryInformationData.description)
                }


            }
        })
    }


    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@EditMachineryActivity.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
    }

    private fun setupProgress() {
        progressDialog = ProgressDialog(this@EditMachineryActivity)
        progressDialog.setMessage("لطفا شکیبا باشید")
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
        progressDialog.show()
    }

}
