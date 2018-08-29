package com.linkerpad.linkerpad.Fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.linkerpad.linkerpad.*
import com.linkerpad.linkerpad.Data.DateType
import com.linkerpad.linkerpad.Data.JalaliCalendar
import kotlinx.android.synthetic.main.reports_fragment_layout.*
import kotlinx.android.synthetic.main.reports_fragment_layout.view.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Created by alihajiloo on 8/6/18.
 */

class ReportsFragment : Fragment() {

    var reportDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.reports_fragment_layout, container, false)

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

            var intent = Intent(context, ChooseDateActivity::class.java)
            intent.putExtra("startOrEndDate", DateType.StartDate.value)
            startActivityForResult(intent, DateType.StartDate.value)
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