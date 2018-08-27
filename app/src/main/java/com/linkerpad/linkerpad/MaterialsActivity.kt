package com.linkerpad.linkerpad

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import com.linkerpad.linkerpad.Adapters.MaterialAdapter
import com.linkerpad.linkerpad.ApiData.output.MaterialListResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.MaterialInformationData
import kotlinx.android.synthetic.main.materials_item.*
import kotlinx.android.synthetic.main.materials_layout.*
import retrofit2.Call
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class MaterialsActivity : AppCompatActivity() {

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.materials_layout)

        setSupportActionBar(toolbar)

        var projectId = intent.getStringExtra("projectId")

        setupProgress()
        getMaterialList(projectId)

        //Add Materials
        materialsActivityFab.setOnClickListener {
            var intent = Intent(this@MaterialsActivity, AddMaterialsActivity::class.java)
            intent.putExtra("projectId", projectId)
            startActivity(intent)
            this@MaterialsActivity.finish()
        }


        //back clicked
        materialsBackIcon.setOnClickListener { this@MaterialsActivity.finish() }
    }

    private fun getMaterialList(projectId: String, reportDate: String = "2020-02-02") {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getProjectMaterialList(getToken(), projectId, reportDate)

        call.enqueue(object : retrofit2.Callback<MaterialListResponse> {
            override fun onFailure(call: Call<MaterialListResponse>?, t: Throwable?) {
                progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<MaterialListResponse>?, response: Response<MaterialListResponse>?) {

                progressDialog.dismiss()

                var materialListResponse = response!!.body()

                var materialList = ArrayList<MaterialInformationData>()
                materialList = materialListResponse!!.responseObject

                materialRecyclerView.layoutManager = LinearLayoutManager(this@MaterialsActivity)
                materialRecyclerView.adapter = MaterialAdapter(this@MaterialsActivity, materialList, projectId)

            }

        })

    }

    private fun setupProgress() {
        progressDialog = ProgressDialog(this@MaterialsActivity)
        progressDialog.setMessage("لطفا شکیبا باشید")
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
        progressDialog.show()
    }

    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@MaterialsActivity.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
