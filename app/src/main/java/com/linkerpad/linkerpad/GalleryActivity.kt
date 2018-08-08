package com.linkerpad.linkerpad

import android.annotation.TargetApi
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair

import kotlinx.android.synthetic.main.gallery_layout.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

import android.view.View


class GalleryActivity : AppCompatActivity() {



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_layout)
        setSupportActionBar(toolbar)

        galleryInclude.setOnClickListener {
/*            var layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT)
            galleryInclude.layoutParams = layoutParams*/
            var intent = Intent(this@GalleryActivity , GalleryItemDetailsActivity::class.java)

            var pair :android.util.Pair<View, String> = Pair.create(galleryInclude,"image")

            var activityOption = ActivityOptions.makeSceneTransitionAnimation(this@GalleryActivity,pair)


            startActivity(intent,activityOption.toBundle())
        }




        //back click
        galleryBackIcon.setOnClickListener { this@GalleryActivity.finish() }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


/*    override fun onBackPressed() {


        var layoutParams = LinearLayout.LayoutParams(400 , 350)
        layoutParams.setMargins(16,16,16,16)
        galleryInclude.layoutParams = layoutParams
    }*/
}
