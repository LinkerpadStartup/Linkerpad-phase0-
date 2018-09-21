package com.linkerpad.linkerpad.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.linkerpad.linkerpad.Data.CreateDailyActivityData
import com.linkerpad.linkerpad.Data.DailyActivityInformationData
import com.linkerpad.linkerpad.DoneActivitiesActivity
import com.linkerpad.linkerpad.EditDoneActivitiesActivity
import com.linkerpad.linkerpad.R
import kotlinx.android.synthetic.main.done_activities_item.view.*

/**
 * Created by alihajiloo on 8/25/18.
 */

class DailyActivityAdapter(var context: Context, var data: ArrayList<DailyActivityInformationData>, var projectId: String) : RecyclerView.Adapter<DailyActivityAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.done_activities_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.onBind(data[position], position)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(itemModel: DailyActivityInformationData, position: Int) {


            itemView.titleDoneActivityTv.text = itemModel.title
            itemView.numberOfCrewDoneActivityTv.text = itemModel.numberOfCrew.toString()
            itemView.workHoursDoneActivityTv.text = itemModel.workHours.toString()
            itemView.workLoadsDoneActivityTv.text = itemModel.workload.toString()+" "+itemModel.workloadUnit

            var description = ""
            if (itemModel.description.length > 37) {
                description = itemModel.description.toString().substring(0, 37) + "..."
            }else{
                description = itemModel.description
            }

            itemView.descriptionDoneActivityTv.text = description

            itemView.doneActivitesItemLL.setOnClickListener {
                //item clicked
                var intent = Intent(context, EditDoneActivitiesActivity::class.java)
                intent.putExtra("id", itemModel.id)
                intent.putExtra("projectId", projectId)
                intent.putExtra("reportDate", itemModel.reportDate)

                (context as DoneActivitiesActivity).finish()

                context.startActivity(intent)

            }

        }
    }

}