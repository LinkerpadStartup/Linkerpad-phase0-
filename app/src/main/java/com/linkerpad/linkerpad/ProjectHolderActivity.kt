package com.linkerpad.linkerpad

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.view.View
import android.view.animation.AnimationUtils
import com.linkerpad.linkerpad.ApiData.output.ProjectInformationResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Fragments.DataRequestFragment
import com.linkerpad.linkerpad.Fragments.ReportsFragment
import com.linkerpad.linkerpad.Fragments.TeamFragment
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.project_holder_app_bar.*
import kotlinx.android.synthetic.main.project_holder_content.*
import kotlinx.android.synthetic.main.project_holder_layout.*
import retrofit2.Call
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class ProjectHolderActivity : AppCompatActivity() {

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.project_holder_layout)
        setSupportActionBar(toolbar)

        var fragmentManager: FragmentManager = supportFragmentManager
        var fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.projectsFrameLayout, ReportsFragment()).commit()


        setupProgress()
        getProjectInformation(intent.getStringExtra("id"))

        headerNameTv.setText(getNameLastName())
        headerEmailTv.setText(getEmail())

/*  phase-1      addReqDataIcon.setOnClickListener { view ->
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
        }*/

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        onNavigationItemSelected()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun getNameLastName(): String {
        var sharedPreferences: SharedPreferences = this@ProjectHolderActivity.getSharedPreferences("userInformation", 0)
        return "${sharedPreferences.getString("firstName", null)} ${sharedPreferences.getString("lastName",null)}"
    }

    private fun getEmail(): String {
        var sharedPreferences: SharedPreferences = this@ProjectHolderActivity.getSharedPreferences("userInformation", 0)
        return "${sharedPreferences.getString("email", null)}"
    }

    private fun getProjectInformation(projectId: String) {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getProjectInforamtion(getToken(), projectId)

        call.enqueue(object : retrofit2.Callback<ProjectInformationResponse> {
            override fun onFailure(call: Call<ProjectInformationResponse>?, t: Throwable?) {
                progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()

            }

            @TargetApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<ProjectInformationResponse>?, response: Response<ProjectInformationResponse>?) {
                progressDialog.dismiss()


                var responseProjectInformation = response!!.body()!!.responseObject
                if (response!!.code() == 200) {
                    projectTitleTv.setText(response!!.body()!!.responseObject.name)
                    if (responseProjectInformation.projectPicture != ""){
                        val b = Base64.decode(responseProjectInformation.projectPicture, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(b, 0, b.size)
                        drawerHeaderll.background = BitmapDrawable(resources,bitmap)
                        drawerImageView.visibility = View.INVISIBLE
                    }
                }
            }

        })
    }

    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@ProjectHolderActivity.getSharedPreferences("userInformation", 0)
        return sharedPreferences.getString("token", null)
    }

    private fun setupProgress() {
        progressDialog = ProgressDialog(this@ProjectHolderActivity)
        progressDialog.setMessage("لطفا شکیبا باشید")
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
        progressDialog.show()
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
                    /*var slideTopAnimaiton = AnimationUtils.loadAnimation(this@ProjectHolderActivity, R.anim.abc_slide_out_bottom)
                    phase - 1 moreNvll . startAnimation (slideTopAnimaiton)
                    moreNvll.visibility = View.INVISIBLE  */

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
                    /*phase-1         var slideTopAnimaiton = AnimationUtils.loadAnimation(this@ProjectHolderActivity, R.anim.abc_slide_out_bottom)
                             moreNvll.startAnimation(slideTopAnimaiton)
                             moreNvll.visibility = View.INVISIBLE*/

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

    fun onNavigationItemSelected() {

        accountInfoMenu.setOnClickListener {
            var intent = Intent(this@ProjectHolderActivity, AccountInfoActivity::class.java)
            startActivity(intent)
        }

        exitMenu.setOnClickListener {
            var intent = Intent(this@ProjectHolderActivity, RegLoginHolderActivity::class.java)
            startActivity(intent)
        }

        commentsMenu.setOnClickListener {
            var intent = Intent(this@ProjectHolderActivity, SendCommentsActivity::class.java)
            startActivity(intent)
        }

        aboutUs.setOnClickListener {
            var intent = Intent(this@ProjectHolderActivity, AboutUSActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()


        }

    }


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
