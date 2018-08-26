package com.linkerpad.linkerpad

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.Toast
import com.linkerpad.linkerpad.ApiData.output.MachineryResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.Visibility
import com.linkerpad.linkerpad.Models.MachineryViewModel
import kotlinx.android.synthetic.main.add_machinery_layout.*
import retrofit2.Call
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class AddMachineryActivity : AppCompatActivity() {

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_machinery_layout)

        setSupportActionBar(toolbar)

        var projectId = intent.getStringExtra("projectId")

        saveMachineryTv.setOnClickListener {
            setupProgress()
            createMachinery(projectId)

        }


        //back clicked
        addMachineryBackIcon.setOnClickListener { this@AddMachineryActivity.finish() }
    }

    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@AddMachineryActivity.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
    }

    private fun setupProgress() {
        progressDialog = ProgressDialog(this@AddMachineryActivity)
        progressDialog.setMessage("لطفا شکیبا باشید")
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
        progressDialog.show()
    }

    private fun createMachinery(projectId: String) {

        var service: IUserApi = IWebApi.Factory.create()

        var machineryBody = MachineryViewModel.setCreateMachineryInformation(MachineryViewModel("", "", "", projectId,
                "", "2020-02-02", TitleMachineryEdt.text.toString(), descriptionMachineryEdt.text.toString(), countActiveMachineryEdt.text.toString().toInt()
                , countDeactiveMachineryEdt.text.toString().toInt(), timeCountMachineryEdt.text.toString().toFloat()))

        var call = service.createEquipment(getToken(), machineryBody)

        call.enqueue(object : retrofit2.Callback<MachineryResponse> {
            override fun onFailure(call: Call<MachineryResponse>?, t: Throwable?) {
                progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()

            }

            override fun onResponse(call: Call<MachineryResponse>?, response: Response<MachineryResponse>?) {

                if (response!!.code() == 200) {

                    Toast.makeText(this@AddMachineryActivity, "ماشین آلات با موفقیت ثبت شد!", Toast.LENGTH_LONG).show()
                    var intent = Intent(this@AddMachineryActivity, MachineryActivity::class.java)
                    intent.putExtra("projectId", projectId)
                    startActivity(intent)
                    this@AddMachineryActivity.finish()

                } else {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، مشکلی هنگام پردازش رخ داده!", Snackbar.LENGTH_LONG).show()

                }
            }

        })


    }


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

}

