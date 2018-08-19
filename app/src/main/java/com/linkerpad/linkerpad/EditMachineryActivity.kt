package com.linkerpad.linkerpad

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.linkerpad.linkerpad.Data.Visibility
import kotlinx.android.synthetic.main.edit_machinery_layout.*

class EditMachineryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_machinery_layout)

        setSupportActionBar(toolbar)


        var visibility = Visibility.Visible

        MachinerySwitch.setOnClickListener {
            if (visibility == Visibility.Visible) {
                MachinerySwitch.text = "فعال"
                visibility = Visibility.InVisible
            } else {
                MachinerySwitch.text = "غیرفعال"
                visibility = Visibility.Visible
            }

        }

        //back clicked
        editMachineryBackIcon.setOnClickListener { this@EditMachineryActivity.finish() }
    }
}