package com.linkerpad.linkerpad

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.linkerpad.linkerpad.Adapters.DailyActivityAdapter
import com.linkerpad.linkerpad.Adapters.ProjectsListAdapter
import com.linkerpad.linkerpad.ApiData.output.DailyActivityListResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.DailyActivityInformationData
import com.linkerpad.linkerpad.Models.DailyActivityViewModel
import kotlinx.android.synthetic.main.done_activities_item.*
import kotlinx.android.synthetic.main.done_activities_layout.*
import retrofit2.Call
import retrofit2.Response

class DoneActivitiesActivity : AppCompatActivity() {

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.done_activities_layout)
        setSupportActionBar(toolbar)


        setupProgress()
        getDailyActivityList(intent.getStringExtra("projectId"))


        addDoneActivitiesFab.setOnClickListener {
            var intent = Intent(this@DoneActivitiesActivity, AddDoneActivitiesActivity::class.java)
            var projectId = getIntent().getStringExtra("projectId")
            intent.putExtra("projectId", projectId)
            startActivity(intent)
        }

        //back clicked
        doneActivitesBackIcon.setOnClickListener { this@DoneActivitiesActivity.finish() }
    }

    private fun getDailyActivityList(projectId: String, reportDate: String = "2020-02-02") {
        var service: IUserApi = IWebApi.Factory.create()
        var dailyActivityBody =DailyActivityViewModel.setDailyActivityList(projectId, reportDate)
        var call = service.getProjectDailyActivityList(getToken(),projectId,reportDate)

        call.enqueue(object : retrofit2.Callback<DailyActivityListResponse> {
            override fun onFailure(call: Call<DailyActivityListResponse>?, t: Throwable?) {
                progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<DailyActivityListResponse>?, response: Response<DailyActivityListResponse>?) {

                progressDialog.dismiss()

                var dailyActivityListResponse = response!!.body()

                var dailyActivityList = ArrayList<DailyActivityInformationData>()
                dailyActivityList = dailyActivityListResponse!!.responseObject

                dailyActivityRecyclerView.layoutManager = LinearLayoutManager(this@DoneActivitiesActivity)
                dailyActivityRecyclerView.adapter = DailyActivityAdapter(this@DoneActivitiesActivity, dailyActivityList)

            }

        })

    }

    private fun setupProgress() {
        progressDialog = ProgressDialog(this@DoneActivitiesActivity)
        progressDialog.setMessage("لطفا شکیبا باشید")
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
        progressDialog.show()
    }

    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@DoneActivitiesActivity.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
    }
}
