package com.linkerpad.linkerpad

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.show_report_confimation_layout.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class ShowReportConfimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_report_confimation_layout)
        setSupportActionBar(toolbar)


        var projectId = intent.getStringExtra("projectId")
        var reportDate = intent.getStringExtra("reportDate")

        // load pdf
        Toast.makeText(this@ShowReportConfimationActivity, "درحال بارگذاری گزارش...", Toast.LENGTH_LONG).show()
        showConfirmationReportWebView.loadUrl("http://89.42.211.248/v1/report/reportresult?projectId=$projectId&reportdate=${reportDate.replace("/", "-")}")


        // download pdf
        downloadReportConfirmationImv.setOnClickListener {
            var webpage = Uri.parse("http://89.42.211.248/v1/report/$projectId/${reportDate.replace("/", "-")}")
            var intent = Intent(Intent.ACTION_VIEW, webpage);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_FROM_BACKGROUND)
            startActivity(intent)
        }

        shareReportConfirmationImv.setOnClickListener {
            var intent = Intent(android.content.Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(android.content.Intent.EXTRA_TEXT, "http://89.42.211.248/v1/report/reportresult?projectId=$projectId&reportdate=${reportDate.replace("/", "-")}")
            startActivity(Intent.createChooser(intent, "اشتراک گذاری از طریق..."))
        }

        //back click
        showReportConfirmationBackIcon.setOnClickListener {
            var intent = Intent(this@ShowReportConfimationActivity, ConfirmationActivity::class.java)
            intent.putExtra("projectId", projectId)
            intent.putExtra("reportDate", reportDate)
            this@ShowReportConfimationActivity.finish()
            startActivity(intent)
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()

        var intent = Intent(this@ShowReportConfimationActivity, ConfirmationActivity::class.java)
        intent.putExtra("projectId", getIntent().getStringExtra("projectId"))
        intent.putExtra("reportDate", getIntent().getStringExtra("reportDate"))
        this@ShowReportConfimationActivity.finish()
        startActivity(intent)

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}

