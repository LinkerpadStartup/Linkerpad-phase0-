package com.linkerpad.linkerpad

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.gallery_items.*
import kotlinx.android.synthetic.main.gallery_layout.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class GalleryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_layout)
        setSupportActionBar(toolbar)

        galleryInclude.setOnClickListener {
            var layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT)
            galleryInclude.layoutParams = layoutParams
        }

        //back click
        galleryBackIcon.setOnClickListener { this@GalleryActivity.finish() }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onBackPressed() {


        var layoutParams = LinearLayout.LayoutParams(400 , 350)
        layoutParams.setMargins(16,16,16,16)
        galleryInclude.layoutParams = layoutParams
    }
}
