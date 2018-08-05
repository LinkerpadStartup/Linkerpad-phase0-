package com.linkerpad.linkerpad

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.widget.Toast
import com.linkerpad.linkerpad.Adapters.RegLoginPagerAdapter
import com.linkerpad.linkerpad.Fragments.ProjectsFragment
import com.linkerpad.linkerpad.Fragments.RegisterFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class MainActivity : AppCompatActivity()/*, NavigationView.OnNavigationItemSelectedListener*/ {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setupViewPager(mainPager)
        tabs.setupWithViewPager(mainPager)
        mainPager.setCurrentItem(1, true)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        addProjectFab.setOnClickListener {
            var intent = Intent(this@MainActivity, AddProjectActivity::class.java)
            startActivity(intent)
        }

        onNavigationItemSelected()
        /*  nav_view.setNavigationItemSelectedListener(this)*/

    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = RegLoginPagerAdapter(supportFragmentManager)
        adapter.addFragment(ProjectsFragment(), "داشبورد")
        adapter.addFragment(ProjectsFragment(), "پروژه ها")
        viewPager.adapter = adapter

    }

    fun onNavigationItemSelected() {

        accountInfoMenu.setOnClickListener {
            var intent = Intent(this@MainActivity, AccountInfoActivity::class.java)
            startActivity(intent)
        }

        exitMenu.setOnClickListener {
            var intent = Intent(this@MainActivity, RegLoginHolderActivity::class.java)
            startActivity(intent)
        }

        commentsMenu.setOnClickListener {
            var intent = Intent(this@MainActivity, SendCommentsActivity::class.java)
            startActivity(intent)
        }
    }


    /*override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.accountInfoMenu -> {

                var intent = Intent(this@MainActivity , AccountInfoActivity::class.java)
                startActivity(intent)


            }
            R.id.exitMenu ->{
                var intent = Intent(this@MainActivity , RegLoginHolderActivity::class.java)
                startActivity(intent)
            }


        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
*/
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
