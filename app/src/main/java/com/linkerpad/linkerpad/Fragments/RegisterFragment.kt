package com.linkerpad.linkerpad.Fragments

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.R
import kotlinx.android.synthetic.main.register_fragment_layout.*
import kotlinx.android.synthetic.main.register_fragment_layout.view.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import javax.security.auth.callback.Callback
import android.view.KeyEvent.KEYCODE_BACK
import android.widget.EditText
import butterknife.BindView
import com.linkerpad.linkerpad.ApiData.input.RegisterBody
import com.linkerpad.linkerpad.ApiData.output.RegisterResponse
import com.linkerpad.linkerpad.Data.UserRegisterData
import com.linkerpad.linkerpad.Data.Visibility
import com.linkerpad.linkerpad.Models.UserInformationViewModel
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.mobsandgeeks.saripaar.annotation.Password
import com.mobsandgeeks.saripaar.annotation.Pattern
import kotlinx.android.synthetic.main.reg_login_holder_layout.*


/**
 * Created by alihajiloo on 7/30/18.
 */
class RegisterFragment : Fragment(), Validator.ValidationListener {

    /* private var userLogic: IUserLogic? = null*/

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
    @Pattern(regex = "^[09][0-9]{10}$")
    @BindView(R.id.phoneEdt)
    lateinit var phoneEdt: EditText

    /* @BindView(R.id.companyEdt)
     lateinit var companyEdt: EditText*/

    @Password(min = 6, scheme = Password.Scheme.ANY)
    @BindView(R.id.passwordEdt)
    lateinit var passwordEdt: EditText

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        /* userLogic=UserLogic(IUserApi.Factory.create());
         userLogic!!.Register(UsersData("","","","","",""))*/
        var view: View = inflater.inflate(R.layout.register_fragment_layout, container, false)
        initialize(view)

        var validator = Validator(this@RegisterFragment)
        validator.setValidationListener(this@RegisterFragment)

        view.registerBtn.setOnClickListener {

            validator.validate()

        }


        var visibility = Visibility.InVisible
        passwordEdt.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        view.seePasswordImv.setOnClickListener {
            if (visibility == Visibility.InVisible) {
                passwordEdt.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                visibility = Visibility.Visible
                view.seePasswordImv.setImageDrawable(resources.getDrawable(R.drawable.eye_gray))

            } else {
                passwordEdt.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                visibility = Visibility.InVisible
                view.seePasswordImv.setImageDrawable(resources.getDrawable(R.drawable.ic_visibility_off_gray))

            }
        }


        /*   view.nameEdt.setOnClickListener {
               Toast.makeText(context , "edit" , Toast.LENGTH_SHORT).show()
               var layoutParam: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3000)
               view.registerSpace.layoutParams=layoutParam
           }
   */
        /*if (view != null) {
            val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm.isAcceptingText){
                var layoutParam : LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , 1000)
                view.registerSpace.layoutParams=layoutParam
            }else{
                var layoutParam : LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , 30)
               view.registerSpace.layoutParams=layoutParam
            }

        }*/


