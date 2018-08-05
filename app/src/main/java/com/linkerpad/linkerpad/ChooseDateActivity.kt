package com.linkerpad.linkerpad

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ir.mirrajabi.persiancalendar.core.PersianCalendarHandler
import ir.mirrajabi.persiancalendar.core.interfaces.OnDayClickedListener
import ir.mirrajabi.persiancalendar.core.models.PersianDate
import kotlinx.android.synthetic.main.choose_date_layout.*
import ir.mirrajabi.persiancalendar.core.interfaces.OnMonthChangedListener
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

var today: PersianDate? = null
var startOrEndDate: Int = 0

class ChooseDateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_date_layout)

        startOrEndDate = intent.getIntExtra("startOrEndDate", 1)

        var calender: PersianCalendarHandler = persian_calendar.calendar
        today = calender.today

        monthTVpcv.text = calender.getMonthName(today) + " " + today!!.year

        calender.setOnMonthChangedListener(
                OnMonthChangedListener { date -> monthTVpcv.text = calender.getMonthName(date) + " " + today!!.year })

        var selectedDay: Int = 0
        var selectedMonth: Int = 0
        var selectedYear: Int = 0
        persian_calendar.setOnDayClickedListener(OnDayClickedListener { date ->
            selectedDay = date.dayOfMonth
            selectedMonth = date.month
            selectedYear = date.year

            var date: String = "$selectedYear/$selectedMonth/$selectedDay"
            var intent = Intent()
            intent.putExtra("date", date)
            if (startOrEndDate == 1)
                setResult(1, intent)
            else
                setResult(2, intent)

        })


    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }
}
