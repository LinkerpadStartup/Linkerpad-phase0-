package com.linkerpad.linkerpad.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.linkerpad.linkerpad.R

/**
 * Created by alihajiloo on 8/6/18.
 */

class DataRequestFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view : View = inflater.inflate(R.layout.data_request_fragment_layout,container,false)


        return view
    }
}