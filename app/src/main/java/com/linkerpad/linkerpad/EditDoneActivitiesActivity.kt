package com.linkerpad.linkerpad

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.linkerpad.linkerpad.ApiData.output.GetDailyActivityInformationResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.DailyActivityInformationOutput
import com.linkerpad.linkerpad.Models.DailyActivityViewModel
import kotlinx.android.synthetic.main.edit_done_activities_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class EditDoneActivitiesActivity : AppCompatActivity() {

    var dailyActivityId = ""
    var projectId = ""

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_done_activities_layout)
        setSupportActionBar(toolbar)

        dailyActivityId = intent.getStringExtra("id")
        projectId = intent.getStringExtra("projectId")

        setupProgress()
        getDailyActivityInformation(projectId, dailyActivityId)

        removeDailyActivityTv.setOnClickListener {
            setupProgress()
            deleteDailyActivity(projectId, dailyActivityId)
        }

        editDoneActivitesBackIcon.setOnClickListener { this@EditDoneActivitiesActivity.finish() }
    }

    private fun getDailyActivityInformation(projectId: String, dailyActivityId: String) {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getDailyActivityInformation(getToken(), projectId, dailyActivityId)

        call.enqueue(object : Callback<GetDailyActivityInformationResponse> {
            override fun onFailure(call: Call<GetDailyActivityInformationResponse>?, t: Throwable?) {
                progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<GetDailyActivityInformationResponse>?, response: Response<GetDailyActivityInformationResponse>?) {
                progressDialog.dismiss()

                var dailyActivityInformation = DailyActivityViewModel.getDailyAcivityInformation(DailyActivityInformationOutput(response!!.body()!!.status, response.body()!!.status, response!!.body()!!.responseObject))

                if (response!!.code() == 200) {
                    TitleDoneActivitiesEdt.setText(dailyActivityInformation.title)
                    countMemberDoneActivitiesEdt.setText(dailyActivityInformation.numberOfCrew.toString())
                    timeCountDoneActivitiesEdt.setText(dailyActivityInformation.workHours.toString())
                    sizeCountDoneActivitiesEdt.setText(dailyActivityInformation.workload.toString())
                    sizeUnitDoneActivitiesEdt.setText(dailyActivityInformation.workloadUnit)
                    desciptionDoneActivitiesEdt.setText(dailyActivityInformation.description)
                }


            }
        })
    }

    private fun deleteDailyActivity(projectId: String, dailyActivityId: String){

    }
    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@EditDoneActivitiesActivity.getSharedPreferences("userInformation", 0)
        return sharedPreferences.getString("token", null)
    }

    private fun setupProgress() {
        progressDialog = ProgressDialog(this@EditDoneActivitiesActivity)
        progressDialog.setMessage("لطفا شکیبا باشید")
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
        progressDialog.show()
    }


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
