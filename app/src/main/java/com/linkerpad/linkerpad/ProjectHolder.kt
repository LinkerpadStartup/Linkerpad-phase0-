package com.linkerpad.linkerpad

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.linkerpad.linkerpad.Fragments.DataRequestFragment
import com.linkerpad.linkerpad.Fragments.DocumentsFragment
import com.linkerpad.linkerpad.Fragments.ReportsFragment
import com.linkerpad.linkerpad.R.id.dataRequestNv
import kotlinx.android.synthetic.main.project_holder_layout.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import android.support.design.internal.BottomNavigationItemView
import java.lang.reflect.AccessibleObject.setAccessible
import java.lang.reflect.Array.setBoolean
import android.support.design.internal.BottomNavigationMenuView



class ProjectHolder : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.project_holder_layout)
        setSupportActionBar(toolbar)

        var fragmentManager: FragmentManager = supportFragmentManager
        var fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.projectsFrameLayout, ReportsFragment()).commit()


     //   BottomNavigationViewHelper.removeShiftMode(navigation)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private var nvPosition: Int = 1
    private var moreCount: Int = 0
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        var fragmentManager: FragmentManager = supportFragmentManager
        var fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        val reportsFragment: ReportsFragment = ReportsFragment()
        when (item.itemId) {
            R.id.reportsNv -> {

                searchDocIcon.visibility  = View.INVISIBLE
                searchReqDataIcon.visibility = View.INVISIBLE
                filterReqDataIcon.visibility = View.INVISIBLE
                sortReqDataIcon.visibility = View.INVISIBLE
                addReqDataIcon.visibility = View.INVISIBLE

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

                if (moreCount == 1){
                    var slideTopAnimaiton = AnimationUtils.loadAnimation(this@ProjectHolder, R.anim.abc_slide_out_bottom)
                    moreNvll.startAnimation(slideTopAnimaiton)
                    moreNvll.visibility = View.INVISIBLE

                    moreCount = 0
                }

                return@OnNavigationItemSelectedListener true
            }
            R.id.documentsNv -> {

                searchDocIcon.visibility = View.VISIBLE
                searchReqDataIcon.visibility = View.INVISIBLE
                filterReqDataIcon.visibility = View.INVISIBLE
                sortReqDataIcon.visibility = View.INVISIBLE
                addReqDataIcon.visibility = View.INVISIBLE

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

                if (moreCount == 1){
                    var slideTopAnimaiton = AnimationUtils.loadAnimation(this@ProjectHolder, R.anim.abc_slide_out_bottom)
                    moreNvll.startAnimation(slideTopAnimaiton)
                    moreNvll.visibility = View.INVISIBLE

                    moreCount = 0
                }

                return@OnNavigationItemSelectedListener true
            }
            R.id.dataRequestNv -> {

                searchDocIcon.visibility  = View.INVISIBLE
                searchReqDataIcon.visibility = View.VISIBLE
                filterReqDataIcon.visibility = View.VISIBLE
                sortReqDataIcon.visibility = View.VISIBLE
                addReqDataIcon.visibility = View.VISIBLE

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

                if (moreCount == 1){
                    var slideTopAnimaiton = AnimationUtils.loadAnimation(this@ProjectHolder, R.anim.abc_slide_out_bottom)
                    moreNvll.startAnimation(slideTopAnimaiton)
                    moreNvll.visibility = View.INVISIBLE

                    moreCount = 0
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.moreNv -> {
                if (moreCount == 0) {
                    var slideTopAnimaiton = AnimationUtils.loadAnimation(this@ProjectHolder, R.anim.abc_slide_in_bottom)
                    moreNvll.startAnimation(slideTopAnimaiton)
                    moreNvll.visibility = View.VISIBLE

                    moreCount = 1
                } else {
                    var slideTopAnimaiton = AnimationUtils.loadAnimation(this@ProjectHolder, R.anim.abc_slide_out_bottom)
                    moreNvll.startAnimation(slideTopAnimaiton)
                    moreNvll.visibility = View.INVISIBLE

                    moreCount = 0
                }
                
            }

        }
        false
    }

/*
    internal object BottomNavigationViewHelper {

        @SuppressLint("RestrictedApi")
        fun removeShiftMode(view: BottomNavigationView) {
            val menuView = view.getChildAt(0) as BottomNavigationMenuView
            try {
                val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
                shiftingMode.isAccessible = true
                shiftingMode.setBoolean(menuView, false)
                shiftingMode.isAccessible = false
                for (i in 0 until menuView.childCount) {
                    val item = menuView.getChildAt(i) as BottomNavigationItemView
                    item.setShiftingMode(false)
                    if (item.isSelected)
                        item.setTextColor(ColorStateList(arrayOf<IntArray>(intArrayOf(Color.GRAY,Color.GRAY)), intArrayOf(Color.GRAY)))
                    // set once again checked value, so view will be updated
                    item.setChecked(item.itemData.isChecked)
                }
            } catch (e: NoSuchFieldException) {
            } catch (e: IllegalAccessException) {
            }

        }
    }*/

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
