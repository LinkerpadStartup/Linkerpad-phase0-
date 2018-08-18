package com.linkerpad.linkerpad

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.machinery_item.*
import kotlinx.android.synthetic.main.machinery_layout.*

class MachineryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.machinery_layout)
        setSupportActionBar(toolbar)


        machineryActivityFab.setOnClickListener {
            var intent = Intent(this@MachineryActivity, AddMachineryActivity::class.java)
            startActivity(intent)
        }

        machineryItemLL.setOnClickListener {
            var intent = Intent(this@MachineryActivity, EditMachineryActivity::class.java)
            startActivity(intent)
        }

        //back clicked
        machineryBackIcon.setOnClickListener {
            this@MachineryActivity.finish()
        }
    }
}
