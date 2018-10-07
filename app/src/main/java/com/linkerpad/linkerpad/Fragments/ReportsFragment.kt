package com.linkerpad.linkerpad.Fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.CardView
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ViewTarget
import com.linkerpad.linkerpad.*
import com.linkerpad.linkerpad.Data.DateType
import com.linkerpad.linkerpad.Data.JalaliCalendar
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import kotlinx.android.synthetic.main.project_holder_content.*
import kotlinx.android.synthetic.main.reports_fragment_layout.*
import kotlinx.android.synthetic.main.reports_fragment_layout.view.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by alihajiloo on 8/6/18.
 */

class ReportsFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        dayDateTv.setText("$dayOfMonth")

        var monthName = ""
        when (monthOfYear + 1) {
            1 -> {

                monthName = "فروردین"
            }
            2 -> {

                monthName = "اردیبشهت"
            }
            3 -> {

                monthName = "خرداد"
            }
            4 -> {

                monthName = "تیر"
            }
            5 -> {

                monthName = "مرداد"
            }
            6 -> {

                monthName = "شهریور"
            }
            7 -> {

                monthName = "مهر"
            }
            8 -> {

                monthName = "آبان"
            }
            9 -> {

                monthName = "آذر"
            }
            10 -> {

                monthName = "دی"
            }
            11 -> {

                monthName = "بهمن"
            }
            12 -> {

                monthName = "اسفند"
            }


        }
        monthDateTv.setText(monthName)
        yearDateTv.setText("$year")

        reportDate = jalaliToGrogorian("$year-${monthOfYear + 1}-$dayOfMonth")

    }

    var reportDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val DATEPICKERSART = "DatePickerDialogStart"

    /* override fun onActivityCreated(savedInstanceState: Bundle?) {
         super.onActivityCreated(savedInstanceState)
         reportDateLL.setOnClickListener {

             *//*var intent = Intent(context, ChooseDateActivity::class.java)
            intent.putExtra("startOrEndDate", DateType.StartDate.value)
            startActivityForResult(intent, DateType.StartDate.value)*//*

            val now = PersianCalendar()
            val dpd = DatePickerDialog.newInstance(
                    this,
                    now.persianYear,
                    now.persianMonth,
                    now.persianDay
            )
            //dpd.isThemeDark = modeDarkDate!!.isChecked
            //dpd.typeface = fontName
            dpd.show(fragmentManager, DATEPICKERSART)
        }

    }*/


    var showcaseView: ShowcaseView? = null
    var showcaseView2: ShowcaseView? = null

    lateinit var datePickerCardView: CardView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.reports_fragment_layout, container, false)

        datePickerCardView = view.findViewById(R.id.datePikerCardView)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        val todayDate = dateFormat.format(date)
                .replace("۰", "0")
                .replace("۱", "1")
                .replace("۲", "2")
                .replace("۳", "3")
                .replace("۴", "4")
                .replace("۵", "5")
                .replace("۶", "6")
                .replace("۷", "7")
                .replace("۸", "8")
                .replace("۹", "9")
        /*  val year   = todayDate.split("/")[0].toInt()
          val month   = todayDate.split("/")[1].toInt()
          val day   = todayDate.split("/")[2].toInt()*/


        var gregorianEnd: JalaliCalendar.YearMonthDate = JalaliCalendar.YearMonthDate(todayDate.toString().split("-")[0].toInt(), todayDate.toString().split("-")[1].toInt(), todayDate.toString().split("-")[2].toInt())
        var jalaliEnd: JalaliCalendar.YearMonthDate = JalaliCalendar.gregorianToJalali(gregorianEnd)

        val now = PersianCalendar()
