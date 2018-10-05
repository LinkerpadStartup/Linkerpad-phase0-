package com.linkerpad.linkerpad

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.linkerpad.linkerpad.ApiData.output.RemoveMemberResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Models.MemberViewModel
import kotlinx.android.synthetic.main.custom_alert_dialog_layout.*
import retrofit2.Call
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class CustomAlertDialog : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_alert_dialog_layout)
        var message = intent.getStringExtra("message")
        var positive = intent.getStringExtra("positive")
        var negative = intent.getStringExtra("negative")

        var userId = intent.getStringExtra("id")
        var projectId = intent.getStringExtra("projectId")

        dialogMessageTv.setText(message)
        positiveTv.setText(positive)
        negativeTv.setText(negative)



        positiveTv.setOnClickListener {

           removeMember(userId, projectId)

        }

        negativeTv.setOnClickListener { this@CustomAlertDialog.finish() }
    }

    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@CustomAlertDialog.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
    }

    private fun removeMember(userId: String, projectId: String) {
        var service: IUserApi = IWebApi.Factory.create()

        var removeMemberBody = MemberViewModel.removeMember(projectId, userId)
        var call = service.removeMember(getToken(), removeMemberBody)

        call.enqueue(object : retrofit2.Callback<RemoveMemberResponse> {
            override fun onFailure(call: Call<RemoveMemberResponse>?, t: Throwable?) {
                //  progressDialog.dismiss()
                Toast.makeText(this@CustomAlertDialog, "خطا، اتصال اینترنت خود را بررسی کنید!", Toast.LENGTH_LONG).show()
                //  Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<RemoveMemberResponse>?, response: Response<RemoveMemberResponse>?) {
                if (response!!.code() == 200) {
                    //  progressDialog.dismiss()
                    AlertDialog.Builder(this@CustomAlertDialog)
                            .setMessage("عضو با موفقیت حذف گردید!")
                            .setPositiveButton("باشه", { dialog, view ->
                                dialog.dismiss()
                                this@CustomAlertDialog.finish()
                            })
                            .create()
                            .show()
                } else if (response.code() == 400) {
                    // progressDialog.dismiss()
                    AlertDialog.Builder(this@CustomAlertDialog)
                            .setMessage("فعالیت غیر مجاز!")
                            .setPositiveButton("باشه", { dialog, view ->
                                dialog.dismiss()
                                this@CustomAlertDialog.finish()
                            })
                            .create()
                            .show()
                } else if (response.code() == 405) {
                    //  progressDialog.dismiss()
                    AlertDialog.Builder(this@CustomAlertDialog)
                            .setMessage("باعرض پوزش شما امکان حذف عضو از پروژه را ندارید. از سازنده یا مدیر درخواست کنید!")
                            .setPositiveButton("باشه", { dialog, view ->
                                dialog.dismiss()
                                this@CustomAlertDialog.finish()
                            })
                            .create()
                            .show()
                } else if (response.code() == 404) {
                    // progressDialog.dismiss()
                    AlertDialog.Builder(this@CustomAlertDialog)
                            .setMessage("ایمیل وارد شده وجود ندارد.شما میتوانید ایشان را به لینکرپد دعوت کنید!")
                            .setPositiveButton("باشه", { dialog, view ->
                                dialog.dismiss()
                                this@CustomAlertDialog.finish()
                            }).setNegativeButton("بعداً", { dialog, view ->
                                dialog.dismiss()
                                this@CustomAlertDialog.finish()
                            })
                            .create()
                            .show()
                }else if (response.code() == 406){
                    AlertDialog.Builder(this@CustomAlertDialog)
                            .setMessage("این دسترسی مجاز نمی باشد")
                            .setPositiveButton("باشه", { dialog, view ->
                                dialog.dismiss()
                                this@CustomAlertDialog.finish()
                            })
                            .create()
                            .show()
                }
            }

        })
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

}
