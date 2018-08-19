package com.linkerpad.linkerpad

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.materials_item.*
import kotlinx.android.synthetic.main.materials_layout.*

class MaterialsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.materials_layout)

        setSupportActionBar(toolbar)

        //Edit Materials
        materialsItemLL.setOnClickListener {
            var intent = Intent(this@MaterialsActivity, EditMaterialsActivity::class.java)
            startActivity(intent)
        }

        //Add Materials
        materialsActivityFab.setOnClickListener {
            var intent = Intent(this@MaterialsActivity, AddMaterialsActivity::class.java)
            startActivity(intent)
        }


        //back clicked
        materialsBackIcon.setOnClickListener { this@MaterialsActivity.finish() }
    }
}
