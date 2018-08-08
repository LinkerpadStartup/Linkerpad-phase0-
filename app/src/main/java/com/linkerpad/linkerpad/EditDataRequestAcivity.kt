package com.linkerpad.linkerpad

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.edit_data_request_layout.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class EditDataRequestAcivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_data_request_layout)

        setSupportActionBar(toolbar)


        creatDateCalender.setOnClickListener {
            var intent = Intent(this@EditDataRequestAcivity, ChooseDateActivity::class.java)
            intent.putExtra("startOrEndDate", 1)
            startActivityForResult(intent, 1)
        }

        finishDateCalender.setOnClickListener {
            var intent = Intent(this@EditDataRequestAcivity, ChooseDateActivity::class.java)
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
        editDataReqBackIcon.setOnClickListener { this@EditDataRequestAcivity.finish() }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Toast.makeText(this@EditDataRequestAcivity, "Nothing Selected", Toast.LENGTH_SHORT).show()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
        Toast.makeText(this@EditDataRequestAcivity, "Selected position: #$position", Toast.LENGTH_SHORT).show()
    }

    fun spinnerSetup() {

        val situationReqDataList = arrayOf("وضعیت", "باز", "دردست اقدادم", "معلق", "بسته")
        val situationReqDataSpnAdapter = ArrayAdapter(this@EditDataRequestAcivity, android.R.layout.simple_spinner_item, situationReqDataList)
        situationReqDataSpnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        situationReqDataSpn.adapter = situationReqDataSpnAdapter

        val responsibleReqDataList = arrayOf("مسئول", "test", "test2")
        val responsibleReqDataSpnAdapter = ArrayAdapter(this@EditDataRequestAcivity, android.R.layout.simple_spinner_item, responsibleReqDataList)
        responsibleReqDataSpnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        responsibleReqDataSpn.adapter = responsibleReqDataSpnAdapter

        responsibleReqDataSpn.onItemSelectedListener = this
        situationReqDataSpn.onItemSelectedListener = this
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        if (resultCode == 1)
            createReqDataDateEdt.setText(data?.getStringExtra("date"))
        else
            finishReqDataDateEdt.setText(data?.getStringExtra("date"))


        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}