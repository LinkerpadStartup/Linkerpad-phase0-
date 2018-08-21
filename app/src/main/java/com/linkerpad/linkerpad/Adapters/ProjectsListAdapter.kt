package com.linkerpad.linkerpad.Adapters

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.linkerpad.linkerpad.Data.ProjectInformationData
import com.linkerpad.linkerpad.ProjectHolderActivity
import com.linkerpad.linkerpad.R
import kotlinx.android.synthetic.main.projetcs_items.view.*


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
        @TargetApi(Build.VERSION_CODES.O)
        fun bindModel(itemModel: ProjectInformationData, position: Int) {

            itemView.projectNameItemTv.setText(itemModel.name)
            itemView.projectStartDateItemTv.setText(itemModel.startDate)
            itemView.projectEndDateItemTv.setText(itemModel.endDate)
            itemView.userRoleProjectItems.setText(if (itemModel.userRole == 0) "مسئول" else if (itemModel.userRole == 1) "مدیر" else if (itemModel.userRole == 2) "سرپرست" else "کارشناس")
            itemView.projectPictureItemImv.setImageBitmap(BitmapFactory.decodeByteArray(java.util.Base64.getDecoder().decode(itemModel.projectPicture), 0, java.util.Base64.getDecoder().decode(itemModel.projectPicture).size))

            itemView.cardProjectInformation.setOnClickListener {
                var intent = Intent(context, ProjectHolderActivity::class.java)
                intent.putExtra("id", data[position].id)
                intent.putExtra("userRole", data[position].userRole)
                context.startActivity(intent)
            }
        }
    }
}