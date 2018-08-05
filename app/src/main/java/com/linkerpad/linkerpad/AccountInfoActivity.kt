package com.linkerpad.linkerpad

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.account_info_layout.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class AccountInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_info_layout)
        setSupportActionBar(toolbar)




        //back click
        accountInfoBackIcon.setOnClickListener {
            var intent = Intent(this@AccountInfoActivity, MainActivity::class.java)
            startActivity(intent)
            this@AccountInfoActivity.finish()
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


}
