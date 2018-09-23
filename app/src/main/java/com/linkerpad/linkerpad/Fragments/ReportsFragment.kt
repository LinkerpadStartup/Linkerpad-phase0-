package com.linkerpad.linkerpad.Fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.reports_fragment_layout, container, false)


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


        view.dayDateTv.setText("${jalaliEnd.date+1}")

        var monthName = ""
        when (jalaliEnd.month) {
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
        view.monthDateTv.setText(monthName)
        view.yearDateTv.setText("${jalaliEnd.year}")
        reportDate = "$todayDate"

        //Toast.makeText(context, "$todayDate", Toast.LENGTH_LONG).show()

        //done activities
        view.doneActivitiesLL.setOnClickListener {
            var intent = Intent(context, DoneActivitiesActivity::class.java)
            var projectId = activity!!.intent.getStringExtra("id")
            intent.putExtra("projectId", projectId)
            intent.putExtra("reportDate", reportDate)
            startActivity(intent)
        }

        view.machineryLL.setOnClickListener {
            var intent = Intent(context, MachineryActivity::class.java)
            var projectId = activity!!.intent.getStringExtra("id")
            intent.putExtra("projectId", projectId)
            intent.putExtra("reportDate", reportDate)
            startActivity(intent)

        }

        view.materialsLL.setOnClickListener {
            var intent = Intent(context, MaterialsActivity::class.java)
            var projectId = activity!!.intent.getStringExtra("id")
            intent.putExtra("projectId", projectId)
            intent.putExtra("reportDate", reportDate)
            startActivity(intent)

        }
        view.notesAndEventsLL.setOnClickListener {
            var intent = Intent(context, NotesAndEventsActivity::class.java)
            startActivity(intent)
        }

        view.confirmationReportTv.setOnClickListener {
            var intent = Intent(context, ConfirmationActivity::class.java)
            var projectId = activity!!.intent.getStringExtra("id")
            intent.putExtra("projectId", projectId)
            intent.putExtra("reportDate", reportDate)
            startActivity(intent)
        }

        view.reportDateLL.setOnClickListener {

            /*var intent = Intent(context, ChooseDateActivity::class.java)
            intent.putExtra("startOrEndDate", DateType.StartDate.value)
            startActivityForResult(intent, DateType.StartDate.value)*/

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

