package com.linkerpad.linkerpad

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        registerBtn.setOnClickListener {

            var intent = Intent(this@MainActivity , RegLoginHolder::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.faidin, R.anim.faidout)

            registerBtn.visibility = View.INVISIBLE
            loginBtn.visibility = View.INVISIBLE
        }
        loginBtn.setOnClickListener {

            var intent = Intent(this@MainActivity , RegLoginHolder::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.faidin, R.anim.faidout)
            registerBtn.visibility = View.INVISIBLE
            loginBtn.visibility = View.INVISIBLE
        }


        }




}
