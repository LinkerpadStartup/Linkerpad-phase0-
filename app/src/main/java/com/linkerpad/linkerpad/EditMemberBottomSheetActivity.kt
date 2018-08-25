package com.linkerpad.linkerpad

import android.app.ProgressDialog
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.linkerpad.linkerpad.ApiData.output.AddMemberResponse
import com.linkerpad.linkerpad.ApiData.output.RemoveMemberResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Models.MemberViewModel
import kotlinx.android.synthetic.main.edit_member_bottom_sheet_layout.*
import retrofit2.Call
import retrofit2.Response

class EditMemberBottomSheetActivity : AppCompatActivity() {

    var userId = ""
    var projectId = ""

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_member_bottom_sheet_layout)

        val slideTopAnimation = AnimationUtils.loadAnimation(this@EditMemberBottomSheetActivity, R.anim.slide_top)
        memberBottomSheet.startAnimation(slideTopAnimation)
        memberBottomSheet.visibility = View.VISIBLE

        spaceEditProjectBottomSheet.setOnClickListener {
            val slideDownAnimation = AnimationUtils.loadAnimation(this@EditMemberBottomSheetActivity, R.anim.slide_down)
            memberBottomSheet.startAnimation(slideDownAnimation)
            memberBottomSheet.visibility = View.INVISIBLE
            this@EditMemberBottomSheetActivity.finish()
        }


        userId = intent.getStringExtra("id")
        projectId = intent.getStringExtra("projectId")

        removeMemberBottomSheetll.setOnClickListener {
            setupProgress()
            removeMember(userId, projectId)
        }
    }

    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@EditMemberBottomSheetActivity.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
    }

    private fun setupProgress() {
        progressDialog = ProgressDialog(this@EditMemberBottomSheetActivity)
        progressDialog.setMessage("لطفا شکیبا باشید")
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
        progressDialog.show()
    }

    private fun removeMember(userId: String, projectId: String) {
        var service: IUserApi = IWebApi.Factory.create()

        var removeMemberBody = MemberViewModel.removeMember(projectId, userId)
        var call = service.removeMember(getToken(), removeMemberBody)

        call.enqueue(object : retrofit2.Callback<RemoveMemberResponse> {
            override fun onFailure(call: Call<RemoveMemberResponse>?, t: Throwable?) {
                progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<RemoveMemberResponse>?, response: Response<RemoveMemberResponse>?) {
                if (response!!.code() == 200) {
                    progressDialog.dismiss()
                    AlertDialog.Builder(this@EditMemberBottomSheetActivity)
                            .setMessage("عضو با موفقیت حذف گردید!")
                            .setPositiveButton("باشه", { dialog, view ->
                                dialog.dismiss()
                            })
                            .create()
                            .show()
                } else if (response.code() == 400) {
                    progressDialog.dismiss()
                    AlertDialog.Builder(this@EditMemberBottomSheetActivity)
                            .setMessage("ایمیل وارد شده هم اکنون عضو تیم است!")
                            .setPositiveButton("باشه", { dialog, view ->
                                dialog.dismiss()
                            })
                            .create()
                            .show()
                } else if (response.code() == 405) {
                    progressDialog.dismiss()
                    AlertDialog.Builder(this@EditMemberBottomSheetActivity)
                            .setMessage("باعرض پوزش شما امکان حذف عضو از پروژه را ندارید. از سازنده یا مدیر درخواست کنید!")
                            .setPositiveButton("باشه", { dialog, view ->
                                dialog.dismiss()
                            })
                            .create()
                            .show()
                } else if (response.code() == 404) {
                    progressDialog.dismiss()
                    AlertDialog.Builder(this@EditMemberBottomSheetActivity)
                            .setMessage("ایمیل وارد شده وجود ندارد.شما میتوانید ایشان را به لینکرپد دعوت کنید!")
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


}

