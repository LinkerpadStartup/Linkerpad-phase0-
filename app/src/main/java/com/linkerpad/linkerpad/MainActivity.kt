package com.linkerpad.linkerpad

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.linkerpad.linkerpad.Adapters.RegLoginPagerAdapter
import com.linkerpad.linkerpad.LoginRegisterFragments.LoginFragment
import com.linkerpad.linkerpad.LoginRegisterFragments.RegisterFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)


        setupViewPager(container)
        tabs.setupWithViewPager(container)

    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = RegLoginPagerAdapter(supportFragmentManager)
        adapter.addFragment(LoginFragment(), "ورود")
        adapter.addFragment(RegisterFragment(), "ثبت نام")
        viewPager.adapter = adapter
    }


}
