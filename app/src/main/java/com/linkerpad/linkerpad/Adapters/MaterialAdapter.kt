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
import kotlinx.android.synthetic.main.materials_item.view.*


/**
 * Created by alihajiloo on 8/25/18.
 */

class MaterialAdapter(var context: Context, var data: ArrayList<MaterialInformationData>, var projectId: String) : RecyclerView.Adapter<MaterialAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.materials_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.onBind(data[position], position)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(itemModel: MaterialInformationData, position: Int) {

            itemView.titleMaterialTv.text = itemModel.title
            itemView.countMaterialTv.text = itemModel.consumedQuantity.toString() +" "+itemModel.consumedQuantityUnit.toString()


            var description = ""
            if (itemModel.description.length > 37) {
                description = itemModel.description.toString().substring(0, 37) + "..."
            }else{
                description = itemModel.description
            }

            itemView.descriptionMaterialsTv.text = description

            itemView.materialsItemLL.setOnClickListener {
                //item clicked
                var intent = Intent(context, EditMaterialsActivity::class.java)
                intent.putExtra("id", data[position].id)
                intent.putExtra("projectId", projectId)
                intent.putExtra("reportDate",itemModel.reportDate)

                (context as MaterialsActivity).finish()

                context.startActivity(intent)

            }

        }
    }

}