package com.linkerpad.linkerpad

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.linkerpad.linkerpad.Data.DateType
import kotlinx.android.synthetic.main.add_project_layout.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class AddProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_project_layout)

        startDateCalender.setOnClickListener {
            var intent = Intent(this@AddProjectActivity, ChooseDateActivity::class.java)
               intent.putExtra("startOrEndDate", DateType.StartDate.value)
            startActivityForResult(intent, DateType.StartDate.value)
        }

        endDateCalender.setOnClickListener {
            var intent = Intent(this@AddProjectActivity, ChooseDateActivity::class.java)
             intent.putExtra("startOrEndDate", DateType.EndDate.value)
            startActivityForResult(intent, DateType.EndDate.value)
        }


        //back click
        addProjectBackIcon.setOnClickListener {
            /* var intent = Intent(this@AddProjectActivity, MainActivity::class.java)
             startActivity(intent)*/
            this@AddProjectActivity.finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val projectDateType = DateType.fromInt(resultCode)
        if (projectDateType!!.value == DateType.StartDate.value)
            projectStartDateEdt.setText(data?.getStringExtra("date"))
        else
            projectEndDateEdt.setText(data?.getStringExtra("date"))


        super.onActivityResult(requestCode, projectDateType!!.value, data)
    }


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

}
