package com.linkerpad.linkerpad

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import com.linkerpad.linkerpad.Fragments.DataRequestFragment
import com.linkerpad.linkerpad.Fragments.DocumentsFragment
import com.linkerpad.linkerpad.Fragments.ReportsFragment
import com.linkerpad.linkerpad.Fragments.TeamFragment
import kotlinx.android.synthetic.main.project_holder_layout.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class ProjectHolderActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.project_holder_layout)
        setSupportActionBar(toolbar)

        var fragmentManager: FragmentManager = supportFragmentManager
        var fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.projectsFrameLayout, ReportsFragment()).commit()


        addReqDataIcon.setOnClickListener { view ->
            var intent = Intent(this@ProjectHolderActivity, AddDataRequestActivity::class.java)
            startActivity(intent)
        }

        teamImv.setOnClickListener {
            var intent = Intent(this@ProjectHolderActivity, TeamActivity::class.java)
            startActivity(intent)
        }

        galleryImv.setOnClickListener {
            var intent = Intent(this@ProjectHolderActivity, GalleryActivity::class.java)
            startActivity(intent)
        }

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

               /*phase-1 searchDocIcon.visibility = View.INVISIBLE
                searchReqDataIcon.visibility = View.INVISIBLE
                filterReqDataIcon.visibility = View.INVISIBLE
                sortReqDataIcon.visibility = View.INVISIBLE
                addReqDataIcon.visibility = View.INVISIBLE*/

                if (nvPosition == 1) {
                    /*fragmentTransaction.replace(R.id.projectsFrameLayout, ReportsFragment()).commit()*/
                } else if (nvPosition == 2) {
                    fragmentTransaction.remove(TeamFragment())
                    fragmentTransaction.hide(TeamFragment())
                    fragmentTransaction.replace(R.id.projectsFrameLayout, ReportsFragment()).commit()
                    nvPosition = 1
                } else if (nvPosition == 3) {
                    fragmentTransaction.remove(DataRequestFragment())
                    fragmentTransaction.hide(DataRequestFragment())
                    fragmentTransaction.replace(R.id.projectsFrameLayout, ReportsFragment()).commit()
                    nvPosition = 1
                }

                if (moreCount == 1) {
                    var slideTopAnimaiton = AnimationUtils.loadAnimation(this@ProjectHolderActivity, R.anim.abc_slide_out_bottom)
                    moreNvll.startAnimation(slideTopAnimaiton)
                    moreNvll.visibility = View.INVISIBLE

                    moreCount = 0
                }

                return@OnNavigationItemSelectedListener true
            }
            R.id.documentsNv -> {

               /*phase-1 searchDocIcon.visibility = View.VISIBLE
                searchReqDataIcon.visibility = View.INVISIBLE
                filterReqDataIcon.visibility = View.INVISIBLE
                sortReqDataIcon.visibility = View.INVISIBLE
                addReqDataIcon.visibility = View.INVISIBLE*/

                if (nvPosition == 1) {
                    fragmentTransaction.remove(ReportsFragment())
                    fragmentTransaction.hide(ReportsFragment())
                    fragmentTransaction.replace(R.id.projectsFrameLayout, TeamFragment()).commit()
                    nvPosition = 2
                } else if (nvPosition == 2) {
                    /*  fragmentTransaction.remove(DocumentsFragment())
                      fragmentTransaction.hide(DocumentsFragment())
                      fragmentTransaction.replace(R.id.projectsFrameLayout, DocumentsFragment()).commit()*/
                    nvPosition = 2
                } else if (nvPosition == 3) {
                    fragmentTransaction.remove(DataRequestFragment())
                    fragmentTransaction.hide(DataRequestFragment())
                    fragmentTransaction.replace(R.id.projectsFrameLayout, TeamFragment()).commit()
                    nvPosition = 2
                }

                if (moreCount == 1) {
                    var slideTopAnimaiton = AnimationUtils.loadAnimation(this@ProjectHolderActivity, R.anim.abc_slide_out_bottom)
                    moreNvll.startAnimation(slideTopAnimaiton)
                    moreNvll.visibility = View.INVISIBLE

                    moreCount = 0
                }

                return@OnNavigationItemSelectedListener true
            }
           /*phase-1 R.id.dataRequestNv -> {

                searchDocIcon.visibility = View.INVISIBLE
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
                    *//*fragmentTransaction.remove(DataRequestFragment())
                    fragmentTransaction.hide(DataRequestFragment())
                    fragmentTransaction.replace(R.id.projectsFrameLayout, DataRequestFragment()).commit()*//*
                    nvPosition = 3
                }

                if (moreCount == 1) {
                    var slideTopAnimaiton = AnimationUtils.loadAnimation(this@ProjectHolderActivity, R.anim.abc_slide_out_bottom)
                    moreNvll.startAnimation(slideTopAnimaiton)
                    moreNvll.visibility = View.INVISIBLE

                    moreCount = 0
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.moreNv -> {
                if (moreCount == 0) {
                    var slideTopAnimaiton = AnimationUtils.loadAnimation(this@ProjectHolderActivity, R.anim.abc_slide_in_bottom)
                    moreNvll.startAnimation(slideTopAnimaiton)
                    moreNvll.visibility = View.VISIBLE

                    moreCount = 1
                } else {
                    var slideTopAnimaiton = AnimationUtils.loadAnimation(this@ProjectHolderActivity, R.anim.abc_slide_out_bottom)
                    moreNvll.startAnimation(slideTopAnimaiton)
                    moreNvll.visibility = View.INVISIBLE

                    moreCount = 0
                }

            }*/

        }
        false
    }


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
