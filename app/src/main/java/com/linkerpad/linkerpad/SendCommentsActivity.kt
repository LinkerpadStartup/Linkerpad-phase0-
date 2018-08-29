package com.linkerpad.linkerpad

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.send_comments_layout.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class SendCommentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.send_comments_layout)

        cancelCommentBtn.setOnClickListener {
            this@SendCommentsActivity.finish()
        }

        sendCommentRG.check(R.id.suggestionRB)
        sendCommentRG.setOnCheckedChangeListener { radioGroup, checkedId ->

            if (checkedId == R.id.suggestionRB) {
            } else if (checkedId == R.id.reportRB) {
            } else {
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

}
