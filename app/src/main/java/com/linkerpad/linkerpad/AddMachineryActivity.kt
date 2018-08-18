package com.linkerpad.linkerpad

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.linkerpad.linkerpad.Data.Visibility
import kotlinx.android.synthetic.main.add_machinery_layout.*

class AddMachineryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_machinery_layout)

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
        addMachineryBackIcon.setOnClickListener { this@AddMachineryActivity.finish() }
    }
}
