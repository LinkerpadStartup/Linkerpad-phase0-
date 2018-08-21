package com.linkerpad.linkerpad

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.project_bottom_sheet.*

/**
 * Created by alihajiloo on 8/21/18.
 */


class EditProjectBottomSheetActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.project_bottom_sheet)

        val slideTopAnimation = AnimationUtils.loadAnimation(this@EditProjectBottomSheetActivity, R.anim.slide_top)
        projectBottomSheet.startAnimation(slideTopAnimation)
        projectBottomSheet.visibility = View.VISIBLE

        spaceEditProjectBottomSheet.setOnClickListener {
            val slideDownAnimation = AnimationUtils.loadAnimation(this@EditProjectBottomSheetActivity, R.anim.slide_down)
            projectBottomSheet.startAnimation(slideTopAnimation)
            projectBottomSheet.visibility = View.INVISIBLE
            this@EditProjectBottomSheetActivity.finish()
        }
    }
}
