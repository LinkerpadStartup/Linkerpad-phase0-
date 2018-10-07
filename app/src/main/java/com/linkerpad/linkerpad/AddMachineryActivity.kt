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
        var reportDate = getIntent().getStringExtra("reportDate")

        saveMachineryTv.setOnClickListener { view ->
            if (TitleMachineryEdt.text.toString() != "" /*&& countActiveMachineryEdt.text.toString() != "" && countDeactiveMachineryEdt.text.toString() != "" && timeCountMachineryEdt.text.toString() != ""*/) {
               // setupProgress()
                createMachinery(projectId, reportDate)
            } else {
                Snackbar.make(view, "عنوان وارد شود!", Snackbar.LENGTH_LONG).show()
            }
        }


        countActiveMachineryEdt.setText("0")
        machineryEnableUpArrow.setOnClickListener {
            var count = changePersianIntToEnglishInt(countActiveMachineryEdt.text.toString()).toInt() + 1

            countActiveMachineryEdt.setText("${count}")
        }

        machineryEnableDownArrow.setOnClickListener {
            var count = changePersianIntToEnglishInt(countActiveMachineryEdt.text.toString()).toInt() + -1

            countActiveMachineryEdt.setText("${count}")
        }


        timeCountMachineryEdt.setText("0.0")
        machineryTimeUpArrow.setOnClickListener {
            var count = changePersianIntToEnglishInt(timeCountMachineryEdt.text.toString()).toDouble() + 0.5

            var solution = remove3Decimal(count)

            timeCountMachineryEdt.setText("${solution}")
        }

        machineryTimeDownArrow.setOnClickListener {
            var count = changePersianIntToEnglishInt(timeCountMachineryEdt.text.toString()).toDouble() - 0.5

            var solution = remove3Decimal(count)

            timeCountMachineryEdt.setText("${solution}")
        }

        countDeactiveMachineryEdt.setText("0")
        machineryDisableUpArrow.setOnClickListener {
            var count = changePersianIntToEnglishInt(countDeactiveMachineryEdt.text.toString()).toInt() + 1

            countDeactiveMachineryEdt.setText("${count}")
        }

        machineryDisableDownArrow.setOnClickListener {
            var count = changePersianIntToEnglishInt(countDeactiveMachineryEdt.text.toString()).toInt() + -1

            countDeactiveMachineryEdt.setText("${count}")
        }

        //back clicked
        addMachineryBackIcon.setOnClickListener {
            var intent = Intent(this@AddMachineryActivity, MachineryActivity::class.java)
            var projectId = getIntent().getStringExtra("projectId")
            intent.putExtra("projectId", projectId)
            intent.putExtra("reportDate", reportDate)
            startActivity(intent)
            this@AddMachineryActivity.finish()
        }
    }

    private fun changePersianIntToEnglishInt(number: String): String {
        return number.replace("۰", "0")
                .replace("۱", "1")
                .replace("۲", "2")
                .replace("۳", "3")
                .replace("۴", "4")
                .replace("۵", "5")
                .replace("۶", "6")
                .replace("۷", "7")
                .replace("۸", "8")
                .replace("۹", "9")
    }

    private fun remove3Decimal(number: Double): Double {
        val number: Double = number
        val number3digits: Double = Math.round(number * 1000.0) / 1000.0
        val number2digits: Double = Math.round(number3digits * 100.0) / 100.0
        val solution: Double = Math.round(number2digits * 10.0) / 10.0

        return solution
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

    private fun createMachinery(projectId: String, reportDate: String) {

        var service: IUserApi = IWebApi.Factory.create()

        var machineryBody = MachineryViewModel.setCreateMachineryInformation(MachineryViewModel(
                "",
                "",
                "",
                projectId,
                "",
                reportDate,
                TitleMachineryEdt.text.toString(),
                descriptionMachineryEdt.text.toString(),
                countActiveMachineryEdt.text.toString().toInt()
                , countDeactiveMachineryEdt.text.toString().toInt(),
                timeCountMachineryEdt.text.toString().toFloat()
        ))

        var call = service.createEquipment(getToken(), machineryBody)

        call.enqueue(object : retrofit2.Callback<MachineryResponse> {
            override fun onFailure(call: Call<MachineryResponse>?, t: Throwable?) {
               // progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()

            }

            override fun onResponse(call: Call<MachineryResponse>?, response: Response<MachineryResponse>?) {

                if (response!!.code() == 200) {

                    Toast.makeText(this@AddMachineryActivity, "آیتم ثبت گردید", Toast.LENGTH_LONG).show()
                    var intent = Intent(this@AddMachineryActivity, MachineryActivity::class.java)
                    intent.putExtra("projectId", projectId)
                    intent.putExtra("reportDate", reportDate)
                    startActivity(intent)
                    this@AddMachineryActivity.finish()

                } else {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، مشکلی هنگام پردازش رخ داده!", Snackbar.LENGTH_LONG).show()

                }
            }

        })


    }

    override fun onBackPressed() {
        super.onBackPressed()
        var intent = Intent(this@AddMachineryActivity, MachineryActivity::class.java)
        var projectId = getIntent().getStringExtra("projectId")
        var reportDate = getIntent().getStringExtra("reportDate")
        intent.putExtra("projectId", projectId)
        intent.putExtra("reportDate", reportDate)
        startActivity(intent)
        this@AddMachineryActivity.finish()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

}

