package com.linkerpad.linkerpad.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.linkerpad.linkerpad.*
import kotlinx.android.synthetic.main.reports_fragment_layout.view.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Created by alihajiloo on 8/6/18.
 */

class ReportsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.reports_fragment_layout, container, false)

        //done activities
        view.doneActivitiesLL.setOnClickListener {
            var intent = Intent(context, DoneActivitiesActivity::class.java)
            var projectId = activity!!.intent.getStringExtra("id")
            intent.putExtra("projectId", projectId)
            startActivity(intent)
        }

        view.machineryLL.setOnClickListener {
            var intent = Intent(context, MachineryActivity::class.java)
            var projectId = activity!!.intent.getStringExtra("id")
            intent.putExtra("projectId", projectId)
            startActivity(intent)

        }

        view.materialsLL.setOnClickListener {
            var intent = Intent(context, MaterialsActivity::class.java)
            startActivity(intent)

        }
        view.notesAndEventsLL.setOnClickListener {
            var intent = Intent(context, NotesAndEventsActivity::class.java)
            startActivity(intent)
        }
        return view
    }


}