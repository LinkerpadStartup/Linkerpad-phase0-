package com.linkerpad.linkerpad

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextPaint
import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ViewTarget
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


        var textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        textPaint.setColor(Color.WHITE)
        textPaint.setTextSize(40f)
        textPaint.setTypeface(Typeface.createFromAsset(this.assets, "IRANSansWeb(FaNum).ttf"))

        var titleTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        titleTextPaint.setColor(Color.parseColor("#1E88E5"))
        titleTextPaint.setTextSize(50f)
        titleTextPaint.setTypeface(Typeface.createFromAsset(this.assets, "IRANSansWeb(FaNum)_Medium.ttf"))


        var sharedPreferences: SharedPreferences = this.getSharedPreferences("userInformation", 0)
        if (sharedPreferences.getBoolean("guide7", true)) {

            //showCase font


            /*    val target = ViewTarget(R.id.addProjectFab, activity)
                showcaseView = ShowcaseView.Builder(this.activity)
                        .setTarget(target)
                        .withMaterialShowcase()
                        .setContentTitlePaint(textPaint)
                        .setContentTextPaint(textPaint)
                        .setContentText("برای شروع، پروژه جدیدی را اضافه و مشخصات آن را وارد نمایید.")
                        .setStyle(R.style.CustomShowcaseTheme3)
                        .build()

                showcaseView!!.setButtonText("بعدی")*/

            var viewTarget: ViewTarget = ViewTarget(R.id.notesAndEventsActivityFab, this)

            ShowcaseView.Builder(this)
                    .setTarget(viewTarget)
                    .withMaterialShowcase()
                    .setStyle(R.style.CustomShowcaseTheme3)
                    .setContentTextPaint(textPaint)
                    .setContentTitlePaint(titleTextPaint)
                    .setContentTitle("ثبت کار جدید")
                    .setContentText("برای ثبت کارهای انجام گرفته، در اینجا مشخصات کار را وارد نمایید. " +
                            "شما همه موارد ایجاد شده توسط تیم پروژه را در این صفحه مشاهده میکنید اما تنها مجاز به حذف و یا ویرایش موارد خودتان هستید. " +
                            "(مسئول و مدیر مجاز به حذف و یا ویرایش تمامی موارد می باشند.)")
                    .hideOnTouchOutside()
                    .build()

            var sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
            sharedPreferencesEditor.putBoolean("guide7", true)
            sharedPreferencesEditor.apply()
            sharedPreferencesEditor.commit()

        }

        //back clicked
        notesAndEventsBackIcon.setOnClickListener { this@NotesAndEventsActivity.finish() }
    }
}
