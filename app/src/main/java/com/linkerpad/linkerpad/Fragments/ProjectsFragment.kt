package com.linkerpad.linkerpad.Fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.linkerpad.linkerpad.ProjectHolderActivity
import com.linkerpad.linkerpad.R
import kotlinx.android.synthetic.main.projects_fragmant_layout.view.*
import kotlinx.android.synthetic.main.projetcs_items.view.*


/**
 * Created by alihajiloo on 7/31/18.
 */

class ProjectsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view: View = inflater.inflate(R.layout.projects_fragmant_layout, container, false)

        view.cardTestClick.setOnClickListener {
            var intent = Intent(context , ProjectHolderActivity::class.java)
            startActivity(intent)
        }

        view.projectsRefreshLayout.setColorSchemeColors(Color.parseColor("#1E88E5"))
        view.projectsRefreshLayout.setOnRefreshListener {
            Handler().postDelayed(Runnable { view.projectsRefreshLayout.setRefreshing(false) }, 2000)

        }

        return view

    }
}