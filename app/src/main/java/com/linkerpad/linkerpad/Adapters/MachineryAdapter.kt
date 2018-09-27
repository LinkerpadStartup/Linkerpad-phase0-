package com.linkerpad.linkerpad.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.linkerpad.linkerpad.Data.EditMachineryData
import com.linkerpad.linkerpad.Data.MachineryInformationData
import com.linkerpad.linkerpad.EditMachineryActivity
import com.linkerpad.linkerpad.MachineryActivity
import com.linkerpad.linkerpad.R
import kotlinx.android.synthetic.main.machinery_item.view.*


/**
 * Created by alihajiloo on 8/25/18.
 */

class MachineryAdapter(var context: Context, var data: ArrayList<MachineryInformationData>, var projectId: String) : RecyclerView.Adapter<MachineryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.machinery_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.onBind(data[position], position)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(itemModel: MachineryInformationData, position: Int) {


            itemView.machineryTitleTv.text = itemModel.title
            itemView.activeMachineryTv.text = itemModel.numberOfActiveEquipment.toString()
            itemView.deactiveMachineryTv.text = itemModel.numberOfDeactiveEquipment.toString()
            itemView.workHoursTv.text = itemModel.workHours.toString()


            var description = ""
            if (itemModel.description.length > 37) {
                description = itemModel.description.toString().substring(0, 37) + "..."
            }else{
                description = itemModel.description
            }

            itemView.descriptionMachineryTv.text = description

            itemView.machineryItemLL.setOnClickListener {
                //item clicked
                var intent = Intent(context, EditMachineryActivity::class.java)
                intent.putExtra("id", data[position].id)
                intent.putExtra("projectId", projectId)
                intent.putExtra("reportDate",itemModel.reportDate)

                (context as MachineryActivity).finish()

                context.startActivity(intent)

            }

        }
    }

}