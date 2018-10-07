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
import android.widget.Button
import android.widget.Toast
import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ViewTarget
import com.linkerpad.linkerpad.Adapters.ConfirmationAdapter
import com.linkerpad.linkerpad.ApiData.output.ConfirmationListResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.ConfirmationInformationData
import com.linkerpad.linkerpad.Data.JalaliCalendar
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
        var reportDate = getIntent().getStringExtra("reportDate").replace("/", "-")

        var gregorianStart: JalaliCalendar.YearMonthDate = JalaliCalendar.YearMonthDate(reportDate.toString().split("-")[0].toInt(), reportDate.toString().split("-")[1].toInt(), reportDate.toString().split("-")[2].toInt()-1)
        var jalaliStart: JalaliCalendar.YearMonthDate = JalaliCalendar.gregorianToJalali(gregorianStart)
        var date = jalaliStart.toString().replace("-", "/")

        confirmationDateTv.setText(date)

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

        var textPaint = TextPaint(Paint.LINEAR_TEXT_FLAG)
        textPaint.setColor(Color.WHITE)
        textPaint.setTextSize(40f)
        textPaint.setTypeface(Typeface.createFromAsset(this.assets, "IRANSansWeb(FaNum).ttf"))

        var titleTextPaint = TextPaint(Paint.LINEAR_TEXT_FLAG)
        titleTextPaint.setColor(Color.parseColor("#1E88E5"))
        titleTextPaint.setTextSize(50f)
        titleTextPaint.setTypeface(Typeface.createFromAsset(this.assets, "IRANSansWeb(FaNum)_Medium.ttf"))

        var showCaseButton = Button(this)
        showCaseButton.background = resources.getDrawable(R.drawable.round_btn_primary)
        showCaseButton.setTextColor(Color.WHITE)
        showCaseButton.typeface = Typeface.createFromAsset(resources.assets, "IRANSansWeb(FaNum)_Light.ttf")

        var sharedPreferences: SharedPreferences = this.getSharedPreferences("userInformation", 0)
        if (sharedPreferences.getBoolean("guide8", true)) {

            showcaseView = ShowcaseView.Builder(this)
                    .setTarget(ViewTarget(R.id.showCaseForCheckBox, this))
                    .withMaterialShowcase()
                    .setStyle(R.style.CustomShowcaseTheme3)
                    .setContentTextPaint(textPaint)
                    .setContentTitlePaint(titleTextPaint)
                    .setContentTitle("تایید گزارش")
                    .setContentText("برای تایید نهایی گزارش روزانه، تیک خود را بزنید.")
                    .replaceEndButton(showCaseButton)
                    .build()

            showcaseView!!.setButtonText("بعدی")


            showcaseView!!.overrideButtonClick {
                showcaseView!!.hide()
                showcaseView!!.removeAllViews()

                ShowcaseView.Builder(this, true)
                        .setTarget(ViewTarget(R.id.showConfirmationReportTv, this))
                        .withNewStyleShowcase()
                        .setStyle(R.style.CustomShowcaseTheme3)
                        .setContentTextPaint(textPaint)
                        .setContentTitlePaint(titleTextPaint)
                        .setContentTitle("نمایش گزارش")
                        .setContentText("جهت نمایش، دانلود و یا به اشتراک گذاری گزارش روزانه، این آیکون را لمس نمایید.")
                        .replaceEndButton(showCaseButton)
                        .build().setButtonText("باشه")
            }


            var sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
            sharedPreferencesEditor.putBoolean("guide8", true)
            sharedPreferencesEditor.apply()
            sharedPreferencesEditor.commit()
        }


        //refresh
        refreshBtnImv.setOnClickListener {
            getConfirmationListUpdate(projectId, reportDate)
        }

        confirmationSwapRefresh.setColorSchemeColors(Color.parseColor("#1E88E5"))
        confirmationSwapRefresh.setOnRefreshListener {

            getConfirmationListUpdate(projectId, reportDate)
            confirmationSwapRefresh.isRefreshing = false

        }

        //end refresh


    }

    private fun getConfirmationList(projectId: String, reportDate: String = "2020-02-02") {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getProjectConfirmationList(getToken(), projectId, reportDate)

        call.enqueue(object : retrofit2.Callback<ConfirmationListResponse> {
            override fun onFailure(call: Call<ConfirmationListResponse>?, t: Throwable?) {
          //      progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ConfirmationListResponse>?, response: Response<ConfirmationListResponse>?) {

                //    progressDialog.dismiss()
                if (response!!.code() == 200) {
                    try {
                        var confirmationListResponse = response!!.body()

                        var confirmationList = ArrayList<ConfirmationInformationData>()
                        confirmationList = confirmationListResponse!!.responseObject

                        confirmationRecyclerView.layoutManager = LinearLayoutManager(this@ConfirmationActivity)
                        confirmationRecyclerView.adapter = ConfirmationAdapter(this@ConfirmationActivity, confirmationList, projectId, getToken(), reportDate)
                    } catch (e: Exception) {

                    }
                }
            }

        })

    }

    private fun getConfirmationListUpdate(projectId: String, reportDate: String = "2020-02-02") {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getProjectConfirmationList(getToken(), projectId, reportDate)

        call.enqueue(object : retrofit2.Callback<ConfirmationListResponse> {
            override fun onFailure(call: Call<ConfirmationListResponse>?, t: Throwable?) {
                //      progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ConfirmationListResponse>?, response: Response<ConfirmationListResponse>?) {

                //    progressDialog.dismiss()
                if (response!!.code() == 200) {
                    try {

                        Toast.makeText(this@ConfirmationActivity, "بروزرسانی انجام شد", Toast.LENGTH_LONG).show()

                        var confirmationListResponse = response!!.body()

                        var confirmationList = ArrayList<ConfirmationInformationData>()
                        confirmationList = confirmationListResponse!!.responseObject

                        confirmationRecyclerView.layoutManager = LinearLayoutManager(this@ConfirmationActivity)
                        confirmationRecyclerView.adapter = ConfirmationAdapter(this@ConfirmationActivity, confirmationList, projectId, getToken(), reportDate)
                    } catch (e: Exception) {

                    }
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
