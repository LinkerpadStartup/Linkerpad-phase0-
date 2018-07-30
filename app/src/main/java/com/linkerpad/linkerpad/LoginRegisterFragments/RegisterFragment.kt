package com.linkerpad.linkerpad.LoginRegisterFragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.linkerpad.linkerpad.R
import kotlinx.android.synthetic.main.register_fragment_layout.*
import kotlinx.android.synthetic.main.register_fragment_layout.view.*

/**
 * Created by alihajiloo on 7/30/18.
 */
class RegisterFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view: View = inflater.inflate(R.layout.register_fragment_layout, container, false)

        view.registerBtn.setOnClickListener {
            if (!nameEdt.text.contains(" ")) {
                Snackbar.make(view , "نام خانوادگی را وارد کنید!",Snackbar.LENGTH_LONG).show()
            }
        }

            return view
    }
}