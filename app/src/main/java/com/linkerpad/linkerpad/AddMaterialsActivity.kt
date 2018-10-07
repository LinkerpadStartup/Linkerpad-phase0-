package com.linkerpad.linkerpad

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.Toast
import com.linkerpad.linkerpad.ApiData.output.MaterialResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Models.MaterialViewModel
import kotlinx.android.synthetic.main.add_materials_layout.*
import retrofit2.Call
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class AddMaterialsActivity : AppCompatActivity() {

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_materials_layout)
        setSupportActionBar(toolbar)

        var reportDate = getIntent().getStringExtra("reportDate")
        var projectId = intent.getStringExtra("projectId")

        saveMaterialTv.setOnClickListener {view->
         //   setupProgress()

            if (TitleMaterialEdt.text.toString() != "" /*&& countMaterialsEdt.text.toString() != "" && unitMaterialEdt.text.toString() != ""*/) {
                // setupProgress()
                createMaterial(projectId, reportDate)
            } else {
                Snackbar.make(view, "عنوان وارد شود!", Snackbar.LENGTH_LONG).show()
            }

        }


        countMaterialsEdt.setText("0.0")
        countMaterialsUpArrow.setOnClickListener {

            var count = changePersianIntToEnglishInt(countMaterialsEdt.text.toString()).toDouble() + 0.1

            var solution = remove3Decimal(count)

            countMaterialsEdt.setText("${solution}")


        }

        countMaterialsDownArrow.setOnClickListener {

            var count = changePersianIntToEnglishInt(countMaterialsEdt.text.toString()).toDouble() - 0.1

            var solution = remove3Decimal(count)

            countMaterialsEdt.setText("${solution}")


        }

        //back clicked
        addMaterialsBackIcon.setOnClickListener {
            var intent = Intent(this@AddMaterialsActivity, MaterialsActivity::class.java)
            var projectId = getIntent().getStringExtra("projectId")
            intent.putExtra("projectId", projectId)
            intent.putExtra("reportDate", reportDate)
            startActivity(intent)
            this@AddMaterialsActivity.finish()
        }
    }

    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@AddMaterialsActivity.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
    }

    private fun setupProgress() {
        progressDialog = ProgressDialog(this@AddMaterialsActivity)
        progressDialog.setMessage("لطفا شکیبا باشید")
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
        progressDialog.show()
    }

    private fun createMaterial(projectId: String, reportDate: String) {

        var service: IUserApi = IWebApi.Factory.create()

        var materialBody = MaterialViewModel.setCreateMaterialInformation(MaterialViewModel(
                "",
                "",
                "",
                projectId,
                "",
                reportDate,
                TitleMaterialEdt.text.toString(),
                unitMaterialEdt.text.toString(),
                descriptionMaterialsEdt.text.toString()
                , countMaterialsEdt.text.toString().toFloat()
        ))

        var call = service.createMaterial(getToken(), materialBody)

        call.enqueue(object : retrofit2.Callback<MaterialResponse> {
            override fun onFailure(call: Call<MaterialResponse>?, t: Throwable?) {
              //  progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()

            }

            override fun onResponse(call: Call<MaterialResponse>?, response: Response<MaterialResponse>?) {

                if (response!!.code() == 200) {

                  //  Toast.makeText(this@AddMaterialsActivity, "مواد و مصالح با موفقیت ثبت شد!", Toast.LENGTH_LONG).show()
                    Toast.makeText(this@AddMaterialsActivity, "آیتم ثبت گردید", Toast.LENGTH_LONG).show()
                    var intent = Intent(this@AddMaterialsActivity, MaterialsActivity::class.java)
                    intent.putExtra("projectId", projectId)
                    intent.putExtra("reportDate", reportDate)
                    startActivity(intent)
                    this@AddMaterialsActivity.finish()

                } else {
                  //  Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، مشکلی هنگام پردازش رخ داده!", Snackbar.LENGTH_LONG).show()

                }
            }

        })


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
    override fun onBackPressed() {
        super.onBackPressed()
        var intent = Intent(this@AddMaterialsActivity, MaterialsActivity::class.java)
        var projectId = getIntent().getStringExtra("projectId")
        var reportDate = getIntent().getStringExtra("reportDate")
        intent.putExtra("projectId", projectId)
        intent.putExtra("reportDate", reportDate)
        startActivity(intent)
        this@AddMaterialsActivity.finish()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
