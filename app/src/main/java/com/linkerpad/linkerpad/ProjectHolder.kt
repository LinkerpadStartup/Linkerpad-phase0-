package com.linkerpad.linkerpad

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.linkerpad.linkerpad.Fragments.DataRequestFragment
import com.linkerpad.linkerpad.Fragments.DocumentsFragment
import com.linkerpad.linkerpad.Fragments.ReportsFragment
import kotlinx.android.synthetic.main.project_holder_layout.*

class ProjectHolder : AppCompatActivity()  {

    private var nvPosition: Int = 1
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        var fragmentManager: FragmentManager = supportFragmentManager
        var fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        val reportsFragment: ReportsFragment = ReportsFragment()
        when (item.itemId) {
            R.id.navigation_home -> {

                if (nvPosition == 1) {
                    /*fragmentTransaction.replace(R.id.projectsFrameLayout, ReportsFragment()).commit()*/
                } else if (nvPosition == 2) {
                    fragmentTransaction.remove(DocumentsFragment())
                    fragmentTransaction.hide(DocumentsFragment())
                    fragmentTransaction.replace(R.id.projectsFrameLayout, ReportsFragment()).commit()
                    nvPosition = 1
                } else if (nvPosition == 3) {
                    fragmentTransaction.remove(DataRequestFragment())
                    fragmentTransaction.hide(DataRequestFragment())
                    fragmentTransaction.replace(R.id.projectsFrameLayout, ReportsFragment()).commit()
                    nvPosition = 1
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {

                if (nvPosition == 1) {
                    fragmentTransaction.remove(ReportsFragment())
                    fragmentTransaction.hide(ReportsFragment())
                    fragmentTransaction.replace(R.id.projectsFrameLayout, DocumentsFragment()).commit()
                    nvPosition = 2
                } else if (nvPosition == 2) {
                  /*  fragmentTransaction.remove(DocumentsFragment())
                    fragmentTransaction.hide(DocumentsFragment())
                    fragmentTransaction.replace(R.id.projectsFrameLayout, DocumentsFragment()).commit()*/
                    nvPosition = 2
                } else if (nvPosition == 3) {
                    fragmentTransaction.remove(DataRequestFragment())
                    fragmentTransaction.hide(DataRequestFragment())
                    fragmentTransaction.replace(R.id.projectsFrameLayout, DocumentsFragment()).commit()
                    nvPosition = 2
                }

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                if (nvPosition == 1) {
                    fragmentTransaction.remove(ReportsFragment())
                    fragmentTransaction.hide(ReportsFragment())
                    fragmentTransaction.replace(R.id.projectsFrameLayout, DataRequestFragment()).commit()
                    nvPosition = 3
                } else if (nvPosition == 2) {
                    fragmentTransaction.remove(DocumentsFragment())
                    fragmentTransaction.hide(DocumentsFragment())
                    fragmentTransaction.replace(R.id.projectsFrameLayout, DataRequestFragment()).commit()
                    nvPosition = 3
                } else if (nvPosition == 3) {
                    /*fragmentTransaction.remove(DataRequestFragment())
                    fragmentTransaction.hide(DataRequestFragment())
                    fragmentTransaction.replace(R.id.projectsFrameLayout, DataRequestFragment()).commit()*/
                    nvPosition = 3
                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.project_holder_layout)

        var fragmentManager: FragmentManager = supportFragmentManager
        var fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.projectsFrameLayout,ReportsFragment()).commit()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
