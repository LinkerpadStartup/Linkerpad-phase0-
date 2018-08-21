package com.linkerpad.linkerpad

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.edit_note_and_event_layout.*

class EditNoteAndEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_note_and_event_layout)
        setSupportActionBar(toolbar)

        // back clicked
        editNoteAndEventBackIcon.setOnClickListener { this@EditNoteAndEventActivity.finish() }
    }
}
