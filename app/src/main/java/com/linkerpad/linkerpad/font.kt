package com.linkerpad.linkerpad

import android.app.Application
import com.linkerpad.linkerpad.R

import uk.co.chrisjenx.calligraphy.CalligraphyConfig


/**
 * Created by alihajiloo on 11/1/17.
 */


class font : Application() {

    override fun onCreate() {
        super.onCreate()

        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("IRANSansWeb(FaNum).ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())



    }
}
