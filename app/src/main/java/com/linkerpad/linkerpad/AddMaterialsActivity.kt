package com.linkerpad.linkerpad

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.add_materials_layout.*

class AddMaterialsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_materials_layout)
        setSupportActionBar(toolbar)


        //back clicked
        addMaterialsBackIcon.setOnClickListener { this@AddMaterialsActivity.finish() }
    }
}
