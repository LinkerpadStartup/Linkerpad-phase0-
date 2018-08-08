package com.linkerpad.linkerpad

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.gallery_item_details_layout.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class GalleryItemDetailsActivity : AppCompatActivity() {


    private var mScaleFactor = 1.0f
    private var mScaleGestureDetector: ScaleGestureDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_item_details_layout)

     //   mScaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

        var animationSlidTopFade = AnimationUtils.loadAnimation(this@GalleryItemDetailsActivity , R.anim.slide_top_fade)
        var animationSlidTopFade2 = AnimationUtils.loadAnimation(this@GalleryItemDetailsActivity , R.anim.slide_top_fade)
        var animationSlidTopFade3 = AnimationUtils.loadAnimation(this@GalleryItemDetailsActivity , R.anim.slide_top_fade)
        animationSlidTopFade2.startOffset = 200
        animationSlidTopFade3.startOffset = 300
        picTitleGallTv.startAnimation(animationSlidTopFade)

        picNameGallTv.startAnimation(animationSlidTopFade2)

        picDateGallTv.startAnimation(animationSlidTopFade3)

    }

  /*  override fun onTouchEvent(event: MotionEvent?): Boolean {

        mScaleGestureDetector!!.onTouchEvent(event);

        return true
    }*/

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            mScaleFactor *= scaleGestureDetector.scaleFactor
            mScaleFactor = Math.max(1.0f, Math.min(mScaleFactor, 10.0f))
            imageView.setScaleX(mScaleFactor)
            imageView.setScaleY(mScaleFactor)
            return true
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
