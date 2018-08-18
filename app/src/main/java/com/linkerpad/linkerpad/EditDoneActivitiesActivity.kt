package com.linkerpad.linkerpad

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.edit_done_activities_layout.*

class EditDoneActivitiesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_done_activities_layout)
        setSupportActionBar(toolbar)

        editDoneActivitesBackIcon.setOnClickListener { this@EditDoneActivitiesActivity.finish() }
    }
}
