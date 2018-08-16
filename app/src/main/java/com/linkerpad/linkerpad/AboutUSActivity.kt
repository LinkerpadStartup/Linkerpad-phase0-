package com.linkerpad.linkerpad

import android.content.Context
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.about_us_layout.*
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class AboutUSActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_us_layout)
        setSupportActionBar(toolbar)

        logoTv.setTypeface(Typeface.createFromAsset(assets,"Vazir-Bold.ttf"))


        //back click
        aboutUsBackIcon.setOnClickListener { this@AboutUSActivity.finish()}
    }


}
