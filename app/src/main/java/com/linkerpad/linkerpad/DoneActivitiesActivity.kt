package com.linkerpad.linkerpad

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.done_activities_item.*
import kotlinx.android.synthetic.main.done_activities_layout.*

class DoneActivitiesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.done_activities_layout)
        setSupportActionBar(toolbar)

        //item clicked
        doneActivitesItemLL.setOnClickListener {
            var intent = Intent(this@DoneActivitiesActivity, EditDoneActivitiesActivity::class.java)
            startActivity(intent)
        }

        addDoneActivitiesFab.setOnClickListener {
            var intent = Intent(this@DoneActivitiesActivity, AddDoneActivitiesActivity::class.java)
            startActivity(intent)
        }

        //back clicked
        doneActivitesBackIcon.setOnClickListener { this@DoneActivitiesActivity.finish() }
    }
}
