package com.linkerpad.linkerpad

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.linkerpad.linkerpad.ApiData.output.GetUserInformationResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.UserInformationOutputData
import com.linkerpad.linkerpad.Models.UserInformationViewModel
import kotlinx.android.synthetic.main.account_info_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class AccountInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_info_layout)
        setSupportActionBar(toolbar)


        getUserInformation()

        //back click
        accountInfoBackIcon.setOnClickListener {
            /*var intent = Intent(this@AccountInfoActivity, MainActivity::class.java)
            startActivity(intent)*/
            this@AccountInfoActivity.finish()
        }
    }

    private fun getUserInformation() {
        var sharedPreferences: SharedPreferences = this@AccountInfoActivity.getSharedPreferences("userInformation", 0)
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getUserInformation(sharedPreferences.getString("token", null))

        call.enqueue(object : Callback<GetUserInformationResponse> {
            override fun onFailure(call: Call<GetUserInformationResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<GetUserInformationResponse>?, response: Response<GetUserInformationResponse>?) {

                var userInformation: GetUserInformationResponse? = response!!.body()

                var userInformationOutput = UserInformationViewModel.getUserInformation(UserInformationOutputData(userInformation!!.status,
                        userInformation.message, userInformation.responseObject))

                nameEdt.setText("${userInformationOutput.firstName} ${userInformationOutput.lastName}")
                emailEdt.setText("${userInformationOutput.emailAddress}")
                coEdt.setText("${userInformationOutput.company}")
                phoneEdt.setText("${userInformationOutput.mobileNumber.substring(2)}")
                //  Toast.makeText(this@AccountInfoActivity, "${userInformationOutput.firstName}-${userInformationOutput.company}", Toast.LENGTH_LONG).show()

            }

        })
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


}
