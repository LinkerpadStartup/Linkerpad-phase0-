package com.linkerpad.linkerpad

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import android.view.animation.AnimationUtils
import com.linkerpad.linkerpad.Adapters.RegLoginPagerAdapter
import com.linkerpad.linkerpad.LoginRegisterFragments.LoginFragment
import com.linkerpad.linkerpad.LoginRegisterFragments.RegisterFragment
import kotlinx.android.synthetic.main.reg_login_holder_layout.*

class RegLoginHolder : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reg_login_holder_layout)

        setupViewPager(container)
         tabs.setupWithViewPager(container)

        registerBtn.setOnClickListener {

            container.visibility = View.VISIBLE
            appbar.visibility = View.VISIBLE
            val slideTopAnimation = AnimationUtils.loadAnimation(this@RegLoginHolder, R.anim.faidin)
            appbar.startAnimation(slideTopAnimation)
            container.startAnimation(slideTopAnimation)

            registerBtn.visibility = View.INVISIBLE
            loginBtn.visibility = View.INVISIBLE
        }
        loginBtn.setOnClickListener {

            container.visibility = View.VISIBLE
            appbar.visibility = View.VISIBLE
            val slideTopAnimation = AnimationUtils.loadAnimation(this@RegLoginHolder, R.anim.faidin)
            appbar.startAnimation(slideTopAnimation)
            container.startAnimation(slideTopAnimation)

            registerBtn.visibility = View.INVISIBLE
            loginBtn.visibility = View.INVISIBLE
        }


    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = RegLoginPagerAdapter(supportFragmentManager)
        adapter.addFragment(LoginFragment(), "ورود")
        adapter.addFragment(RegisterFragment(), "ثبت نام")
        viewPager.adapter = adapter
    }
}
