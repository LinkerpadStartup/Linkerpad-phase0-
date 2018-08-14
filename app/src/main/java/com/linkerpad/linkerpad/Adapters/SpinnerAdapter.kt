package com.linkerpad.linkerpad.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.linkerpad.linkerpad.R
import kotlinx.android.synthetic.main.data_request_spinner_item.view.*

/**
 * Created by alihajiloo on 8/14/18.
 */

class SpinnerAdapter(var context: Context, var color:IntArray , var title:Array<String> ) : BaseAdapter(){

    lateinit var layoutInflater : LayoutInflater
    override fun getView(i: Int, view_: View?, viewGroup: ViewGroup?): View {

        var view = View(context)
        layoutInflater = LayoutInflater.from(context)
       view = layoutInflater.inflate(R.layout.data_request_spinner_item , null)
        view.spinnerColorImv.setImageResource(color[i])
        view.spinnerTitleTv.setText(title[i])

        return view
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return color.size
    }

}