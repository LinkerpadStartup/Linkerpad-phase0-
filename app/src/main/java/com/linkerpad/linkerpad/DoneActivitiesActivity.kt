package com.linkerpad.linkerpad

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ActionViewTarget
import com.github.amlcurran.showcaseview.targets.ViewTarget
import com.linkerpad.linkerpad.Adapters.DailyActivityAdapter
import com.linkerpad.linkerpad.Adapters.ProjectsListAdapter
import com.linkerpad.linkerpad.ApiData.output.DailyActivityListResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.DailyActivityInformationData
import com.linkerpad.linkerpad.Data.JalaliCalendar
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

        var projectId = getIntent().getStringExtra("projectId")
        var reportDate = getIntent().getStringExtra("reportDate")

        //setupProgress()
        getDailyActivityList(projectId, reportDate)


        addDoneActivitiesFab.setOnClickListener {
            var intent = Intent(this@DoneActivitiesActivity, AddDoneActivitiesActivity::class.java)
            intent.putExtra("reportDate", reportDate)
            intent.putExtra("projectId", projectId)
            startActivity(intent)
            this@DoneActivitiesActivity.finish()
        }

        copyDoneActivitiesImv.setOnClickListener {
            /*      if (getGuide()) {
                      ShowcaseView.Builder(this@DoneActivitiesActivity)
                              .setTarget(ViewTarget(R.id.copyDoneActivitiesImv, this))
                              .withMaterialShowcase()
                              .setContentText("میتوانید جهت سهولت بیشتر، کلیه موارد این لیست را از روز قبل کپی کرده و متناسب با کارهای امروز آنها را ویرایش نمایید")
                              .hideOnTouchOutside()
                              .build()
                      var sharedPreferences: SharedPreferences = this.getSharedPreferences("userInformation", 0)
                      var sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
                      sharedPreferencesEditor.putBoolean("guide", true)
                      sharedPreferencesEditor.apply()
                      sharedPreferencesEditor.commit()
                  }*/
        }


        //refreshing
        doneActivitiesRefresh.setColorSchemeColors(Color.parseColor("#1E88E5"))
        doneActivitiesRefresh.setOnRefreshListener {

            getDailyActivityList(projectId, reportDate)
            doneActivitiesRefresh.isRefreshing = false

        }

        //refreshing
        refreshBtnImv.setOnClickListener {

            getDailyActivityList(projectId, reportDate)

        }


        //back clicked
        doneActivitesBackIcon.setOnClickListener { this@DoneActivitiesActivity.finish() }
    }

    private fun getDailyActivityList(projectId: String, reportDate: String = "2020-02-02") {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getProjectDailyActivityList(getToken(), projectId, reportDate)

        call.enqueue(object : retrofit2.Callback<DailyActivityListResponse> {
            override fun onFailure(call: Call<DailyActivityListResponse>?, t: Throwable?) {
                //progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<DailyActivityListResponse>?, response: Response<DailyActivityListResponse>?) {

                //progressDialog.dismiss()

                var dailyActivityListResponse = response!!.body()

                var dailyActivityList = ArrayList<DailyActivityInformationData>()
                dailyActivityList = dailyActivityListResponse!!.responseObject

                dailyActivityRecyclerView.layoutManager = LinearLayoutManager(this@DoneActivitiesActivity)
                dailyActivityRecyclerView.adapter = DailyActivityAdapter(this@DoneActivitiesActivity, dailyActivityList, projectId)

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

    private fun getGuide(): Boolean {
        var sharedPreferences: SharedPreferences = this@DoneActivitiesActivity.getSharedPreferences("userInformation", 0)
        var first = sharedPreferences.getBoolean("guide", false)
        return first
    }
}
