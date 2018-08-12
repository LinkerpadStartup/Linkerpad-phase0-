package com.linkerpad.linkerpad

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.linkerpad.linkerpad.Data.DateType
import kotlinx.android.synthetic.main.add_data_request_layout.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class AddDataRequestActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_data_request_layout)


        setSupportActionBar(toolbar)

        creatDateCalender.setOnClickListener {
            var intent = Intent(this@AddDataRequestActivity, ChooseDateActivity::class.java)
            intent.putExtra("startOrEndDate", 1)
            startActivityForResult(intent, 1)
        }

        finishDateCalender.setOnClickListener {
            var intent = Intent(this@AddDataRequestActivity, ChooseDateActivity::class.java)
            intent.putExtra("startOrEndDate", 2)
            startActivityForResult(intent, 1)
        }


        var i: Int = 0
        lockEditReqDataImv.setOnClickListener {
            if (i == 0) {
                lockEditReqDataImv.setImageResource(R.drawable.ic_lock_gray)
                i = 1
            } else {
                lockEditReqDataImv.setImageResource(R.drawable.ic_lock_open)
                i = 0
            }
        }

        spinnerSetup()

        //back click
        addDataReqBackIcon.setOnClickListener { this@AddDataRequestActivity.finish() }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Toast.makeText(this@AddDataRequestActivity, "Nothing Selected", Toast.LENGTH_SHORT).show()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
        Toast.makeText(this@AddDataRequestActivity, "Selected position: #$position", Toast.LENGTH_SHORT).show()
    }

    fun spinnerSetup() {

        val situationReqDataList = arrayOf("وضعیت", "باز", "دردست اقدادم", "معلق", "بسته")
        val situationReqDataSpnAdapter = ArrayAdapter(this@AddDataRequestActivity, android.R.layout.simple_spinner_item, situationReqDataList)
        situationReqDataSpnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        situationReqDataSpn.adapter = situationReqDataSpnAdapter

        val responsibleReqDataList = arrayOf("مسئول", "test", "test2")
        val responsibleReqDataSpnAdapter = ArrayAdapter(this@AddDataRequestActivity, android.R.layout.simple_spinner_item, responsibleReqDataList)
        responsibleReqDataSpnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        responsibleReqDataSpn.adapter = responsibleReqDataSpnAdapter

        responsibleReqDataSpn.onItemSelectedListener = this
        situationReqDataSpn.onItemSelectedListener = this
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        var ReqDateDateType = DateType.fromInt(resultCode)

        if (ReqDateDateType!!.value == DateType.StartDate.value)
            createReqDataDateEdt.setText(data?.getStringExtra("date"))
        else
            finishReqDataDateEdt.setText(data?.getStringExtra("date"))


        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
