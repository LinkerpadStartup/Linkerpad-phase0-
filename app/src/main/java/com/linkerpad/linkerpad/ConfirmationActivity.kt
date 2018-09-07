package com.linkerpad.linkerpad

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import com.linkerpad.linkerpad.Adapters.ConfirmationAdapter
import com.linkerpad.linkerpad.ApiData.output.ConfirmationListResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.ConfirmationInformationData
import kotlinx.android.synthetic.main.confirmation_layout.*
import retrofit2.Call
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class ConfirmationActivity : AppCompatActivity() {

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirmation_layout)
        setSupportActionBar(toolbar)


        var projectId = intent.getStringExtra("projectId")
        var reportDate = getIntent().getStringExtra("reportDate")

       // setupProgress()
        getConfirmationList(projectId, reportDate)

        confirmationBackIcon.setOnClickListener {
            this@ConfirmationActivity.finish()
        }

        showConfirmationReportTv.setOnClickListener {
            var intent = Intent(this@ConfirmationActivity, ShowReportConfimationActivity::class.java)
            intent.putExtra("projectId", projectId)
            intent.putExtra("reportDate", reportDate)
            this@ConfirmationActivity.finish()
            startActivity(intent)

        }


    }

    private fun getConfirmationList(projectId: String, reportDate: String = "2020-02-02") {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getProjectConfirmationList(getToken(), projectId, reportDate)

        call.enqueue(object : retrofit2.Callback<ConfirmationListResponse> {
            override fun onFailure(call: Call<ConfirmationListResponse>?, t: Throwable?) {
                progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ConfirmationListResponse>?, response: Response<ConfirmationListResponse>?) {

            //    progressDialog.dismiss()

                try {
                    var confirmationListResponse = response!!.body()

                    var confirmationList = ArrayList<ConfirmationInformationData>()
                    confirmationList = confirmationListResponse!!.responseObject

                    confirmationRecyclerView.layoutManager = LinearLayoutManager(this@ConfirmationActivity)
                    confirmationRecyclerView.adapter = ConfirmationAdapter(this@ConfirmationActivity, confirmationList, projectId, getToken(),reportDate)
                } catch (e: Exception) {

                }
            }

        })

    }


    private fun setupProgress() {
        progressDialog = ProgressDialog(this@ConfirmationActivity)
        progressDialog.setMessage("لطفا شکیبا باشید")
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
        progressDialog.show()
    }

    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@ConfirmationActivity.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
