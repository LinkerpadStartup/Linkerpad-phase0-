package com.linkerpad.linkerpad

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class AccountInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_info_layout)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


}
