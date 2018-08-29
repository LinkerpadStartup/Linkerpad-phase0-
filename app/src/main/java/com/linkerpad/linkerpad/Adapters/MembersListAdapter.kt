package com.linkerpad.linkerpad.Adapters

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Build
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


/**
 * Created by alihajiloo on 8/20/18.
 */
class MembersListAdapter(var context: Context, var data: ArrayList<MemberInformationData>,var projectId:String) : RecyclerView.Adapter<MembersListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.team_items, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindModel(data[position], position)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

            itemView.memberCard.setOnLongClickListener {

                var intent = Intent(context, EditMemberBottomSheetActivity::class.java)
                //    var option: ActivityOptions = ActivityOptions.makeCustomAnimation(context,R.anim.slide_top , R.anim.abc_fade_out)
                intent.putExtra("id", data[position].id)
                intent.putExtra("projectId",projectId )
                context.startActivity(intent)
                return@setOnLongClickListener true
            }

        }

        fun showViews(itemModel: MemberInformationData) {
            var llParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            itemView.firstTeamLL.layoutParams = llParams

            /** company TextView **/
            val companyTeamTv: TextView = TextView(context)
            var companyTvParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
            companyTeamTv.layoutParams = companyTvParams
            companyTeamTv.setText(itemModel.company)
            companyTeamTv.setPadding(0, 0, 20, 0)
            companyTeamTv.setTextSize(16f)
            companyTeamTv.gravity =Gravity.RIGHT
            companyTeamTv.setTypeface(Typeface.createFromAsset(context!!.assets, "IRANSansWeb(FaNum).ttf"))
            itemView.firstTeamLL.addView(companyTeamTv)

            /** access level TextView **/
            val accessLevelTv: TextView = TextView(context)
            accessLevelTv.setText(if (itemModel.userRole == 0) "مسئول" else if (itemModel.userRole == 1) "مدیر" else if (itemModel.userRole == 2) "سرپرست" else "کارشناس")
            accessLevelTv.setPadding(15, 10, 15, 10)
            accessLevelTv.setTextSize(14f)
            accessLevelTv.gravity = Gravity.CENTER
            accessLevelTv.setTextColor(context.resources.getColor(R.color.white))
            accessLevelTv.background = context.resources.getDrawable(R.drawable.rounded_back_gray)
            accessLevelTv.setTypeface(Typeface.createFromAsset(context!!.assets, "IRANSansWeb(FaNum).ttf"))
            itemView.firstTeamLL.addView(accessLevelTv)

            /** first LinearLayout Views added 👆 **/

            itemView.secondTeamLL.layoutParams = llParams

            /** reseption TextView **/
            val reseptionTv: TextView = TextView(context)
            var reseptionTvParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
            reseptionTv.layoutParams = reseptionTvParams
            reseptionTv.setText("")
            reseptionTv.setPadding(0, 0, 20, 0)
            reseptionTv.setTextSize(14f)
            reseptionTv.setTypeface(Typeface.createFromAsset(context!!.assets, "IRANSansWeb(FaNum).ttf"))
            itemView.secondTeamLL.addView(reseptionTv)

            /** phone number TextView **/
            val phoneNumberTv: TextView = TextView(context)
            phoneNumberTv.setText("0${itemModel.mobileNumber.substring(2)}")
            phoneNumberTv.setPadding(15, 0, 0, 0)
            phoneNumberTv.setTextSize(16f)
            phoneNumberTv.setTypeface(Typeface.createFromAsset(context!!.assets, "IRANSansWeb(FaNum).ttf"))
            itemView.secondTeamLL.addView(phoneNumberTv)

            /** Email TextView **/
            itemView.emailTeamTv.layoutParams = llParams
            itemView.emailTeamTv.setText(itemModel.emailAddress)
            itemView.emailTeamTv.setPadding(25, 0, 0, 10)


        }

        fun clearViews() {
            var llParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
            itemView.firstTeamLL.layoutParams = llParams
            itemView.firstTeamLL.removeAllViews()

            itemView.secondTeamLL.layoutParams = llParams
            itemView.secondTeamLL.removeAllViews()

            itemView.emailTeamTv.layoutParams = llParams
        }
    }
}