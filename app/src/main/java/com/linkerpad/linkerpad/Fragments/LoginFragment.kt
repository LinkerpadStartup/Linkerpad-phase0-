package com.linkerpad.linkerpad.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.linkerpad.linkerpad.ForgetPasswordActivity
import com.linkerpad.linkerpad.R
import kotlinx.android.synthetic.main.login_fragment_layout.view.*


/**
 * Created by alihajiloo on 7/30/18.
 */

class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view: View = inflater.inflate(R.layout.login_fragment_layout, container, false)


        view.forgetPasswordTv.setOnClickListener {
            var intent = Intent(context , ForgetPasswordActivity::class.java)
            startActivity(intent)
        }


        return view
    }
}