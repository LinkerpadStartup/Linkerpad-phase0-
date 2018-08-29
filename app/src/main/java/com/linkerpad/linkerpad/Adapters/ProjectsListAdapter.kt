package com.linkerpad.linkerpad.Adapters

import android.annotation.TargetApi
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.OnLongClick
import com.linkerpad.linkerpad.Data.ProjectInformationData
import com.linkerpad.linkerpad.Fragments.ProjectsFragment
import com.linkerpad.linkerpad.ProjectHolderActivity
import com.linkerpad.linkerpad.R
import kotlinx.android.synthetic.main.project_bottom_sheet.view.*
import kotlinx.android.synthetic.main.projects_fragmant_layout.view.*
import kotlinx.android.synthetic.main.projetcs_items.view.*
import android.support.v4.app.FragmentActivity
import android.util.Base64
import com.linkerpad.linkerpad.EditProjectBottomSheetActivity


/**
 * Created by alihajiloo on 8/20/18.
 */
class ProjectsListAdapter(var context: Context, var data: ArrayList<ProjectInformationData>) : RecyclerView.Adapter<ProjectsListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.projetcs_items, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindModel(data[position], position)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /*@BindView(R.id.projectBottomSheet)
        lateinit var projectBottomSheet: LinearLayout*/

        @TargetApi(Build.VERSION_CODES.O)
        fun bindModel(itemModel: ProjectInformationData, position: Int) {

            itemModel.startDate = itemModel.startDate.replace("T00:00:00", "")
            itemModel.endDate = itemModel.endDate.replace("T00:00:00", "")

            itemView.projectNameItemTv.setText(itemModel.name)
            itemView.projectStartDateItemTv.setText(itemModel.startDate)
            itemView.projectEndDateItemTv.setText(itemModel.endDate)
            itemView.userRoleProjectItems.setText(if (itemModel.userRole == 0) "مسئول" else if (itemModel.userRole == 1) "مدیر" else if (itemModel.userRole == 2) "سرپرست" else "کارشناس")

            if (itemModel.projectPicture != "") {

                val b = Base64.decode(itemModel.projectPicture, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(b, 0, b.size)
                //  var profileBitmap:Bitmap = BitmapFactory.decodeByteArray(Base64.getDecoder().decode(itemModel.projectPicture), 0, Base64.getDecoder().decode(itemModel.projectPicture).size)
                itemView.projectPictureItemImv.setImageDrawable(BitmapDrawable(context.resources, bitmap))
            }

            itemView.cardProjectInformation.setOnClickListener {
                var intent = Intent(context, ProjectHolderActivity::class.java)
                intent.putExtra("id", data[position].id)
                intent.putExtra("userRole", data[position].userRole)
                context.startActivity(intent)
            }


            itemView.cardProjectInformation.setOnLongClickListener {
                /*   *//*
                view.projectBottomSheet.startAnimation(slideTopAnimation)
                view.projectBottomSheet.visibility = View.VISIBLE*//*
                var view: View = (context as ProjectsFragment).layoutInflater.inflate(R.layout.project_bottom_sheet, null)

                var dialog:BottomSheetDialog = BottomSheetDialog(context)
                dialog.setContentView(view);
                dialog.show()


*/

                var intent = Intent(context, EditProjectBottomSheetActivity::class.java)
                //    var option: ActivityOptions = ActivityOptions.makeCustomAnimation(context,R.anim.slide_top , R.anim.abc_fade_out)
                intent.putExtra("id", data[position].id)
                intent.putExtra("userRole", data[position].userRole)
                context.startActivity(intent)
                return@setOnLongClickListener true
            }

        }


    }
}