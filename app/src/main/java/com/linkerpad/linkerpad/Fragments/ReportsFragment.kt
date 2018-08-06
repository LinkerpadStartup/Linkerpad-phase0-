package com.linkerpad.linkerpad.Fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.linkerpad.linkerpad.R
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Created by alihajiloo on 8/6/18.
 */

class ReportsFragment:Fragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view : View = inflater.inflate(R.layout.reports_fragment_layout,container,false)

        return view
    }


}