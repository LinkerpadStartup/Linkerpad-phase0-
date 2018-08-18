package com.linkerpad.linkerpad.Fragments

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.ColorFilter
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import com.google.gson.Gson
import com.linkerpad.linkerpad.ApiData.input.LoginBody
import com.linkerpad.linkerpad.ApiData.output.LoginResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.*
import com.linkerpad.linkerpad.ForgetPasswordActivity
import com.linkerpad.linkerpad.MainActivity
import com.linkerpad.linkerpad.Models.UserInformationViewModel
import com.linkerpad.linkerpad.R
import com.linkerpad.linkerpad.RegLoginHolderActivity
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.mobsandgeeks.saripaar.annotation.Password
import kotlinx.android.synthetic.main.login_fragment_layout.view.*
import retrofit2.Call
import retrofit2.Response
import kotlin.math.min


/**
 * Created by alihajiloo on 7/30/18.
 */

class LoginFragment : Fragment(), Validator.ValidationListener {


    @NotEmpty
    @Email
    @BindView(R.id.emailEdt)
    lateinit var emailEdt: EditText

    @NotEmpty
    @Password(min = 6, scheme = Password.Scheme.NUMERIC)
    @BindView(R.id.passwordEdt)
    lateinit var passwordEdt: EditText

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view: View = inflater.inflate(R.layout.login_fragment_layout, container, false)
        initialize(view)

        var validator: Validator = Validator(this@LoginFragment)
        validator.setValidationListener(this@LoginFragment)

        view.loginBtn.setOnClickListener {
            validator.validate()
        }

        view.forgetPasswordTv.setOnClickListener {
            var intent = Intent(context, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }

        var visibility = Visibility.InVisible
        passwordEdt.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        view.seePasswordImv.setOnClickListener {
            if (visibility == Visibility.InVisible) {
                passwordEdt.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                visibility = Visibility.Visible
            } else {
                passwordEdt.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                visibility = Visibility.InVisible
            }
        }


        return view
    }

    private fun setupProgress() {
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("لطفا شکیبا باشید")
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
        progressDialog.show()
    }

    private fun initialize(view: View) {
        emailEdt = view.findViewById(R.id.emailEdt) as EditText
        passwordEdt = view.findViewById(R.id.passwordEdt)
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        for (error: ValidationError in errors!!) {
            val view: View = error.view
            val message = error.getCollatedErrorMessage(context)

            if (view == emailEdt) {
                if (emailEdt.text.toString() == "")
                    emailEdt.error = "اجباری"
                else
                    emailEdt.error = "فرمت نادرست"
            } else if (view == passwordEdt) {
                passwordEdt.error = "حداقل ۶ کاراکتر"
            }
        }
    }

    override fun onValidationSucceeded() {

        setupProgress()

        var loginBody: LoginBody = UserInformationViewModel.setUsernamePassword(
                emailEdt.text.toString(),
                passwordEdt.text.toString())

        val service: IUserApi = IWebApi.Factory.create()
        val call = service.login(loginBody = loginBody)

        try {
            call.enqueue(object : retrofit2.Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                    progressDialog.dismiss()
                    //  Toast.makeText(context, "error: ${t!!.message}", Toast.LENGTH_SHORT).show()
                    Snackbar.make(this@LoginFragment.view!!, "خطا هنگام ورود اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
                    progressDialog.dismiss()

                    if (response!!.code() == 200) {

                        emailEdt.text.clear()
                        passwordEdt.text.clear()

                        var loginResponse: LoginResponse? = response.body()



                        val loginOutputData = UserInformationViewModel.getUserInformation(LoginOutputData(
                                loginResponse!!.status,
                                loginResponse.message,
                                loginResponse.responseObject.token,
                                loginResponse.responseObject.expirationDate,
                                loginResponse.responseObject.userInformationViewModel
                        ))
                        Toast.makeText(context, loginOutputData.status + "", Toast.LENGTH_LONG).show()

                        var sharedPreferences: SharedPreferences = context!!.getSharedPreferences("userInformation", 0)
                        var sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
                        sharedPreferencesEditor.putString("token", loginOutputData.token)
                        sharedPreferencesEditor.putString("username", loginOutputData.emailAddress)
                        sharedPreferencesEditor.apply()
                        sharedPreferencesEditor.commit()

                        activity!!.finish()

                        var intent = Intent(context, MainActivity::class.java)
                        intent.putExtra("firstName", loginOutputData.firstName)
                        intent.putExtra("lastName", loginOutputData.lastName)
                        intent.putExtra("email", loginOutputData.emailAddress)
                        startActivity(intent)


                    } else if (response.code() == 409) {
                        Snackbar.make(this@LoginFragment.view!!, "خطا، ایمیل شما تکراری است.", Snackbar.LENGTH_LONG).show()
                        //  Toast.makeText(context, "خطا، ایمیل شما تکراری است.", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 400) {
                        Snackbar.make(this@LoginFragment.view!!, "خطا، مقادیر ارسالی صحیح نمی باشد.", Snackbar.LENGTH_LONG).show()
                        //  Toast.makeText(context, "خطا، مقادیر ارسالی صحیح نمی باشد.", Toast.LENGTH_LONG).show()
                    }


                }
            })
        } catch (e: Exception) {
            Toast.makeText(context, "${e.printStackTrace().toString()}", Toast.LENGTH_LONG).show()
        }

    }
}