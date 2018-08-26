package com.linkerpad.linkerpad

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import com.linkerpad.linkerpad.ApiData.output.EditUserResponse
import com.linkerpad.linkerpad.ApiData.output.GetUserInformationResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.UserInformationOutputData
import com.linkerpad.linkerpad.Models.UserInformationViewModel
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.mobsandgeeks.saripaar.annotation.Pattern
import kotlinx.android.synthetic.main.account_info_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class AccountInfoActivity : AppCompatActivity(), Validator.ValidationListener {

    @NotEmpty
    @BindView(R.id.nameEdt)
    lateinit var nameEdt: EditText

    @NotEmpty
    @BindView(R.id.lastNameEdt)
    lateinit var lastNameEdt: EditText

    @NotEmpty
    @Email
    @BindView(R.id.emailEdt)
    lateinit var emailEdt: EditText

    @NotEmpty
    @Pattern(regex = "^[9][0-9]{9}$")
    @BindView(R.id.phoneEdt)
    lateinit var phoneEdt: EditText

    @BindView(R.id.companyEdt)
    lateinit var companyEdt: EditText

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_info_layout)
        setSupportActionBar(toolbar)

        //Initialize views
        Initialize()

        //get user information and set to EditTexts
        getUserInformation()

        //save button
        var validator: Validator = Validator(this@AccountInfoActivity)
        validator.setValidationListener(this@AccountInfoActivity)
        saveAccountInformationTv.setOnClickListener {
            validator.validate()
        }

        //back click
        accountInfoBackIcon.setOnClickListener {
            /*var intent = Intent(this@AccountInfoActivity, MainActivity::class.java)
            startActivity(intent)*/
            this@AccountInfoActivity.finish()
        }

    }

    private fun editUserInformation(firstName: String, lastName: String, company: String, mobileNumber: String) {

        var service: IUserApi = IWebApi.Factory.create()
        var call = service.editUserInformation(getToken(), UserInformationViewModel.setUserEditedInformation(
                firstName = firstName,
                lastName = lastName,
                company = company,
                mobileNumber = mobileNumber
        ))

        call.enqueue(object : Callback<EditUserResponse> {
            override fun onFailure(call: Call<EditUserResponse>?, t: Throwable?) {
                progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<EditUserResponse>?, response: Response<EditUserResponse>?) {

                progressDialog.dismiss()

                if (response!!.code() == 200) {
                    getUserInformation()
                    Toast.makeText(this@AccountInfoActivity, "اطلاعات با موفقیت ویرایش شد!", Toast.LENGTH_LONG).show()
                    var intent = Intent(this@AccountInfoActivity, MainActivity::class.java)
                    startActivity(intent)
                    this@AccountInfoActivity.finish()


                } else {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا در ویرایش اطلاعات!", Snackbar.LENGTH_LONG).show()

                }
            }

        })

    }

    private fun Initialize() {
        nameEdt = findViewById(R.id.nameEdt) as EditText
        lastNameEdt = findViewById(R.id.lastNameEdt) as EditText
        emailEdt = findViewById(R.id.emailEdt) as EditText
        companyEdt = findViewById(R.id.companyEdt) as EditText
        phoneEdt = findViewById(R.id.phoneEdt) as EditText


    }

    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@AccountInfoActivity.getSharedPreferences("userInformation", 0)
        return sharedPreferences.getString("token", null)
    }

    private fun getUserInformation() {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getUserInformation(getToken())

        call.enqueue(object : Callback<GetUserInformationResponse> {
            override fun onFailure(call: Call<GetUserInformationResponse>?, t: Throwable?) {

                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<GetUserInformationResponse>?, response: Response<GetUserInformationResponse>?) {

                var userInformation: GetUserInformationResponse? = response!!.body()

                var userInformationOutput = UserInformationViewModel.getUserInformation(UserInformationOutputData(userInformation!!.status,
                        userInformation.message, userInformation.responseObject))

                nameEdt.setText("${userInformationOutput.firstName}")
                lastNameEdt.setText("${userInformationOutput.lastName}")
                emailEdt.setText("${userInformationOutput.emailAddress}")
                companyEdt.setText("${userInformationOutput.company}")
                phoneEdt.setText("${userInformationOutput.mobileNumber.substring(2)}")
                //  Toast.makeText(this@AccountInfoActivity, "${userInformationOutput.firstName}-${userInformationOutput.company}", Toast.LENGTH_LONG).show()

            }

        })
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        for (error: ValidationError in errors!!) {
            val view: View = error.view
            val message = error.getCollatedErrorMessage(this@AccountInfoActivity)

            if (view == nameEdt) {
                nameEdt.error = "اجباری"
            } else if (view == lastNameEdt) {
                lastNameEdt.error = "نام خانوادگی اجباری"
            } else if (view == emailEdt) {
                if (emailEdt.text.toString() == "")
                    emailEdt.error = "اجباری"
                else
                    emailEdt.error = "فرمت نادرست"
            } else if (view == phoneEdt) {
                phoneEdt.error = "فرمت نادرست"
            } else if (view == companyEdt) {

            }
        }


    }

    override fun onValidationSucceeded() {
        setupProgress()

        editUserInformation(nameEdt.text.toString(), lastNameEdt.text.toString(), companyEdt.text.toString(), "98" + phoneEdt.text.toString())
    }

    private fun setupProgress() {
        progressDialog = ProgressDialog(this@AccountInfoActivity)
        progressDialog.setMessage("لطفا شکیبا باشید")
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
        progressDialog.show()
    }


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


}
