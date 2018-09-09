package com.linkerpad.linkerpad

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.widget.EditText
import butterknife.BindView
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import kotlinx.android.synthetic.main.forget_password_layout.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class ForgetPasswordActivity : AppCompatActivity()/*, Validator.ValidationListener */ {

/*    @NotEmpty
    @Email
    @BindView(R.id.forgetPasswordEmailEdt)
    lateinit var forgetPasswordEmailEdt_: EditText*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forget_password_layout)


        // forgetPasswordEmailEdt_ = findViewById(R.id.forgetPasswordEmailEdt) as EditText

        /*  var validator = Validator(this@ForgetPasswordActivity)
          validator.setValidationListener(this@ForgetPasswordActivity)*/


        submitForgetPasswordBtn.setOnClickListener {

            //  validator.validate()

            this@ForgetPasswordActivity.finish()

        }

        cancelForgetPasswordBtn.setOnClickListener { this@ForgetPasswordActivity.finish() }

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

/*
    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        if (forgetPasswordEmailEdt_.text.toString() == "")
            forgetPasswordEmailEdt_.setError("ایمیل خالی است!")
        else
            forgetPasswordEmailEdt_.setError("فرمت ایمیل نادرست!")
    }

    override fun onValidationSucceeded() {

        AlertDialog.Builder(this@ForgetPasswordActivity)
                .setMessage("ایمیل بازیابی با موفقیت ارسال شد!")
                .setPositiveButton("باشه", { dialog, view ->
                    dialog.dismiss()
                })
                .create()
                .show()


    }*/
}
