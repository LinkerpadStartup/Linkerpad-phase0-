package com.linkerpad.linkerpad

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.add_done_activities_layout.*

class AddDoneActivitiesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_done_activities_layout)

        setSupportActionBar(toolbar)

        addDoneActivitesBackIcon.setOnClickListener { this@AddDoneActivitiesActivity.finish() }
    }
}
