package com.linkerpad.linkerpad

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.text.TextPaint
import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ViewTarget
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

    var showcaseView: ShowcaseView? = null

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

        var textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        textPaint.setColor(Color.WHITE)
        textPaint.setTextSize(40f)
        textPaint.setTypeface(Typeface.createFromAsset(this.assets, "IRANSansWeb(FaNum).ttf"))

        var titleTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        titleTextPaint.setColor(Color.parseColor("#1E88E5"))
        titleTextPaint.setTextSize(50f)
        titleTextPaint.setTypeface(Typeface.createFromAsset(this.assets, "IRANSansWeb(FaNum)_Medium.ttf"))


        var sharedPreferences: SharedPreferences = this.getSharedPreferences("userInformation", 0)
        if (sharedPreferences.getBoolean("guide8", true)) {

            showcaseView = ShowcaseView.Builder(this)
                    .setTarget(ViewTarget(R.id.showCaseForCheckBox, this))
                    .withMaterialShowcase()
                    .setStyle(R.style.CustomShowcaseTheme3)
                    .setContentTextPaint(textPaint)
                    .setContentTitlePaint(titleTextPaint)
                    .setContentTitle("تایید گزارش")
                    .setContentText("برای تایید گزارش روزانه خود، تیک مقابل نام خودتان را بزنید.")
                    .hideOnTouchOutside()
                    .build()

            showcaseView!!.setButtonText("بعدی")


            showcaseView!!.overrideButtonClick {
                showcaseView!!.hide()

                ShowcaseView.Builder(this)
                        .setTarget(ViewTarget(R.id.showConfirmationReportTv, this))
                        .withMaterialShowcase()
                        .setStyle(R.style.CustomShowcaseTheme3)
                        .setContentTextPaint(textPaint)
                        .setContentTitlePaint(titleTextPaint)
                        .setContentTitle("نمایش گزارش")
                        .setContentText("جهت نمایش، دریافت و به اشتراک گذاری گزارش روزانه تجمیع شده با فرمت pdf")
                        .hideOnTouchOutside()
                        .build()
            }


            var sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
            sharedPreferencesEditor.putBoolean("guide8", true)
            sharedPreferencesEditor.apply()
            sharedPreferencesEditor.commit()
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
                    confirmationRecyclerView.adapter = ConfirmationAdapter(this@ConfirmationActivity, confirmationList, projectId, getToken(), reportDate)
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
