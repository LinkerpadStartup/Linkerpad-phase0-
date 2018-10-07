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
import kotlin.math.IEEErem
import kotlin.math.absoluteValue
import kotlin.math.ceil


class AddDoneActivitiesActivity : AppCompatActivity() {


    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_done_activities_layout)

        setSupportActionBar(toolbar)

        var reportDate = getIntent().getStringExtra("reportDate")

        saveDailyActivityTv.setOnClickListener { view ->
            if (/*sizeUnitDoneActivitiesEdt.text.toString() != "" && */TitleDoneActivitiesEdt.text.toString() != ""/* && countMemberDoneActivitiesEdt.text.toString() != ""
                    && timeCountDoneActivitiesEdt.text.toString() != "" && sizeCountDoneActivitiesEdt.text.toString() != ""*/) {
                createDailyActivity(intent.getStringExtra("projectId"), reportDate)
                // setupProgress()
            } else {
                Snackbar.make(view, "عنوان وارد شود!", Snackbar.LENGTH_LONG).show()
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

        countMemberDoneActivitiesEdt.setText("0")
        //up member count arrow
        countMemberDoneActivitiesUpArrow.setOnClickListener {

            var count = changePersianIntToEnglishInt(countMemberDoneActivitiesEdt.text.toString()).toInt() + 1

            countMemberDoneActivitiesEdt.setText("${count}")


        }

        //down member count arrow
        countMemberDoneActivitiesDownArrow.setOnClickListener {
            var count = changePersianIntToEnglishInt(countMemberDoneActivitiesEdt.text.toString()).toInt() - 1

            countMemberDoneActivitiesEdt.setText("${count}")

        }

        timeCountDoneActivitiesEdt.setText("0.0")
        //up time count arrow
        timeCountDoneActivitiesUpArrow.setOnClickListener {
            var count = changePersianIntToEnglishInt(timeCountDoneActivitiesEdt.text.toString()).toDouble() +0.5

            var solution = remove3Decimal(count)

            timeCountDoneActivitiesEdt.setText("${solution}")
        }

        //down time count arrow
        timeCountDoneActivitiesDownArrow.setOnClickListener {
            var count = changePersianIntToEnglishInt(timeCountDoneActivitiesEdt.text.toString()).toDouble() -0.5

            var solution = remove3Decimal(count)

            timeCountDoneActivitiesEdt.setText("${solution}")
        }



        sizeCountDoneActivitiesEdt.setText("0.0")
        sizeCountDoneActivitiesUpArrow.setOnClickListener {
            var count = changePersianIntToEnglishInt(sizeCountDoneActivitiesEdt.text.toString()).toDouble() +0.1

            var solution = remove3Decimal(count)

            sizeCountDoneActivitiesEdt.setText("${solution}")
        }

        sizeCountDoneActivitiesDownArrow.setOnClickListener {

            var count = changePersianIntToEnglishInt(sizeCountDoneActivitiesEdt.text.toString()).toDouble() -0.1

            var solution = remove3Decimal(count)

            sizeCountDoneActivitiesEdt.setText("${solution}")
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
                //  progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<CreateDailyActivityResponse>?, response: Response<CreateDailyActivityResponse>?) {

                if (response!!.code() == 200) {

                    Toast.makeText(this@AddDoneActivitiesActivity, "آیتم ثبت گردید", Toast.LENGTH_LONG).show()
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
