package com.linkerpad.linkerpad.Fragments

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getSystemService
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IUserLogic
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.RegisterResponse
import com.linkerpad.linkerpad.Data.UsersData
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
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.mobsandgeeks.saripaar.annotation.Password
import com.mobsandgeeks.saripaar.annotation.Pattern


/**
 * Created by alihajiloo on 7/30/18.
 */
class RegisterFragment : Fragment(), Validator.ValidationListener {

    private var userLogic: IUserLogic? = null

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

    @BindView(R.id.companyEdt)
    lateinit var companyEdt: EditText

    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC)
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
        companyEdt = view.findViewById(R.id.companyEdt) as EditText
        passwordEdt = view.findViewById(R.id.passwordEdt) as EditText

    }


    private fun setupProgress() {
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("لطفا شکیبا باشید")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        for (error: ValidationError in errors!!) {
            val view: View = error.view
            val message = error.getCollatedErrorMessage(context)

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

            } else if (view == passwordEdt) {
                passwordEdt.error = "حداقل ۶ کاراکتر"
            }
        }


    }

    override fun onValidationSucceeded() {

        setupProgress()

        var userData = UsersData(nameEdt.text.toString(),
                lastNameEdt.text.toString(),
                companyEdt.text.toString(),
                "98" + phoneEdt.text.toString().substring(1),
                emailEdt.text.toString(),
                passwordEdt.text.toString())

        Toast.makeText(context, "98" + phoneEdt.text.toString().substring(1), Toast.LENGTH_SHORT).show()
        val service: IUserApi = IWebApi.Factory.create()
        val call = service.register(userData)

        try {
            call.enqueue(object : retrofit2.Callback<RegisterResponse> {
                override fun onFailure(call: Call<RegisterResponse>?, t: Throwable?) {
                    progressDialog.dismiss()
                    Toast.makeText(context, "error: ${t!!.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<RegisterResponse>?, response: Response<RegisterResponse>?) {
                    progressDialog.dismiss()

                    if (response!!.code() == 200) {
                        nameEdt.text.clear()
                        lastNameEdt.text.clear()
                        emailEdt.text.clear()
                        phoneEdt.text.clear()
                        companyEdt.text.clear()
                        passwordEdt.text.clear()

                     /*   var registerResponse: RegisterResponse? = response.body()
                        Toast.makeText(context, "${registerResponse!!.message}", Toast.LENGTH_LONG).show()*/

                    } else if (response!!.code() == 409) {
                        Toast.makeText(context, "خطا، ایمیل شما تکراری است.", Toast.LENGTH_LONG).show()
                    } else if (response!!.code() == 400) {
                        Toast.makeText(context, "خطا، مقادیر ارسالی صحیح نمی باشد.", Toast.LENGTH_LONG).show()
                    }


                }
            })
        } catch (e: Exception) {
            Toast.makeText(context, "${e.printStackTrace().toString()}", Toast.LENGTH_LONG).show()
        }

    }


}