/*
        Toast.makeText(context,"${now.persianDay}",Toast.LENGTH_LONG).show()
*/


        view.dayDateTv.text = "${now.persianDay}"

        var monthName = ""
        when (now.persianMonth+1) {
            1 -> {

                monthName = "فروردین"
            }
            2 -> {

                monthName = "اردیبشهت"
            }
            3 -> {

                monthName = "خرداد"
            }
            4 -> {

                monthName = "تیر"
            }
            5 -> {

                monthName = "مرداد"
            }
            6 -> {

                monthName = "شهریور"
            }
            7 -> {

                monthName = "مهر"
            }
            8 -> {

                monthName = "آبان"
            }
            9 -> {

                monthName = "آذر"
            }
            10 -> {

                monthName = "دی"
            }
            11 -> {

                monthName = "بهمن"
            }
            12 -> {

                monthName = "اسفند"
            }
        }
        view.monthDateTv.text = monthName
        view.yearDateTv.text = "${now.persianYear}"
        reportDate = "$todayDate"

        //Toast.makeText(context, "$todayDate", Toast.LENGTH_LONG).show()

        var projectId = activity!!.intent.getStringExtra("id")

        //done activities
        view.doneActivitiesLL.setOnClickListener {
            var intent = Intent(context, DoneActivitiesActivity::class.java)
            intent.putExtra("projectId", projectId)
            intent.putExtra("reportDate", reportDate)
            startActivity(intent)
        }

        view.machineryLL.setOnClickListener {
            var intent = Intent(context, MachineryActivity::class.java)
            intent.putExtra("projectId", projectId)
            intent.putExtra("reportDate", reportDate)
            startActivity(intent)

        }

        view.materialsLL.setOnClickListener {
            var intent = Intent(context, MaterialsActivity::class.java)
            intent.putExtra("projectId", projectId)
            intent.putExtra("reportDate", reportDate)
            startActivity(intent)

        }
        view.notesAndEventsLL.setOnClickListener {
            var intent = Intent(context, NotesAndEventsActivity::class.java)
            intent.putExtra("projectId", projectId)
            intent.putExtra("reportDate", reportDate)
            startActivity(intent)
        }

        view.confirmationReportTv.setOnClickListener {
            var intent = Intent(context, ConfirmationActivity::class.java)
            intent.putExtra("projectId", projectId)
            intent.putExtra("reportDate", reportDate)
            startActivity(intent)
        }

        view.reportDateLL.setOnClickListener {

            /*var intent = Intent(context, ChooseDateActivity::class.java)
            intent.putExtra("startOrEndDate", DateType.StartDate.value)
            startActivityForResult(intent, DateType.StartDate.value)*/

            val dpd = DatePickerDialog.newInstance(
                    this,
                    now.persianYear,
                    now.persianMonth,
                    now.persianDay
            )
            //dpd.isThemeDark = modeDarkDate!!.isChecked
            //dpd.typeface = fontName
            dpd.show(activity!!.fragmentManager, DATEPICKERSART)
        }

        view.reportDateImv.setOnClickListener {

            val now = PersianCalendar()
            val dpd = DatePickerDialog.newInstance(
                    this,
                    now.persianYear,
                    now.persianMonth,
                    now.persianDay
            )
            //dpd.isThemeDark = modeDarkDate!!.isChecked
            //dpd.typeface = fontName
            dpd.show(activity!!.fragmentManager, DATEPICKERSART)
        }

        activity!!.editProjectInformationImv.setImageDrawable(resources.getDrawable(R.drawable.ic_edit_white))
        activity!!.editProjectInformationImv.setOnClickListener {
            var intent = Intent(context, EditProjectActivity::class.java)
            var projectId = activity!!.intent.getStringExtra("id")
            intent.putExtra("id", projectId)
            context!!.startActivity(intent)
            activity!!.finish()

        }


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        var textPaint = TextPaint(Paint.LINEAR_TEXT_FLAG)
        textPaint.setColor(Color.WHITE)
        textPaint.setTextSize(40f)
        textPaint.setTypeface(Typeface.createFromAsset(activity!!.assets, "IRANSansWeb(FaNum).ttf"))

        var titleTextPaint = TextPaint(Paint.LINEAR_TEXT_FLAG)
        titleTextPaint.setColor(Color.parseColor("#1E88E5"))
        titleTextPaint.setTextSize(50f)
        titleTextPaint.setTypeface(Typeface.createFromAsset(activity!!.assets, "IRANSansWeb(FaNum)_Medium.ttf"))

        var showCaseButton = Button(activity)
        showCaseButton.background = resources.getDrawable(R.drawable.round_btn_primary)
        showCaseButton.setTextColor( Color.WHITE)
        showCaseButton.typeface = Typeface.createFromAsset(resources.assets,"IRANSansWeb(FaNum)_Light.ttf")

        var sharedPreferences: SharedPreferences = context!!.getSharedPreferences("userInformation", 0)
        if (sharedPreferences.getBoolean("guide3", true)) {

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

            var viewTarget: ViewTarget = ViewTarget(R.id.dayDateTv, activity)

            showcaseView = ShowcaseView.Builder(this.activity,true)
                    .setTarget(viewTarget)
                    .withNewStyleShowcase()
                    .setStyle(R.style.CustomShowcaseTheme3)
                    .setContentTextPaint(textPaint)
                    .setContentTitlePaint(titleTextPaint)
                    .setContentTitle("ثبت گزارش")
                    .setContentText("ابتدا تاریخ گزارش را انتخاب کنید. سپس آیتم های خود را با توجه به دسته بندی موجود، ثبت نمایید.")
                    .replaceEndButton(showCaseButton)
                    .build()

            showcaseView!!.setButtonText("بعدی")


            showcaseView!!.overrideButtonClick {
                showcaseView!!.hide()
                showcaseView!!.removeAllViews()

                showcaseView2 = ShowcaseView.Builder(activity,true)
                        .setTarget(ViewTarget(R.id.confirmationReportTv, activity))
                        .withNewStyleShowcase()
                        .setStyle(R.style.CustomShowcaseTheme3)
                        .setContentTextPaint(textPaint)
                        .setContentTitlePaint(titleTextPaint)
                        .setContentTitle("تایید نهایی گزارش")
                        .setContentText("پس از تکمیل گزارش خود، آن را تایید نمایید.")
                        .replaceEndButton(showCaseButton)
                        .build()

                showcaseView2!!.setButtonText("بعدی")


                showcaseView2!!.overrideButtonClick {
                    showcaseView2!!.hide()
                    showcaseView2!!.removeAllViews()

                    ShowcaseView.Builder(activity)
                            .setTarget(ViewTarget(R.id.editProjectInformationImv, activity))
                            .withMaterialShowcase()
                            .setStyle(R.style.CustomShowcaseTheme3)
                            .setContentTextPaint(textPaint)
                            .setContentTitlePaint(titleTextPaint)
                            .setContentTitle("ویرایش پروژه")
                            .setContentText("برای ویرایش پروژه، این آیکون را لمس نمایید.\n(افراد با سطح دسترسی «مسئول» ، مجاز به انجام این کار هستند.)")
                            .replaceEndButton(showCaseButton)
                            .build().setButtonText("باشه")
                }
            }


            var sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
            sharedPreferencesEditor.putBoolean("guide3", true)
            sharedPreferencesEditor.apply()
            sharedPreferencesEditor.commit()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode != Activity.RESULT_CANCELED) {
            val projectDateType = DateType.fromInt(resultCode)
            if (projectDateType!!.value == DateType.StartDate.value) {
                var date = data?.getStringExtra("date")
                reportDate = jalaliToGrogorian(date!!.replace("/", "-"))
                dayDateTv.setText(date!!.split("/")[2])
                var monthDate = date!!.split("/")[1].toInt()
                var monthName = ""
                when (monthDate) {
                    1 -> {

                        monthName = "فروردین"
                    }
                    2 -> {

                        monthName = "اردیبشهت"
                    }
                    3 -> {

                        monthName = "خرداد"
                    }
                    4 -> {

                        monthName = "تیر"
                    }
                    5 -> {

                        monthName = "مرداد"
                    }
                    6 -> {

                        monthName = "شهریور"
                    }
                    7 -> {

                        monthName = "مهر"
                    }
                    8 -> {

                        monthName = "آبان"
                    }
                    9 -> {

                        monthName = "آذر"
                    }
                    10 -> {

                        monthName = "دی"
                    }
                    11 -> {

                        monthName = "بهمن"
                    }
                    12 -> {

                        monthName = "اسفند"
                    }


                }
                monthDateTv.setText(monthName)
                yearDateTv.setText(date!!.split("/")[0])
            }

        }


    }

    private fun jalaliToGrogorian(date: String): String {
        var jalali: JalaliCalendar.YearMonthDate = JalaliCalendar.YearMonthDate(date.toString().split("-")[0].toInt(), date.split("-")[1].toInt(), date.split("-")[2].toInt())
        var grogorian: JalaliCalendar.YearMonthDate = JalaliCalendar.jalaliToGregorian(jalali)
        return grogorian.toString()
    }


}

