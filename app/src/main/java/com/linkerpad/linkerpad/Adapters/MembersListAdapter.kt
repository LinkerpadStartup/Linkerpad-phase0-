package com.linkerpad.linkerpad.Adapters

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.linkerpad.linkerpad.Data.MemberInformationData
import com.linkerpad.linkerpad.Data.ProjectInformationData
import com.linkerpad.linkerpad.EditMemberBottomSheetActivity
import com.linkerpad.linkerpad.ProjectHolderActivity
import com.linkerpad.linkerpad.R
import kotlinx.android.synthetic.main.projetcs_items.view.*
import kotlinx.android.synthetic.main.team_items.*
import kotlinx.android.synthetic.main.team_items.view.*
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent.getIntent
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent.getIntent
import android.content.SharedPreferences
import android.support.annotation.NonNull
import android.widget.Toast
import com.linkerpad.linkerpad.ApiData.output.RemoveMemberResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.CustomAlertDialog
import com.linkerpad.linkerpad.Models.MemberViewModel
import com.linkerpad.linkerpad.Models.MemberViewModel.Companion.removeMember
import retrofit2.Call
import retrofit2.Response


/**
 * Created by alihajiloo on 8/20/18.
 */
class MembersListAdapter(var context: Context, var data: ArrayList<MemberInformationData>, var projectId: String) : RecyclerView.Adapter<MembersListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.team_items, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindModel(data[position], position)

       /* viewHolder.itemView.deleteMemberImv.setOnClickListener {
            AlertDialog.Builder(context)
                    .setMessage("آیا از حذف این عضو از پروژه مطمئن هستید؟")
                    .setPositiveButton("حذف شود", { dialog, view ->
                        dialog.dismiss()
                        removeMember(data[position].id, projectId)
                    }).setNegativeButton("خیر", { dialogInterface, i ->
                        dialogInterface.dismiss()
                    })
                    .create()
                    .show()
        }*/
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("MissingPermission")
        @TargetApi(Build.VERSION_CODES.O)
        fun bindModel(itemModel: MemberInformationData, position: Int) {
            var i: Int = 0

            itemView.nameLastNameTv.setText("${itemModel.firstName} ${itemModel.lastName}")
            if (itemModel.profilePicture != "")
//                itemView.userProfilePictureImv.setImageBitmap(BitmapFactory.decodeByteArray(java.util.Base64.getDecoder().decode(itemModel.profilePicture), 0, java.util.Base64.getDecoder().decode(itemModel.profilePicture).size))

                itemView.memberCard.setOnClickListener {
                    if (i == 0) {
                        showViews(itemModel)
                        i = 1
                    } else {
                        clearViews()
                        i = 0
                    }
                }

            itemView.callTeamImv.setOnClickListener {

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    var phoneIntent: Intent = Intent(Intent.ACTION_CALL)
                    phoneIntent.setData(Uri.parse("tel:${itemModel.mobileNumber}"))
                    context.startActivity(phoneIntent)


                } else {

                }


            }

            itemView.emailTeamImv.setOnClickListener {
                var emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "${itemModel.emailAddress}", null))

                context.startActivity(Intent.createChooser(emailIntent, "ارسال ایمیل با ..."))
            }
            /*  itemView.memberCard.setOnLongClickListener {

                  var intent = Intent(context, EditMemberBottomSheetActivity::class.java)
                  //    var option: ActivityOptions = ActivityOptions.makeCustomAnimation(context,R.anim.slide_top , R.anim.abc_fade_out)
                  intent.putExtra("id", data[position].id)
                  intent.putExtra("projectId", projectId)
                  context.startActivity(intent)
                  return@setOnLongClickListener true
              }*/

            itemView.deleteMemberImv.setOnClickListener {
                //   Toast.makeText(context,"${itemModel.firstName}" , Toast.LENGTH_LONG).show()
             /*  AlertDialog.Builder(context.applicationContext)
                        .setMessage("حذف این فرد از پروژه را تایید میکنید؟")
                        .setPositiveButton("بله", { dialog, view ->
                            dialog.dismiss()
                            removeMember(data[position].id, projectId)
                        }).setNegativeButton("خیر", { dialogInterface, i ->
                            dialogInterface.dismiss()
                        })
                        .create()
                        .show()*/

                var intent = Intent(context, CustomAlertDialog::class.java)
                //    var option: ActivityOptions = ActivityOptions.makeCustomAnimation(context,R.anim.slide_top , R.anim.abc_fade_out)
                intent.putExtra("id", data[position].id)
                intent.putExtra("projectId", projectId)
                intent.putExtra("message" , "حذف این فرد از پروژه را تایید میکنید؟")
                intent.putExtra("positive" , "بله")
                intent.putExtra("negative", "خیر")
                context.startActivity(intent)

            }

            itemView.editMemberImv.setOnClickListener {
                //Toast.makeText(context,"${itemModel.lastName}" , Toast.LENGTH_LONG).show()
            }
        }


        fun showViews(itemModel: MemberInformationData) {
            var llParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            itemView.firstTeamLL.layoutParams = llParams
            itemView.secondTeamLL.layoutParams = llParams
            itemView.thirdTeamLL.layoutParams = llParams

            itemView.moreTeamImv.setImageDrawable(context.resources.getDrawable(R.drawable.ic_expand_less_gray))

            /*        */
            /** reseption TextView **//*
            val reseptionTv: TextView = TextView(context)
            var reseptionTvParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
            reseptionTv.layoutParams = reseptionTvParams
            reseptionTv.setText("")
            reseptionTv.setPadding(0, 0, 20, 0)
            reseptionTv.setTextSize(14f)
            reseptionTv.setTypeface(Typeface.createFromAsset(context!!.assets, "IRANSansWeb(FaNum).ttf"))
            itemView.firstTeamLL.addView(reseptionTv)*/

            /** access level TextView **/
            val accessLevelTv: TextView = TextView(context)
            accessLevelTv.setText(if (itemModel.userRole == 0) "مسئول" else if (itemModel.userRole == 1) "مدیر" else if (itemModel.userRole == 2) "سرپرست" else "کارشناس")
            accessLevelTv.setPadding(35, 10, 35, 10)
            accessLevelTv.setTextSize(14f)
            accessLevelTv.gravity = Gravity.CENTER
            accessLevelTv.setTextColor(context.resources.getColor(R.color.white))
            accessLevelTv.background = context.resources.getDrawable(R.drawable.rounded_back_gray)
            accessLevelTv.setTypeface(Typeface.createFromAsset(context!!.assets, "IRANSansWeb(FaNum).ttf"))
            itemView.firstTeamLL.addView(accessLevelTv)

            /** first LinearLayout Views added 👆 **/


            /** company TextView **/
            val companyTeamTv: TextView = TextView(context)
            var companyTvParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
            companyTeamTv.layoutParams = companyTvParams
            companyTeamTv.setText(itemModel.company)
            companyTeamTv.setPadding(15, 0, 20, 0)
            companyTeamTv.setTextSize(16f)
            companyTeamTv.gravity = Gravity.LEFT
            companyTeamTv.setTypeface(Typeface.createFromAsset(context!!.assets, "IRANSansWeb(FaNum).ttf"))
            itemView.firstTeamLL.addView(companyTeamTv)


            /** phone number TextView **/
            var phoneNumberTvParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
            val phoneNumberTv: TextView = TextView(context)
            phoneNumberTv.layoutParams = phoneNumberTvParams
            phoneNumberTv.setText("0${itemModel.mobileNumber.substring(2)}")
            phoneNumberTv.setPadding(15, 0, 0, 0)
            phoneNumberTv.setTextSize(16f)
            phoneNumberTv.gravity = Gravity.LEFT
            phoneNumberTv.setTypeface(Typeface.createFromAsset(context!!.assets, "IRANSansWeb(FaNum).ttf"))
            itemView.secondTeamLL.addView(phoneNumberTv)

            var llParamsWrap: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            var llParams2: LinearLayout.LayoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)

            /** Email TextView **/
            itemView.emailTeamTv.layoutParams = llParams2
            itemView.emailTeamTv.setText(itemModel.emailAddress)
            itemView.emailTeamTv.setPadding(15, 0, 0, 10)


            llParamsWrap.setMargins(0, 0, 0, 10)
            itemView.deleteMemberImv.layoutParams = llParamsWrap
            itemView.deleteMemberImv.setPadding(25, 25, 25, 25)
            itemView.editMemberImv.layoutParams = llParamsWrap
            itemView.editMemberImv.setPadding(25, 25, 25, 25)


        }

        fun clearViews() {
            var llParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
            itemView.firstTeamLL.layoutParams = llParams
            itemView.firstTeamLL.removeAllViews()

            itemView.secondTeamLL.layoutParams = llParams
            itemView.secondTeamLL.removeAllViews()

            itemView.emailTeamTv.layoutParams = llParams
            itemView.deleteMemberImv.layoutParams = llParams
            itemView.editMemberImv.layoutParams = llParams

            itemView.moreTeamImv.setImageDrawable(context.resources.getDrawable(R.drawable.ic_expand_more_gray))

        }

        private fun getToken(): String {
            var sharedPreferences: SharedPreferences = context.getSharedPreferences("userInformation", 0)
            var token = sharedPreferences.getString("token", null)
            return token
        }

        private fun removeMember(userId: String, projectId: String) {
            var service: IUserApi = IWebApi.Factory.create()

            var removeMemberBody = MemberViewModel.removeMember(projectId, userId)
            var call = service.removeMember(getToken(), removeMemberBody)

            call.enqueue(object : retrofit2.Callback<RemoveMemberResponse> {
                override fun onFailure(call: Call<RemoveMemberResponse>?, t: Throwable?) {
                    // progressDialog.dismiss()
                }

                override fun onResponse(call: Call<RemoveMemberResponse>?, response: Response<RemoveMemberResponse>?) {
                    if (response!!.code() == 200) {
                        //  progressDialog.dismiss()
                        AlertDialog.Builder(context)
                                .setMessage("عضو با موفقیت حذف گردید!")
                                .setPositiveButton("باشه", { dialog, view ->
                                    dialog.dismiss()
                                })
                                .create()
                                .show()
                    } else if (response.code() == 400) {
                        //  progressDialog.dismiss()
                        AlertDialog.Builder(context)
                                .setMessage("ایمیل وارد شده هم اکنون عضو تیم است!")
                                .setPositiveButton("باشه", { dialog, view ->
                                    dialog.dismiss()
                                })
                                .create()
                                .show()
                    } else if (response.code() == 405) {
                        //  progressDialog.dismiss()
                        AlertDialog.Builder(context)
                                .setMessage("باعرض پوزش شما امکان حذف عضو از پروژه را ندارید. از سازنده یا مدیر درخواست کنید!")
                                .setPositiveButton("باشه", { dialog, view ->
                                    dialog.dismiss()
                                })
                                .create()
                                .show()
                    } else if (response.code() == 404) {
                        //  progressDialog.dismiss()
                        AlertDialog.Builder(context)
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


}