        return view
    }

    private fun initialize(view: View) {
        nameEdt = view.findViewById(R.id.nameEdt) as EditText
        lastNameEdt = view.findViewById(R.id.lastNameEdt) as EditText
        emailEdt = view.findViewById(R.id.emailEdt) as EditText
        phoneEdt = view.findViewById(R.id.phoneEdt) as EditText
        // companyEdt = view.findViewById(R.id.companyEdt) as EditText
        passwordEdt = view.findViewById(R.id.passwordEdt) as EditText

    }


    private fun setupProgress() {
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("با لینکرپد پروژه در دستان شماست")
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
        progressDialog.show()
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        for (error: ValidationError in errors!!) {
            val view: View = error.view
            val message = error.getCollatedErrorMessage(context)

            if (view == nameEdt) {
                if (nameEdt.text.toString() == "")
                    nameEdt.error = "نام خود را وارد کنید"
                nameEdt.error = "نام خود را وارد کنید"
            } else if (view == lastNameEdt) {
                lastNameEdt.error = "نام خانوادگی خود را وارد کنید"
            } else if (view == emailEdt) {
                if (emailEdt.text.toString() == "")
                    emailEdt.error = "ایمیل خود را وارد کنید"
                else
                    emailEdt.error = "ایمیل خود را به درستی وارد کنید"
            } else if (view == phoneEdt) {
                if (phoneEdt.text.toString() == "")
                    phoneEdt.error = "تلفن همراه خود را وارد کنید"
                else
                    phoneEdt.error = "نمونه صحیح 091xxxxxxxx"

            } /*else if (view == companyEdt) {

            }*/ else if (view == passwordEdt) {
                if (passwordEdt.text.toString() == "")
                    passwordEdt.error = "رمز عبور خود را وارد کنید"
                else if (passwordEdt.text.length < 6)
                    passwordEdt.error = "حداقل ۶ کاراکتر"
                /* else {

                    // passwordEdt.error = "کاراکتر های مجاز و الزامی a-z و 9-1"

                 }*/


            }
        }


    }

    override fun onValidationSucceeded() {

        if (passwordEdt.text.toString() == "")
            passwordEdt.error = "رمز عبور خود را وارد کنید"
        else if (passwordEdt.text.length < 6)
            passwordEdt.error = "حداقل ۶ کاراکتر"
        else {
            setupProgress()

            var registerBody: RegisterBody = UserInformationViewModel.setRegisterInformation(UserInformationViewModel(
                    firstName = nameEdt.text.toString(),
                    lastName = lastNameEdt.text.toString(),
                    company = "بدون شرکت",
                    mobileNumber = "98" + phoneEdt.text.toString().substring(1),
                    emailAddress = emailEdt.text.toString(),
                    token = "",
                    profilePicture = "",
                    expirationDate = "",
                    message = "",
                    status = "",
                    password = passwordEdt.text.toString()))


            // Toast.makeText(context, "98" + phoneEdt.text.toString().substring(1), Toast.LENGTH_SHORT).show()
            val service: IUserApi = IWebApi.Factory.create()
            val call = service.register(registerBody = registerBody)

            try {
                call.enqueue(object : retrofit2.Callback<RegisterResponse> {
                    override fun onFailure(call: Call<RegisterResponse>?, t: Throwable?) {
                        progressDialog.dismiss()
                        Snackbar.make(this@RegisterFragment.view!!, "خطا هنگام ورود اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<RegisterResponse>?, response: Response<RegisterResponse>?) {
                        progressDialog.dismiss()

                        if (response!!.code() == 200) {
                            nameEdt.text.clear()
                            lastNameEdt.text.clear()
                            emailEdt.text.clear()
                            phoneEdt.text.clear()
                            //companyEdt.text.clear()
                            passwordEdt.text.clear()

                            AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
                                    .setMessage("ثبت نام با موفقیت انجام شد.هم اکنون میتوانید با ایمیل خود وارد شوید.")
                                    .setPositiveButton("باشه", { dialog, view ->
                                        dialog.dismiss()

                                    })
                                    .create()
                                    .show()

                            // change view pager within fragment
                            activity!!.container.setCurrentItem(0)
                            /*   var registerResponse: RegisterResponse? = response.body()
                               Toast.makeText(context, "${registerResponse!!.message}", Toast.LENGTH_LONG).show()*/

                        } else if (response.code() == 409) {
                            Snackbar.make(this@RegisterFragment.view!!, "شما با این ایمیل قبلا ثبت نام کرده اید.", Snackbar.LENGTH_LONG).show()
                        } else if (response.code() == 400) {
                            Snackbar.make(this@RegisterFragment.view!!, "خطا، مقادیر ارسالی صحیح نمی باشد.", Snackbar.LENGTH_LONG).show()
                        }


                    }
                })
            } catch (e: Exception) {
               // Toast.makeText(context, "${e.printStackTrace().toString()}", Toast.LENGTH_LONG).show()
            }

        }
    }


}