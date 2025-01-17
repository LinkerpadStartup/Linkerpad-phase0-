package com.linkerpad.linkerpad

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.util.Base64
import android.view.View
import android.widget.Toast
import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ViewTarget
import com.linkerpad.linkerpad.Adapters.RegLoginPagerAdapter
import com.linkerpad.linkerpad.ApiData.input.LoginBody
import com.linkerpad.linkerpad.ApiData.output.GetUserInformationResponse
import com.linkerpad.linkerpad.ApiData.output.LoginResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.LoginOutputData
import com.linkerpad.linkerpad.Data.UserInformationOutputData
import com.linkerpad.linkerpad.Fragments.ProjectsFragment
import com.linkerpad.linkerpad.Models.UserInformationViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.choose_support_layout.view.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class MainActivity : AppCompatActivity()/*, NavigationView.OnNavigationItemSelectedListener*/ {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        headerNameTv.setText(getNameLastName())
        headerEmailTv.setText(getEmail())

        login(getEmail(), getPassword())

        /*     ActivityCompat.requestPermissions(this@MainActivity, arrayOf(
                     android.Manifest.permission.CALL_PHONE,
                     android.Manifest.permission.CAMERA,
                     android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
             ), 1)*/

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        onNavigationItemSelected()
        /*  nav_view.setNavigationItemSelectedListener(this)*/

    }

    //get login token
    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@MainActivity.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
    }

    //get user Information API
    private fun getUserInformation() {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getUserInformation(getToken())

        call.enqueue(object : Callback<GetUserInformationResponse> {
            override fun onFailure(call: Call<GetUserInformationResponse>?, t: Throwable?) {

                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<GetUserInformationResponse>?, response: Response<GetUserInformationResponse>?) {

                if (response!!.code() == 200) {
                    var userInformation: GetUserInformationResponse? = response!!.body()

                    var userInformationOutput = UserInformationViewModel.getUserInformation(UserInformationOutputData(userInformation!!.status,
                            userInformation.message, userInformation.responseObject))


                    if (userInformationOutput.profilePicture != "" && userInformationOutput.profilePicture != null) {

                        val b = Base64.decode(userInformationOutput.profilePicture, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(b, 0, b.size)
                        //  var profileBitmap:Bitmap = BitmapFactory.decodeByteArray(Base64.getDecoder().decode(itemModel.projectPicture), 0, Base64.getDecoder().decode(itemModel.projectPicture).size)
                        drawerImageView.setImageDrawable(BitmapDrawable(resources, bitmap))
                    }
                }
            }

        })
    }

    private fun getNameLastName(): String {
        var sharedPreferences: SharedPreferences = this@MainActivity.getSharedPreferences("userInformation", 0)
        return "${sharedPreferences.getString("firstName", null)} ${sharedPreferences.getString("lastName", null)}"
    }

    private fun getEmail(): String {
        var sharedPreferences: SharedPreferences = this@MainActivity.getSharedPreferences("userInformation", 0)
        return "${sharedPreferences.getString("email", null)}"
    }

    private fun getPassword(): String {
        var sharedPreferences: SharedPreferences = this@MainActivity.getSharedPreferences("userInformation", 0)
        return "${sharedPreferences.getString("password", null)}"
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = RegLoginPagerAdapter(supportFragmentManager)
        /**phase-1**/  /* adapter.addFragment(ProjectsFragment(), "داشبورد")*/
        adapter.addFragment(ProjectsFragment(), "لیست پروژه ها")
        viewPager.adapter = adapter

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
                    // Toast.makeText(this@MainActivity, "خطا هنگام ورود اتصال اینترنت خود را بررسی کنید!", Toast.LENGTH_LONG).show()
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

                        var sharedPreferences: SharedPreferences = this@MainActivity.getSharedPreferences("userInformation", 0)
                        var sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
                        sharedPreferencesEditor.putString("token", loginOutputData.token)
                        sharedPreferencesEditor.putString("username", loginOutputData.emailAddress)
                        sharedPreferencesEditor.putString("password", password)
                        sharedPreferencesEditor.putString("firstName", loginOutputData.firstName)
                        sharedPreferencesEditor.putString("lastName", loginOutputData.lastName)
                        sharedPreferencesEditor.putString("email", loginOutputData.emailAddress)
                        sharedPreferencesEditor.apply()
                        sharedPreferencesEditor.commit()

                        /*  this@MainActivity.finish()
                          var intent = Intent(this@MainActivity, MainActivity::class.java)
                          startActivity(intent)*/

                        setupViewPager(mainPager)
                        tabs.setupWithViewPager(mainPager)
                        mainPager.setCurrentItem(1, true)

                        //get user information for get profilePicture
                        getUserInformation()

                    } else if (response.code() == 409) {
                        //Snackbar.make(this@LoginFragment.view!!, "خطا، ایمیل شما تکراری است.", Snackbar.LENGTH_LONG).show()
                        //  Toast.makeText(context, "خطا، ایمیل شما تکراری است.", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 400) {
                        //Snackbar.make(this@LoginFragment.view!!, "خطا، مقادیر ارسالی صحیح نمی باشد.", Snackbar.LENGTH_LONG).show()
                        //  Toast.makeText(context, "خطا، مقادیر ارسالی صحیح نمی باشد.", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 401) {

                        this@MainActivity.finish()
                        var intent = Intent(this@MainActivity, RegLoginHolderActivity::class.java)
                        startActivity(intent)

                        // Snackbar.make(this@LoginFragment.view!!, "خطا، ایمیل یا رمز عبور صحیح نمی باشد.", Snackbar.LENGTH_LONG).show()

                    }


                }
            })
        } catch (e: Exception) {
            //Toast.makeText(this@RegLoginHolderActivity, "${e.printStackTrace().toString()}", Toast.LENGTH_LONG).show()
        }

    }

    fun onNavigationItemSelected() {

        accountInfoMenu.setOnClickListener {
            var intent = Intent(this@MainActivity, AccountInfoActivity::class.java)
            startActivityForResult(intent, 1)
        }

        exitMenu.setOnClickListener {

            var sharedPreferences: SharedPreferences = this@MainActivity.getSharedPreferences("userInformation", 0)
            var sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
            sharedPreferencesEditor.putString("token", "")
            sharedPreferencesEditor.putString("username", "")
            sharedPreferencesEditor.putString("firstName", "")
            sharedPreferencesEditor.putString("lastName", "")
            sharedPreferencesEditor.putString("email", null)
            sharedPreferencesEditor.putString("password", null)

            sharedPreferencesEditor.apply()
            sharedPreferencesEditor.commit()

            var intent = Intent(this@MainActivity, RegLoginHolderActivity::class.java)
            startActivity(intent)
            this@MainActivity.finish()
        }

        commentsMenu.setOnClickListener {

            var layoutInflater = this@MainActivity.layoutInflater
            var view: View = layoutInflater.inflate(R.layout.choose_support_layout, null)
            var dialog: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity, R.style.AlertDialogTheme)
            dialog.setView(view)
            dialog.setCancelable(true)

            dialog.create()
            var dialog2 = dialog.show()

            view.telegramChooseSupportTv.setOnClickListener {

                dialog2.dismiss()

                val telegram = Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/linkerpadsupport"))
                startActivity(telegram)
            }

            view.emailChooseSupportTv.setOnClickListener {

                dialog2.dismiss()
                var emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "support@linkerpad.com", null))

                startActivity(Intent.createChooser(emailIntent, "ارسال ایمیل با ..."))
            }

            /* var intent = Intent(this@MainActivity, SendCommentsActivity::class.java)
             startActivity(intent)*/
        }

        aboutUs.setOnClickListener {
            var intent = Intent(this@MainActivity, AboutUSActivity::class.java)
            startActivity(intent)
        }

        strongSettingMenu.setOnClickListener {
            Toast.makeText(this@MainActivity, "بزودی", Toast.LENGTH_LONG).show()
        }

        giudMenu.setOnClickListener {
            var webpage = Uri.parse("http://linkerpad.com/help")
            var intent = Intent(Intent.ACTION_VIEW, webpage)
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
            this@MainActivity.finish()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                headerNameTv.setText(getNameLastName())
                headerEmailTv.setText(getEmail())
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

}
