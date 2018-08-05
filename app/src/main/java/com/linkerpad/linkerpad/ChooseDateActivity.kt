package com.linkerpad.linkerpad

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ir.mirrajabi.persiancalendar.core.PersianCalendarHandler
import ir.mirrajabi.persiancalendar.core.models.PersianDate
import kotlinx.android.synthetic.main.choose_date_layout.*
import ir.mirrajabi.persiancalendar.core.interfaces.OnMonthChangedListener
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class ChooseDateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_date_layout)

        var calender: PersianCalendarHandler = persian_calendar.calendar
        var today: PersianDate = calender.today

        monthTVpcv.text = calender.getMonthName(today)+" "+today.year

        calender.setOnMonthChangedListener(
                OnMonthChangedListener { date -> monthTVpcv.text = calender.getMonthName(date)+" "+today.year })


    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
