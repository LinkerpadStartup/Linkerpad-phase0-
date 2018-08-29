package com.linkerpad.linkerpad

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import com.linkerpad.linkerpad.Adapters.MachineryAdapter
import com.linkerpad.linkerpad.ApiData.output.MachineryListResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.MachineryInformationData
import com.linkerpad.linkerpad.Models.MachineryViewModel
import kotlinx.android.synthetic.main.machinery_item.*
import kotlinx.android.synthetic.main.machinery_layout.*
import retrofit2.Call
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class MachineryActivity : AppCompatActivity() {

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.machinery_layout)
        setSupportActionBar(toolbar)

        var projectId = intent.getStringExtra("projectId")
        var reportDate = getIntent().getStringExtra("reportDate")

        setupProgress()
        getMachineryList(projectId,reportDate)


        machineryActivityFab.setOnClickListener {
            var intent = Intent(this@MachineryActivity, AddMachineryActivity::class.java)
            intent.putExtra("projectId", projectId)
            intent.putExtra("reportDate" , reportDate)
            startActivity(intent)
            this@MachineryActivity.finish()
        }

        //back clicked
        machineryBackIcon.setOnClickListener {
            this@MachineryActivity.finish()
        }
    }


    private fun getMachineryList(projectId: String, reportDate: String = "2020-02-02") {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getProjectEquipmentList(getToken(), projectId, reportDate)

        call.enqueue(object : retrofit2.Callback<MachineryListResponse> {
            override fun onFailure(call: Call<MachineryListResponse>?, t: Throwable?) {
                progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<MachineryListResponse>?, response: Response<MachineryListResponse>?) {

                progressDialog.dismiss()

                var machineryListResponse = response!!.body()

                var machineryList = ArrayList<MachineryInformationData>()
                machineryList = machineryListResponse!!.responseObject

                machineryRecyclerView.layoutManager = LinearLayoutManager(this@MachineryActivity)
                machineryRecyclerView.adapter = MachineryAdapter(this@MachineryActivity, machineryList, projectId)

            }

        })

    }

    private fun setupProgress() {
        progressDialog = ProgressDialog(this@MachineryActivity)
        progressDialog.setMessage("لطفا شکیبا باشید")
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
        progressDialog.show()
    }

    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@MachineryActivity.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
