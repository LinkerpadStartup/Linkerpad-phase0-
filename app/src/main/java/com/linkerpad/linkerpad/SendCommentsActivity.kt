package com.linkerpad.linkerpad

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.send_comments_layout.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import android.content.Intent
import android.net.Uri


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

        sendCommentBtn.setOnClickListener {
          /*  Toast.makeText(this@SendCommentsActivity, "پیغام با موفقیت ارسال شد.", Toast.LENGTH_LONG).show()
            this@SendCommentsActivity.finish()*/

            val telegram = Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/linkerpadsupport"))
            startActivity(telegram)
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

}
