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
        setContentView(R.layout.add_data_request_layout)


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


        spinnerSetup()

        //back click
        addDataReqBackIcon.setOnClickListener { this@EditDataRequestAcivity.finish() }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Toast.makeText(this@EditDataRequestAcivity, "Nothing Selected", Toast.LENGTH_SHORT).show()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
        Toast.makeText(this@EditDataRequestAcivity, "Selected position: #$position", Toast.LENGTH_SHORT).show()
    }

    fun spinnerSetup() {

        val reqDataList = arrayOf("بایگانی", "تست", "تست۲")
        val reqDataSpnAdapter = ArrayAdapter(this@EditDataRequestAcivity, android.R.layout.simple_spinner_item, reqDataList)
        reqDataSpnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        reqDataSpn.adapter = reqDataSpnAdapter

        reqDataSpn.onItemSelectedListener = this
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