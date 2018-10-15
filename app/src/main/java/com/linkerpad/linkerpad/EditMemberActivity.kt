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
import com.linkerpad.linkerpad.ApiData.output.MemberResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Models.MemberViewModel
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import kotlinx.android.synthetic.main.edit_member_layout.*
import retrofit2.Call
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class EditMemberActivity : AppCompatActivity(), Validator.ValidationListener {

    var userRole: Int = 3
/*
    @BindView(R.id.AddMemberEmailEdt)
    @Email
    @NotEmpty
    lateinit var addMemberEmailEdt: EditText*/

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_member_layout)

        /*  var validator: Validator = Validator(this@EditMemberActivity)
          validator.setValidationListener(this)*/

        editMemberEmailEdt.setText("${intent.getStringExtra("email")}")


        submitAddMemberBtn.setOnClickListener {
            /* validator.validate()*/

            var projectId = intent.getStringExtra("projectId")
            var userId = intent.getStringExtra("userId")

            editMemberToProject(userId, projectId, userRole)

        }

        cancelAddMemberBtn.setOnClickListener {
            this@EditMemberActivity.finish()
        }

        var userRoleIntent = intent.getStringExtra("userRole")
        if (userRoleIntent == "1") {
            addMemberRG.check(R.id.adminRB)
        } else if (userRoleIntent == "2") {
            addMemberRG.check(R.id.powerCollaboratorRB)
        } else if (userRoleIntent == "3") {
            addMemberRG.check(R.id.powerCollaboratorRB)
        }

        addMemberRG.setOnCheckedChangeListener { radioGroup, view ->
            if (view == R.id.adminRB) {
                userRole = 1
            } else if (view == R.id.powerCollaboratorRB) {
                userRole = 2
            } else if (view == R.id.collaboratorRB) {
                userRole = 3
            }
        }

        /*    Initialize()*/
    }

    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@EditMemberActivity.getSharedPreferences("userInformation", 0)
        return sharedPreferences.getString("token", null)
    }


    private fun editMemberToProject(userId: String, projectId: String, userRole: Int) {
        var service: IUserApi = IWebApi.Factory.create()
        // Toast.makeText(this, "$userRole", Toast.LENGTH_LONG).show()
        var editMember = MemberViewModel.setEditMemberToProject(userId = userId, projectId = projectId, userRole = userRole)
        var call = service.editUserRoleMember(getToken(), editMember)

        call.enqueue(object : retrofit2.Callback<MemberResponse> {
            override fun onFailure(call: Call<MemberResponse>?, t: Throwable?) {
                progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<MemberResponse>?, response: Response<MemberResponse>?) {
                if (response!!.code() == 200) {
                    // progressDialog.dismiss()
                    AlertDialog.Builder(this@EditMemberActivity)
                            .setMessage("فرد مورد نظر با موفقیت ویرایش شد.")
                            .setPositiveButton("باشه", { dialog, view ->
                                dialog.dismiss()

                            })
                            .create()
                            .show()

                } else if (response.code() == 400) {
                    //  progressDialog.dismiss()
                    AlertDialog.Builder(this@EditMemberActivity)
                            .setMessage("این فرد قبلا به تیم پروژه اضافه شده است.")
                            .setPositiveButton("باشه", { dialog, view ->
                                dialog.dismiss()
                                //   addMemberEmailEdt.setText( "")
                            })
                            .create()
                            .show()
                } else if (response.code() == 405) {
                    //  progressDialog.dismiss()
                    AlertDialog.Builder(this@EditMemberActivity)
                            .setMessage("باعرض پوزش شما امکان افزودن عضو به پروژه را ندارید. از سازنده یا مدیر درخواست کنید.")
                            .setPositiveButton("باشه", { dialog, view ->
                                dialog.dismiss()
                            })
                            .create()
                            .show()
                } else if (response.code() == 404) {
                    //  progressDialog.dismiss()
                    AlertDialog.Builder(this@EditMemberActivity)
                            .setMessage("کاربری با این ایمیل موجود نمی باشد. قبل از افزودن فرد به تیم پروژه، از او بخواهید در لینکرپد ثبت نام نماید.")
                            .setPositiveButton("باشه", { dialog, view ->
                                dialog.dismiss()
                            })
                            .create()
                            .show()
                }
            }

        })
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        /*  if (addMemberEmailEdt.text.toString() == "")
              addMemberEmailEdt.setError("ایمیل خالی است!")
          else
              addMemberEmailEdt.setError("فرمت ایمیل نادرست!")*/
    }

    override fun onValidationSucceeded() {

        /*      //  setupProgress()
              var id = intent.getStringExtra("id")

              addMemberToProject(id, userRole)*/

    }

    /*  private fun Initialize() {
          addMemberEmailEdt = findViewById(R.id.AddMemberEmailEdt) as EditText
      }*/

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
