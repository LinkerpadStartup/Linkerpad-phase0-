package com.linkerpad.linkerpad.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.linkerpad.linkerpad.*
import com.linkerpad.linkerpad.Data.MaterialInformationData
import com.linkerpad.linkerpad.Data.NoteAndEventInformationData
import kotlinx.android.synthetic.main.materials_item.view.*
import kotlinx.android.synthetic.main.notes_and_events_item.view.*


/**
 * Created by alihajiloo on 8/25/18.
 */

class NoteAndEventAdapter(var context: Context, var data: ArrayList<NoteAndEventInformationData>, var projectId: String) : RecyclerView.Adapter<NoteAndEventAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.notes_and_events_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.onBind(data[position], position)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(itemModel: NoteAndEventInformationData, position: Int) {

            itemView.titleNoteAndEventTv.text = itemModel.title


            var description = ""
            if (itemModel.description.length > 37) {
                description = itemModel.description.toString().substring(0, 37) + "..."
            } else {
                description = itemModel.description
            }

            itemView.descriptionNoteAndEventTv.text = description

            itemView.noteAndEventsItemLL.setOnClickListener {
                //item clicked
                var intent = Intent(context, EditNoteAndEventActivity::class.java)
                intent.putExtra("id", data[position].id)
                intent.putExtra("projectId", projectId)
                intent.putExtra("reportDate", itemModel.reportDate)

                (context as NotesAndEventsActivity).finish()

                context.startActivity(intent)

            }

        }
    }

}