package com.linkerpad.linkerpad

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.text.TextPaint
import android.widget.Button
import android.widget.Toast
import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ViewTarget
import com.linkerpad.linkerpad.Adapters.NoteAndEventAdapter
import com.linkerpad.linkerpad.ApiData.output.NoteAndEventListResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.NoteAndEventInformationData
import kotlinx.android.synthetic.main.notes_and_events_item.*
import kotlinx.android.synthetic.main.notes_and_events_layout.*
import retrofit2.Call
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class NotesAndEventsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes_and_events_layout)

        setSupportActionBar(toolbar)

        var projectId = intent.getStringExtra("projectId")
        var reportDate = getIntent().getStringExtra("reportDate")


        // setupProgress()
        getNoteAndEventList(projectId, reportDate)


        //fab Add clicked
        notesAndEventsActivityFab.setOnClickListener {
            var intent = Intent(this@NotesAndEventsActivity, AddNoteAndEventActivity::class.java)
            intent.putExtra("projectId", projectId)
            intent.putExtra("reportDate", reportDate)
            startActivity(intent)
            this@NotesAndEventsActivity.finish()
        }

        //refreshing
        noteAndEventRefresh.setColorSchemeColors(Color.parseColor("#1E88E5"))
        noteAndEventRefresh.setOnRefreshListener {

            getNoteAndEventListUpdate(projectId, reportDate)
            noteAndEventRefresh.isRefreshing = false

        }

        //refreshing
        refreshBtnImv.setOnClickListener {

            getNoteAndEventListUpdate(projectId, reportDate)

        }

        //Show ShowCase for first initialize application
        ShowCaseSetup()

        //back clicked
        notesAndEventsBackIcon.setOnClickListener { this@NotesAndEventsActivity.finish() }
    }

    private fun getNoteAndEventList(projectId: String, reportDate: String = "2020-02-02") {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getProjectNoteList(getToken(), projectId, reportDate)

        call.enqueue(object : retrofit2.Callback<NoteAndEventListResponse> {
            override fun onFailure(call: Call<NoteAndEventListResponse>?, t: Throwable?) {
                //  progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<NoteAndEventListResponse>?, response: Response<NoteAndEventListResponse>?) {

                // progressDialog.dismiss()

                if(response!!.code() ==200) {
                    var NoteAndEventListResponse = response!!.body()

                    var NoteAndEventList = ArrayList<NoteAndEventInformationData>()
                    NoteAndEventList = NoteAndEventListResponse!!.responseObject

                    noteAndEventRecycler.layoutManager = LinearLayoutManager(this@NotesAndEventsActivity)
                    noteAndEventRecycler.adapter = NoteAndEventAdapter(this@NotesAndEventsActivity, NoteAndEventList, projectId)
                }
            }

        })

    }

    private fun getNoteAndEventListUpdate(projectId: String, reportDate: String = "2020-02-02") {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getProjectNoteList(getToken(), projectId, reportDate)

        call.enqueue(object : retrofit2.Callback<NoteAndEventListResponse> {
            override fun onFailure(call: Call<NoteAndEventListResponse>?, t: Throwable?) {
                //  progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<NoteAndEventListResponse>?, response: Response<NoteAndEventListResponse>?) {

                // progressDialog.dismiss()

                Toast.makeText(this@NotesAndEventsActivity, "بروزرسانی انجام شد", Toast.LENGTH_LONG).show()

                var NoteAndEventListResponse = response!!.body()

                var NoteAndEventList = ArrayList<NoteAndEventInformationData>()
                NoteAndEventList = NoteAndEventListResponse!!.responseObject

                noteAndEventRecycler.layoutManager = LinearLayoutManager(this@NotesAndEventsActivity)
                noteAndEventRecycler.adapter = NoteAndEventAdapter(this@NotesAndEventsActivity, NoteAndEventList, projectId)

            }

        })

    }


    private fun ShowCaseSetup() {

        var textPaint = TextPaint(Paint.LINEAR_TEXT_FLAG)
        textPaint.setColor(Color.WHITE)
        textPaint.setTextSize(40f)
        textPaint.setTypeface(Typeface.createFromAsset(this.assets, "IRANSansWeb(FaNum).ttf"))

        var titleTextPaint = TextPaint(Paint.LINEAR_TEXT_FLAG)
        titleTextPaint.setColor(Color.parseColor("#1E88E5"))
        titleTextPaint.setTextSize(50f)
        titleTextPaint.setTypeface(Typeface.createFromAsset(this.assets, "IRANSansWeb(FaNum)_Medium.ttf"))

        var showCaseButton = Button(this)
        showCaseButton.background = resources.getDrawable(R.drawable.round_btn_primary)
        showCaseButton.setTextColor( Color.WHITE)
        showCaseButton.typeface = Typeface.createFromAsset(resources.assets,"IRANSansWeb(FaNum)_Light.ttf")

        var sharedPreferences: SharedPreferences = this.getSharedPreferences("userInformation", 0)
        if (sharedPreferences.getBoolean("guide7", true)) {

            //showCase font

            var viewTarget: ViewTarget = ViewTarget(R.id.notesAndEventsActivityFab, this)

            ShowcaseView.Builder(this)
                    .setTarget(viewTarget)
                    .withMaterialShowcase()
                    .setStyle(R.style.CustomShowcaseTheme3)
                    .setContentTextPaint(textPaint)
                    .setContentTitlePaint(titleTextPaint)
                    .setContentTitle("ثبت آیتم های جدید")
                    .setContentText("برای ثبت، جزئیات هر آیتم را وارد نمایید. \n لیست کل آیتم های روزانه، در این صفحه دیده می شود اما هر فرد می تواند آیتم های خود را ویرایش یا حذف نماید.\n(افراد با سطح دسترسی «مسئول» یا «مدیر»، مجاز به ویرایش یا حذف تمامی آیتم ها می باشند)")
                    .replaceEndButton(showCaseButton)
                    .build().setButtonText("باشه")

            var sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
            sharedPreferencesEditor.putBoolean("guide7", true)
            sharedPreferencesEditor.apply()
            sharedPreferencesEditor.commit()

        }
    }

    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@NotesAndEventsActivity.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
