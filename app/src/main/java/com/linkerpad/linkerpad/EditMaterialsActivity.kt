package com.linkerpad.linkerpad

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.edit_materials_layout.*

class EditMaterialsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_materials_layout)
        setSupportActionBar(toolbar)


        //back clicked
        editMaterialsBackIcon.setOnClickListener { this@EditMaterialsActivity.finish() }
    }
}
