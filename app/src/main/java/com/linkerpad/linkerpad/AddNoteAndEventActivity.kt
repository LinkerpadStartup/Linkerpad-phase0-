package com.linkerpad.linkerpad

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.add_note_and_event_layout.*

class AddNoteAndEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_note_and_event_layout)
        setSupportActionBar(toolbar)

        // back clicked
        addNoteAndEventBackIcon.setOnClickListener { this@AddNoteAndEventActivity.finish() }
    }
}
