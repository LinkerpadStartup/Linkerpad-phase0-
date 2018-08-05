package com.linkerpad.linkerpad

import android.content.Context
import android.content.Intent
import android.icu.util.LocaleData
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import kotlinx.android.synthetic.main.add_project_layout.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class AddProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_project_layout)

        startDateCalender.setOnClickListener {
            var intent = Intent(this@AddProjectActivity, ChooseDateActivity::class.java)
            intent.putExtra("startOrEndDate", 1)
            startActivityForResult(intent, 1)
        }

        endDateCalender.setOnClickListener {
            var intent = Intent(this@AddProjectActivity, ChooseDateActivity::class.java)
            intent.putExtra("startOrEndDate", 2)
            startActivityForResult(intent, 1)
        }


        //back click
        addProjectBackIcon.setOnClickListener {
            var intent = Intent(this@AddProjectActivity, MainActivity::class.java)
            startActivity(intent)
            this@AddProjectActivity.finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        if (resultCode == 1)
            projectStartDateEdt.setText(data?.getStringExtra("date"))
        else
            projectEndDateEdt.setText(data?.getStringExtra("date"))


        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

}
