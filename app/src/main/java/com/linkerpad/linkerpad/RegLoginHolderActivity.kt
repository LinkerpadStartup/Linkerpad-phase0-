package com.linkerpad.linkerpad

import android.content.Context
import android.inputmethodservice.Keyboard
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import android.view.animation.AnimationUtils
import com.linkerpad.linkerpad.Adapters.RegLoginPagerAdapter
import com.linkerpad.linkerpad.Fragments.LoginFragment
import com.linkerpad.linkerpad.Fragments.RegisterFragment
import kotlinx.android.synthetic.main.reg_login_holder_layout.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import android.app.Activity
import android.graphics.Typeface
import android.support.v4.content.ContextCompat.getSystemService
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import kotlinx.android.synthetic.main.custom_tab.*
import kotlinx.android.synthetic.main.custom_tab.view.*


class RegLoginHolderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reg_login_holder_layout)

        setupViewPager(container)
        tabs.setupWithViewPager(container)

        logoTv.setTypeface(Typeface.createFromAsset(assets,"Vazir-Bold.ttf"))
        introTv.setTypeface(Typeface.createFromAsset(assets,"Vazir-Bold.ttf"))
        loginBtn.setTypeface(Typeface.createFromAsset(assets,"IRANSansWeb(FaNum)_Bold.ttf"))
        registerBtn.setTypeface(Typeface.createFromAsset(assets,"IRANSansWeb(FaNum)_Bold.ttf"))

        /** register btn click **/
        registerBtn.setOnClickListener {

            setupViewPager(container)
            tabs.setupWithViewPager(container)

            //Visibilities
            container.visibility = View.VISIBLE
            appbar.visibility = View.VISIBLE
            val slideTopAnimation = AnimationUtils.loadAnimation(this@RegLoginHolderActivity, R.anim.slide_top)
            appbar.startAnimation(slideTopAnimation)
            container.startAnimation(slideTopAnimation)


            //inVisibilities text
            val fadeOutAnimation = AnimationUtils.loadAnimation(this@RegLoginHolderActivity, android.R.anim.fade_out)

            registerBtn.visibility = View.INVISIBLE
            loginBtn.visibility = View.INVISIBLE
            introTv.startAnimation(fadeOutAnimation)
            introTv.visibility = View.INVISIBLE

            //changing
            //val scaleBigAnimation = AnimationUtils.loadAnimation(this@RegLoginHolderActivity, R.anim.scale_big)
            //logoLinkerpad.startAnimation(scaleBigAnimation)
            // logoLinkerpad.setPadding(30, 30, 30, 30)
            val slideTopAnimation2 = AnimationUtils.loadAnimation(this@RegLoginHolderActivity, R.anim.slide_top_2)
            logoHolderll1.startAnimation(slideTopAnimation2)
          //  logoHolderll2.visibility = View.VISIBLE
          //  logoHolderll1.visibility = View.INVISIBLE
            logoHolderll1.isClickable = true

            container.setCurrentItem(1, true)

        }

        /** login btn click **/
        loginBtn.setOnClickListener {

            setupViewPager(container)
            tabs.setupWithViewPager(container)

            //Visibilities
            container.visibility = View.VISIBLE
            appbar.visibility = View.VISIBLE
            val slideTopAnimation = AnimationUtils.loadAnimation(this@RegLoginHolderActivity, R.anim.slide_top)
            appbar.startAnimation(slideTopAnimation)
            container.startAnimation(slideTopAnimation)


            //inVisibilities text
            val fadeOutAnimation = AnimationUtils.loadAnimation(this@RegLoginHolderActivity, android.R.anim.fade_out)

            registerBtn.visibility = View.INVISIBLE
            loginBtn.visibility = View.INVISIBLE
            introTv.startAnimation(fadeOutAnimation)
            introTv.visibility = View.INVISIBLE

            //changing
            //val scaleBigAnimation = AnimationUtils.loadAnimation(this@RegLoginHolderActivity, R.anim.scale_big)
            //logoLinkerpad.startAnimation(scaleBigAnimation)
            //logoLinkerpad.setPadding(30, 30, 30, 30)

            val slideTopAnimation2 = AnimationUtils.loadAnimation(this@RegLoginHolderActivity, R.anim.slide_top_2)
            logoHolderll1.startAnimation(slideTopAnimation2)
            //  logoHolderll2.visibility = View.VISIBLE
            //  logoHolderll1.visibility = View.INVISIBLE
            logoHolderll1.isClickable = true

            container.setCurrentItem(0, true)
        }

        logoClickDownll.setOnClickListener {

            //go out
            val slideDownAnimation = AnimationUtils.loadAnimation(this@RegLoginHolderActivity, R.anim.slide_down)
            slideDownAnimation.fillAfter = true
            slideDownAnimation.isFillEnabled = true
            appbar.startAnimation(slideDownAnimation)
            container.startAnimation(slideDownAnimation)

            //Coming
            val fadeInAnimation = AnimationUtils.loadAnimation(this@RegLoginHolderActivity, R.anim.fade_in)
            fadeInAnimation.startOffset = 400
            introTv.startAnimation(fadeInAnimation)
            registerBtn.startAnimation(fadeInAnimation)
            loginBtn.startAnimation(fadeInAnimation)
            introTv.visibility = View.VISIBLE
            registerBtn.visibility = View.VISIBLE
            loginBtn.visibility = View.VISIBLE

            val slideDownAnimation2 = AnimationUtils.loadAnimation(this@RegLoginHolderActivity, R.anim.slide_down_2)
            logoHolderll1.startAnimation(slideDownAnimation2)
            //  logoHolderll2.visibility = View.INVISIBLE
            /*fadeInAnimation.startOffset = 700
            logoHolderll1.startAnimation(fadeInAnimation)
            logoHolderll1.visibility = View.VISIBLE*/
            logoHolderll1.isClickable =false

            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }

            container.removeAllViews()
        }

        var view:View = LayoutInflater.from(this@RegLoginHolderActivity).inflate(R.layout.custom_tab,tabs , false)
        view.tabTv.setTypeface(Typeface.createFromAsset(assets , "IRANSansWeb(FaNum)_Light.ttf"))
        tabs.getTabAt(0)!!.setCustomView(view.tabTv)

        tabs.getTabAt(1)!!.setCustomView(view.tabTv)

    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = RegLoginPagerAdapter(supportFragmentManager)
        adapter.addFragment(LoginFragment(), "ورود")
        adapter.addFragment(RegisterFragment(), "ثبت نام")
        viewPager.adapter = adapter
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
