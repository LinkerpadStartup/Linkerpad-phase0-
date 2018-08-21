package com.linkerpad.linkerpad

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.notes_and_events_item.*
import kotlinx.android.synthetic.main.notes_and_events_layout.*

class NotesAndEventsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes_and_events_layout)


        //to edit note And event
        noteAndEventsItemLL.setOnClickListener {
            var intent = Intent(this@NotesAndEventsActivity, EditNoteAndEventActivity::class.java)
            startActivity(intent)
        }


        //fab Add clicked
        notesAndEventsActivityFab.setOnClickListener {
            var intent = Intent(this@NotesAndEventsActivity, AddNoteAndEventActivity::class.java)
            startActivity(intent)
        }

        //back clicked
        notesAndEventsBackIcon.setOnClickListener { this@NotesAndEventsActivity.finish() }
    }
}
