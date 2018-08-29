package com.linkerpad.linkerpad

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.Toast
import com.linkerpad.linkerpad.ApiData.output.CreateDailyActivityResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Models.DailyActivityViewModel
import kotlinx.android.synthetic.main.add_done_activities_layout.*
import retrofit2.Call
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class AddDoneActivitiesActivity : AppCompatActivity() {


    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_done_activities_layout)

        setSupportActionBar(toolbar)

        var reportDate = getIntent().getStringExtra("reportDate")

        saveDailyActivityTv.setOnClickListener { view ->
            if (sizeUnitDoneActivitiesEdt.text.toString() != "" && TitleDoneActivitiesEdt.text.toString() != "" && countMemberDoneActivitiesEdt.text.toString() != ""
                    && timeCountDoneActivitiesEdt.text.toString() != "" && sizeCountDoneActivitiesEdt.text.toString() != "") {
                createDailyActivity(intent.getStringExtra("projectId"), reportDate)
                setupProgress()
            } else {
                Snackbar.make(view, "فقط توضیحات میتواند خالی باشد!", Snackbar.LENGTH_LONG).show()
            }

        }

        //back clicked
        addDoneActivitesBackIcon.setOnClickListener {
            var intent = Intent(this@AddDoneActivitiesActivity, DoneActivitiesActivity::class.java)
            var projectId = getIntent().getStringExtra("projectId")
            intent.putExtra("projectId", projectId)
            intent.putExtra("reportDate", reportDate)
            startActivity(intent)
            this@AddDoneActivitiesActivity.finish()
        }

    }


    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@AddDoneActivitiesActivity.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
    }

    private fun setupProgress() {
        progressDialog = ProgressDialog(this@AddDoneActivitiesActivity)
        progressDialog.setMessage("لطفا شکیبا باشید")
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
        progressDialog.show()
    }

    private fun createDailyActivity(projectId: String, reportDate: String) {

        var service: IUserApi = IWebApi.Factory.create()

        var dailyActivityBody = DailyActivityViewModel.setCreateDailyActivityInformation(DailyActivityViewModel(
                "",
                "",
                "",
                projectId,
                "",
                reportDate,
                TitleDoneActivitiesEdt.text.toString(),
                sizeUnitDoneActivitiesEdt.text.toString(),
                desciptionDoneActivitiesEdt.text.toString()
                , countMemberDoneActivitiesEdt.text.toString().toInt(),
                timeCountDoneActivitiesEdt.text.toString().toFloat(),
                sizeCountDoneActivitiesEdt.text.toString().toFloat()
        ))

        var call = service.createDailyActivity(getToken(), dailyActivityBody)

        call.enqueue(object : retrofit2.Callback<CreateDailyActivityResponse> {
            override fun onFailure(call: Call<CreateDailyActivityResponse>?, t: Throwable?) {
                progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()

            }

            override fun onResponse(call: Call<CreateDailyActivityResponse>?, response: Response<CreateDailyActivityResponse>?) {

                if (response!!.code() == 200) {

                    Toast.makeText(this@AddDoneActivitiesActivity, "فعالیت با موفقیت ثبت شد!", Toast.LENGTH_LONG).show()
                    var intent = Intent(this@AddDoneActivitiesActivity, DoneActivitiesActivity::class.java)
                    intent.putExtra("projectId", projectId)
                    intent.putExtra("reportDate", reportDate)
                    startActivity(intent)
                    this@AddDoneActivitiesActivity.finish()

                } else {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، مشکلی هنگام پردازش رخ داده!", Snackbar.LENGTH_LONG).show()

                }
            }

        })


    }


    override fun onBackPressed() {
        super.onBackPressed()
        var intent = Intent(this@AddDoneActivitiesActivity, DoneActivitiesActivity::class.java)
        var projectId = getIntent().getStringExtra("projectId")
        var reportDate = getIntent().getStringExtra("reportDate")
        intent.putExtra("projectId", projectId)
        intent.putExtra("reportDate", reportDate)
        startActivity(intent)
        this@AddDoneActivitiesActivity.finish()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

}
