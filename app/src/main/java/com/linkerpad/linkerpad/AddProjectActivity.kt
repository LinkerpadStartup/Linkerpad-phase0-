package com.linkerpad.linkerpad

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.add_project_layout.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class AddProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_project_layout)

         



        //back click
        addProjectBackIcon.setOnClickListener {
            var intent = Intent(this@AddProjectActivity, MainActivity::class.java)
            startActivity(intent)
            this@AddProjectActivity.finish()
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

}
