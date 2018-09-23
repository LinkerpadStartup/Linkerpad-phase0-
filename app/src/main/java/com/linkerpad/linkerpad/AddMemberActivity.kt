package com.linkerpad.linkerpad

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import com.linkerpad.linkerpad.ApiData.output.AddMemberResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Models.MemberViewModel
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import kotlinx.android.synthetic.main.add_member_layout.*
import retrofit2.Call
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class AddMemberActivity : AppCompatActivity(), Validator.ValidationListener {

    var userRole: Int = 0

    @BindView(R.id.AddMemberEmailEdt)
    @Email
    @NotEmpty
    lateinit var addMemberEmailEdt: EditText

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_member_layout)

        var validator: Validator = Validator(this@AddMemberActivity)
        validator.setValidationListener(this)

        submitAddMemberBtn.setOnClickListener { validator.validate() }

        cancelAddMemberBtn.setOnClickListener {
            this@AddMemberActivity.finish()
        }

        addMemberRG.check(R.id.collaboratorRB)
        addMemberRG.setOnCheckedChangeListener { radioGroup, view ->
            if (view == R.id.adminRB) {
                userRole = 1
            } else if (view == R.id.powerCollaboratorRB) {
                userRole = 2
            } else if (view == R.id.collaboratorRB) {
                userRole = 3
            }
        }

        Initialize()
    }

    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@AddMemberActivity.getSharedPreferences("userInformation", 0)
        return sharedPreferences.getString("token", null)
    }

    private fun setupProgress() {
        progressDialog = ProgressDialog(this@AddMemberActivity)
        progressDialog.setMessage("لطفا شکیبا باشید")
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
        progressDialog.show()
    }

    private fun addMemberToProject(projectId: String, userRole: Int) {
        var service: IUserApi = IWebApi.Factory.create()
        // Toast.makeText(this, "$userRole", Toast.LENGTH_LONG).show()
        var addMemberBody = MemberViewModel.setAddMemberToProject(projectId, addMemberEmailEdt.text.toString(), userRole)
        var call = service.addMemberToProject(getToken(), addMemberBody)

        call.enqueue(object : retrofit2.Callback<AddMemberResponse> {
            override fun onFailure(call: Call<AddMemberResponse>?, t: Throwable?) {
                progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<AddMemberResponse>?, response: Response<AddMemberResponse>?) {
                if (response!!.code() == 200) {
                    // progressDialog.dismiss()
                    AlertDialog.Builder(this@AddMemberActivity)
                            .setMessage("ایمیل حاوی دعوتنامه با موفقیت ارسال شد!")
                            .setPositiveButton("باشه", { dialog, view ->
                                dialog.dismiss()
                            })
                            .create()
                            .show()
                } else if (response.code() == 400) {
                    //  progressDialog.dismiss()
                    AlertDialog.Builder(this@AddMemberActivity)
                            .setMessage("ایمیل وارد شده هم اکنون عضو تیم است!")
                            .setPositiveButton("باشه", { dialog, view ->
                                dialog.dismiss()
                            })
                            .create()
                            .show()
                } else if (response.code() == 405) {
                    //  progressDialog.dismiss()
                    AlertDialog.Builder(this@AddMemberActivity)
                            .setMessage("باعرض پوزش شما امکان افزودن عضو به پروژه را ندارید. از سازنده یا مدیر درخواست کنید!")
                            .setPositiveButton("باشه", { dialog, view ->
                                dialog.dismiss()
                            })
                            .create()
                            .show()
                } else if (response.code() == 404) {
                    //  progressDialog.dismiss()
                    AlertDialog.Builder(this@AddMemberActivity)
                            .setMessage("عضو مورد نظر کاربر لینکرپد نمی باشد لطفا جهت ثبت نام اولیه با پشتیبانی تماس بگیرید")
                            .setPositiveButton("باشه", { dialog, view ->
                                dialog.dismiss()
                            }).setNegativeButton("بعداً", { dialog, view ->
                                dialog.dismiss()
                            })
                            .create()
                            .show()
                }
            }

        })
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        if (addMemberEmailEdt.text.toString() == "")
            addMemberEmailEdt.setError("ایمیل خالی است!")
        else
            addMemberEmailEdt.setError("فرمت ایمیل نادرست!")
    }

    override fun onValidationSucceeded() {

        //  setupProgress()
        var id = intent.getStringExtra("id")

        addMemberToProject(id, userRole)

    }

    private fun Initialize() {
        addMemberEmailEdt = findViewById(R.id.AddMemberEmailEdt) as EditText
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
