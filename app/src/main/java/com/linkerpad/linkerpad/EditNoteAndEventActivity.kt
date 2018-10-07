package com.linkerpad.linkerpad

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.linkerpad.linkerpad.ApiData.output.GetNoteAndEventInformationResponse
import com.linkerpad.linkerpad.ApiData.output.NoteAndEventResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.NoteAndEventInformationOutput
import com.linkerpad.linkerpad.Models.NoteAndEventsViewModel
import kotlinx.android.synthetic.main.edit_note_and_event_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class EditNoteAndEventActivity : AppCompatActivity() {

    var reportDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_note_and_event_layout)
        setSupportActionBar(toolbar)

        var noteId = intent.getStringExtra("id")
        var projectId = intent.getStringExtra("projectId")
        reportDate = getIntent().getStringExtra("reportDate")


        getNoteInformation(projectId, noteId)

        //save materials edit
        saveNoteAndEventTv.setOnClickListener {
            // setupProgress()

            view ->
            //   setupProgress()

            if (TitleNoteAndEventEdt.text.toString() != "") {
                // setupProgress()
                editNote(projectId, noteId)
            } else {
                Snackbar.make(view, "عنوان وارد شود!", Snackbar.LENGTH_LONG).show()
            }


        }

        deleteNoteAndEventTv.setOnClickListener {
            // setupProgress()

            AlertDialog.Builder(this@EditNoteAndEventActivity, R.style.AlertDialogTheme)
                    .setMessage("از حذف این مورد مطمئن هستید؟")
                    .setPositiveButton("بله", { dialog, view ->
                        dialog.dismiss()
                        deleteNote(projectId, noteId)
                    }).setNegativeButton("خیر", { dialog, view ->
                        dialog.dismiss()
                    })
                    .create()
                    .show()

        }
        // back clicked
        editNoteAndEventBackIcon.setOnClickListener { this@EditNoteAndEventActivity.finish() }
    }

    private fun editNote(projectId: String, noteId: String) {

        var service: IUserApi = IWebApi.Factory.create()

        var editNoteBody = NoteAndEventsViewModel.setEditNoteAndEvent(
                noteId,
                projectId,
                TitleNoteAndEventEdt.text.toString(),
                descriptionNoteAndEventEdt.text.toString()
        )

        var call = service.editNote(getToken(), editNoteBody)

        call.enqueue(object : retrofit2.Callback<NoteAndEventResponse> {
            override fun onFailure(call: Call<NoteAndEventResponse>?, t: Throwable?) {
                //  progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<NoteAndEventResponse>?, response: Response<NoteAndEventResponse>?) {
                //  progressDialog.dismiss()

                if (response!!.code() == 200) {
                    Toast.makeText(this@EditNoteAndEventActivity, "آیتم ویرایش گردید", Toast.LENGTH_LONG).show()

                    var intent = Intent(this@EditNoteAndEventActivity, NotesAndEventsActivity::class.java)
                    intent.putExtra("projectId", projectId)
                    intent.putExtra("reportDate", reportDate)
                    startActivity(intent)
                    this@EditNoteAndEventActivity.finish()
                } else if (response.code() == 404) {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, مشکلات،موانع و یادداشت ها یافت نشد!", Snackbar.LENGTH_LONG).show()

                } else if (response.code() == 405) {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "شما مجاز به انجام این کار نمی باشید", Snackbar.LENGTH_LONG).show()

                }

            }

        })

    }

    private fun getNoteInformation(projectId: String, noteId: String) {

        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getNotesInformation(getToken(), projectId, noteId)

        call.enqueue(object : Callback<GetNoteAndEventInformationResponse> {
            override fun onFailure(call: Call<GetNoteAndEventInformationResponse>?, t: Throwable?) {
                // progressDialog.dismiss()

                var s = ""

                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, اتصال اینترنت خود را بررسی کنید!${t.toString()}", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<GetNoteAndEventInformationResponse>?, response: Response<GetNoteAndEventInformationResponse>?) {
//                progressDialog.dismiss()


                if (response!!.code() == 200) {

                    var noteInformationData = NoteAndEventsViewModel.getNoteAndEventInformation(NoteAndEventInformationOutput(response!!.body()!!.status, response.body()!!.status, response!!.body()!!.responseObject))
                    TitleNoteAndEventEdt.setText(noteInformationData.title)
                    descriptionNoteAndEventEdt.setText(noteInformationData.description)
                }


            }
        })
    }

    private fun deleteNote(projectId: String, noteId: String) {

        var service: IUserApi = IWebApi.Factory.create()

        var deleteNoteBody = NoteAndEventsViewModel.setDeleteNoteAndEvent(noteId, projectId)

        var call = service.deleteNote(getToken(), deleteNoteBody)

        call.enqueue(object : retrofit2.Callback<NoteAndEventResponse> {
            override fun onFailure(call: Call<NoteAndEventResponse>?, t: Throwable?) {
                // progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<NoteAndEventResponse>?, response: Response<NoteAndEventResponse>?) {
                // progressDialog.dismiss()

                if (response!!.code() == 200) {
                    //Toast.makeText(this@EditMaterialsActivity, "مواد و مصالح با موفقیت حذف گردید", Toast.LENGTH_LONG).show()
                    Toast.makeText(this@EditNoteAndEventActivity, "آیتم حذف گردید", Toast.LENGTH_LONG).show()

                    var intent = Intent(this@EditNoteAndEventActivity, NotesAndEventsActivity::class.java)
                    intent.putExtra("projectId", projectId)
                    intent.putExtra("reportDate", reportDate)
                    startActivity(intent)
                    this@EditNoteAndEventActivity.finish()
                } else if (response.code() == 404) {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, مشکلات،موانع و یادداشت ها یافت نشد!", Snackbar.LENGTH_LONG).show()

                } else if (response.code() == 405) {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "شما مجاز به انجام این کار نمی باشید", Snackbar.LENGTH_LONG).show()

                }

            }

        })

    }


    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@EditNoteAndEventActivity.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
    }


    override fun onBackPressed() {
        super.onBackPressed()
        var intent = Intent(this@EditNoteAndEventActivity, NotesAndEventsActivity::class.java)
        var projectId = getIntent().getStringExtra("projectId")
        intent.putExtra("projectId", projectId)
        intent.putExtra("reportDate", reportDate)
        startActivity(intent)
        this@EditNoteAndEventActivity.finish()

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
