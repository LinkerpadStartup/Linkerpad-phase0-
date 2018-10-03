package com.linkerpad.linkerpad

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.Toast
import com.linkerpad.linkerpad.ApiData.output.NoteAndEventResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Models.NoteAndEventsViewModel
import kotlinx.android.synthetic.main.add_note_and_event_layout.*
import retrofit2.Call
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class AddNoteAndEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_note_and_event_layout)
        setSupportActionBar(toolbar)

        // back clicked
        addNoteAndEventBackIcon.setOnClickListener { this@AddNoteAndEventActivity.finish() }
        setSupportActionBar(toolbar)

        var reportDate = getIntent().getStringExtra("reportDate")
        var projectId = intent.getStringExtra("projectId")

        saveNoteAndEventTv.setOnClickListener {view->
            //   setupProgress()

            if (TitleNoteAndEventEdt.text.toString() != "") {
                // setupProgress()
                createNoteAndEvent(projectId, reportDate)
            } else {
                Snackbar.make(view, "فقط توضیحات میتواند خالی باشد!", Snackbar.LENGTH_LONG).show()
            }

        }


   
    }

    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@AddNoteAndEventActivity.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
    }

    private fun createNoteAndEvent(projectId: String, reportDate: String) {

        var service: IUserApi = IWebApi.Factory.create()

        var NoteAndEventBody = NoteAndEventsViewModel.setCreateNoteAndEventInformation(NoteAndEventsViewModel(
                "",
                "",
                "",
                projectId,
                "",
                reportDate,
                TitleNoteAndEventEdt.text.toString(),
                descriptionNoteAndEventEdt.text.toString()

        ))

        var call = service.createNote(getToken(), NoteAndEventBody)

        call.enqueue(object : retrofit2.Callback<NoteAndEventResponse> {
            override fun onFailure(call: Call<NoteAndEventResponse>?, t: Throwable?) {
                //  progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()

            }

            override fun onResponse(call: Call<NoteAndEventResponse>?, response: Response<NoteAndEventResponse>?) {

                if (response!!.code() == 200) {

                    //  Toast.makeText(this@AddNoteAndEventsActivity, "مواد و مصالح با موفقیت ثبت شد!", Toast.LENGTH_LONG).show()
                    Toast.makeText(this@AddNoteAndEventActivity, "آیتم ثبت گردید", Toast.LENGTH_LONG).show()
                    var intent = Intent(this@AddNoteAndEventActivity, NotesAndEventsActivity::class.java)
                    intent.putExtra("projectId", projectId)
                    intent.putExtra("reportDate", reportDate)
                    startActivity(intent)
                    this@AddNoteAndEventActivity.finish()

                } else {
                    //  Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، مشکلی هنگام پردازش رخ داده!", Snackbar.LENGTH_LONG).show()

                }
            }

        })


    }
    
    override fun onBackPressed() {
        super.onBackPressed()
        var intent = Intent(this@AddNoteAndEventActivity, NotesAndEventsActivity::class.java)
        var projectId = getIntent().getStringExtra("projectId")
        var reportDate = getIntent().getStringExtra("reportDate")
        intent.putExtra("projectId", projectId)
        intent.putExtra("reportDate", reportDate)
        startActivity(intent)
        this@AddNoteAndEventActivity.finish()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
