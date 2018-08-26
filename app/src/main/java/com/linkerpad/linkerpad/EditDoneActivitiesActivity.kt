package com.linkerpad.linkerpad

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.Toast
import com.linkerpad.linkerpad.ApiData.output.DeleteDailyActivityResponse
import com.linkerpad.linkerpad.ApiData.output.EditDailyActivityResponse
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

        saveEditDailyActivityTv.setOnClickListener {
            setupProgress()
            editDailyActivity(projectId, dailyActivityId)
        }

        editDoneActivitesBackIcon.setOnClickListener {
            var intent = Intent(this@EditDoneActivitiesActivity, DoneActivitiesActivity::class.java)
            intent.putExtra("projectId", projectId)
            startActivity(intent)
            this@EditDoneActivitiesActivity.finish()
        }
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

    private fun deleteDailyActivity(projectId: String, dailyActivityId: String) {

        var service: IUserApi = IWebApi.Factory.create()

        var deleteDailyActivityBody = DailyActivityViewModel.setDeleteDailyActivity(dailyActivityId, projectId)

        var call = service.deleteDailyActivity(getToken(), deleteDailyActivityBody)

        call.enqueue(object : retrofit2.Callback<DeleteDailyActivityResponse> {
            override fun onFailure(call: Call<DeleteDailyActivityResponse>?, t: Throwable?) {
                progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<DeleteDailyActivityResponse>?, response: Response<DeleteDailyActivityResponse>?) {
                progressDialog.dismiss()

                if (response!!.code() == 200) {
                    Toast.makeText(this@EditDoneActivitiesActivity, "فعالیت با موفقیت حذف گردید", Toast.LENGTH_LONG).show()

                    var intent = Intent(this@EditDoneActivitiesActivity, DoneActivitiesActivity::class.java)
                    intent.putExtra("projectId", projectId)
                    startActivity(intent)
                    this@EditDoneActivitiesActivity.finish()
                } else if (response.code() == 404) {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, فعالیت یافت نشد!", Snackbar.LENGTH_LONG).show()

                } else if (response.code() == 405) {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "با عرض پوزش ، شما امکان حذف فعالیت را ندارید!", Snackbar.LENGTH_LONG).show()

                }

            }

        })

    }

    private fun editDailyActivity(projectId: String, dailyActivityId: String) {

        var service: IUserApi = IWebApi.Factory.create()

        var editDailyActivityBody = DailyActivityViewModel.setEditDailyActivity(dailyActivityId, projectId,
                TitleDoneActivitiesEdt.text.toString(), sizeUnitDoneActivitiesEdt.text.toString(), desciptionDoneActivitiesEdt.text.toString(),
                countMemberDoneActivitiesEdt.text.toString().toInt(), timeCountDoneActivitiesEdt.text.toString().toFloat(), sizeCountDoneActivitiesEdt.text.toString().toFloat()
        )

        var call = service.editDailyActivity(getToken(), editDailyActivityBody)

        call.enqueue(object : retrofit2.Callback<EditDailyActivityResponse> {
            override fun onFailure(call: Call<EditDailyActivityResponse>?, t: Throwable?) {
                progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<EditDailyActivityResponse>?, response: Response<EditDailyActivityResponse>?) {
                progressDialog.dismiss()

                if (response!!.code() == 200) {
                    Toast.makeText(this@EditDoneActivitiesActivity, "فعالیت با موفقیت ویرایش گردید", Toast.LENGTH_LONG).show()

                    var intent = Intent(this@EditDoneActivitiesActivity, DoneActivitiesActivity::class.java)
                    intent.putExtra("projectId", projectId)
                    startActivity(intent)
                    this@EditDoneActivitiesActivity.finish()
                } else if (response.code() == 404) {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, فعالیت یافت نشد!", Snackbar.LENGTH_LONG).show()

                } else if (response.code() == 405) {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "با عرض پوزش ، شما امکان ویرایش فعالیت را ندارید!", Snackbar.LENGTH_LONG).show()

                }

            }

        })

    }

    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@EditDoneActivitiesActivity.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
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
