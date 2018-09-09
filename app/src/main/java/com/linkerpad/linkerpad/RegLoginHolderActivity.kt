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
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat.getSystemService
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.linkerpad.linkerpad.ApiData.input.LoginBody
import com.linkerpad.linkerpad.ApiData.output.LoginResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.LoginOutputData
import com.linkerpad.linkerpad.Models.UserInformationViewModel
import kotlinx.android.synthetic.main.custom_tab.*
import kotlinx.android.synthetic.main.custom_tab.view.*
import retrofit2.Call
import retrofit2.Response


class RegLoginHolderActivity : AppCompatActivity() {

    var logoPos = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reg_login_holder_layout)

        setupViewPager(container)
        tabs.setupWithViewPager(container)

        getWindow().setBackgroundDrawable(resources.getDrawable(R.drawable.back_6_2))

        logoTv.setTypeface(Typeface.createFromAsset(assets, "Vazir-Bold.ttf"))
        introTv.setTypeface(Typeface.createFromAsset(assets, "Vazir-Bold.ttf"))
        loginBtn.setTypeface(Typeface.createFromAsset(assets, "IRANSansWeb(FaNum)_Bold.ttf"))
        registerBtn.setTypeface(Typeface.createFromAsset(assets, "IRANSansWeb(FaNum)_Bold.ttf"))



        if (!getEmail().equals("null") && !getPassword().equals("null")) {

            this@RegLoginHolderActivity.finish()
            var intent = Intent(this@RegLoginHolderActivity, MainActivity::class.java)
            startActivity(intent)
        }


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
            logoPos = 1

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
            logoPos = 1

            container.setCurrentItem(0, true)
        }

        logoClickDownll.setOnClickListener {

            if (logoPos == 1) {
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
                logoHolderll1.isClickable = false

                logoPos = 0

                //for hide keyboard
                val view = this.currentFocus
                if (view != null) {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }


                container.removeAllViews()
            }
        }

        logoHolderll1.setOnClickListener {

            if (logoPos == 1) {
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
                logoHolderll1.isClickable = false

                logoPos = 0

                //for hide keyboard
                val view = this.currentFocus
                if (view != null) {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }


                container.removeAllViews()
            }
        }

        var view: View = LayoutInflater.from(this@RegLoginHolderActivity).inflate(R.layout.custom_tab, tabs, false)
        view.tabTv.setTypeface(Typeface.createFromAsset(assets, "IRANSansWeb(FaNum)_Light.ttf"))
        tabs.getTabAt(0)!!.setCustomView(view.tabTv)

        tabs.getTabAt(1)!!.setCustomView(view.tabTv)

    }

    private fun getEmail(): String {
        var sharedPreferences: SharedPreferences = this@RegLoginHolderActivity.getSharedPreferences("userInformation", 0)
        return "${sharedPreferences.getString("email", null)}"
    }

    private fun getPassword(): String {
        var sharedPreferences: SharedPreferences = this@RegLoginHolderActivity.getSharedPreferences("userInformation", 0)
        var pass = "${sharedPreferences.getString("password", null)}"
        return pass
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = RegLoginPagerAdapter(supportFragmentManager)
        adapter.addFragment(LoginFragment(), "ورود")
        adapter.addFragment(RegisterFragment(), "ثبت نام")
        viewPager.adapter = adapter
    }

    override fun onBackPressed() {

        if (logoPos == 1) {
            logoPos = 0

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
            logoHolderll1.isClickable = false
            if (logoPos == 1)
                logoPos = 0

            //for hide keyboard
            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }


            container.removeAllViews()

        } else {
            super.onBackPressed()
        }
    }

    private fun login(email: String, password: String) {
        var loginBody: LoginBody = UserInformationViewModel.setUsernamePassword(
                email, password)

        val service: IUserApi = IWebApi.Factory.create()
        val call = service.login(loginBody = loginBody)

        try {
            call.enqueue(object : retrofit2.Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                    // progressDialog.dismiss()
                    //  Toast.makeText(context, "error: ${t!!.message}", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this@RegLoginHolderActivity, "خطا هنگام ورود اتصال اینترنت خود را بررسی کنید!", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
                    //  progressDialog.dismiss()

                    if (response!!.code() == 200) {

                        var loginResponse: LoginResponse? = response.body()


                        val loginOutputData = UserInformationViewModel.getUserInformation(LoginOutputData(
                                loginResponse!!.status,
                                loginResponse.message,
                                loginResponse.responseObject.token,
                                loginResponse.responseObject.expirationDate,
                                loginResponse.responseObject.userInformationViewModel
                        ))

                        var sharedPreferences: SharedPreferences = this@RegLoginHolderActivity.getSharedPreferences("userInformation", 0)
                        var sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
                        sharedPreferencesEditor.putString("token", loginOutputData.token)
                        sharedPreferencesEditor.putString("username", loginOutputData.emailAddress)
                        sharedPreferencesEditor.putString("password", password)
                        sharedPreferencesEditor.putString("firstName", loginOutputData.firstName)
                        sharedPreferencesEditor.putString("lastName", loginOutputData.lastName)
                        sharedPreferencesEditor.putString("email", loginOutputData.emailAddress)
                        sharedPreferencesEditor.apply()
                        sharedPreferencesEditor.commit()

                        this@RegLoginHolderActivity.finish()
                        var intent = Intent(this@RegLoginHolderActivity, MainActivity::class.java)
                        startActivity(intent)


                    } else if (response.code() == 409) {
                        //Snackbar.make(this@LoginFragment.view!!, "خطا، ایمیل شما تکراری است.", Snackbar.LENGTH_LONG).show()
                        //  Toast.makeText(context, "خطا، ایمیل شما تکراری است.", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 400) {
                        //Snackbar.make(this@LoginFragment.view!!, "خطا، مقادیر ارسالی صحیح نمی باشد.", Snackbar.LENGTH_LONG).show()
                        //  Toast.makeText(context, "خطا، مقادیر ارسالی صحیح نمی باشد.", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 401) {
                        // Snackbar.make(this@LoginFragment.view!!, "خطا، ایمیل یا رمز عبور صحیح نمی باشد.", Snackbar.LENGTH_LONG).show()

                    }


                }
            })
        } catch (e: Exception) {
            //Toast.makeText(this@RegLoginHolderActivity, "${e.printStackTrace().toString()}", Toast.LENGTH_LONG).show()
        }

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
