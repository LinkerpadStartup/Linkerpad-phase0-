package com.linkerpad.linkerpad

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.linkerpad.linkerpad.ApiData.output.GetMachineryInformationResponse
import com.linkerpad.linkerpad.ApiData.output.MachineryResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.MachineryInformationOutput
import com.linkerpad.linkerpad.Models.MachineryViewModel
import kotlinx.android.synthetic.main.edit_machinery_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class EditMachineryActivity : AppCompatActivity() {

    lateinit var progressDialog: ProgressDialog

    var reportDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_machinery_layout)

        setSupportActionBar(toolbar)

        var equipmentId = intent.getStringExtra("id")
        var projectId = intent.getStringExtra("projectId")
        reportDate = getIntent().getStringExtra("reportDate")


        //get Equipment
        // setupProgress()
        getMachineryInformation(equipmentId, projectId)

        //delete Equipment
        deleteMachineryTv.setOnClickListener {
            // setupProgress()

            AlertDialog.Builder(this@EditMachineryActivity, R.style.AlertDialogTheme)
                    .setMessage("از حذف این مورد مطمئن هستید؟")
                    .setPositiveButton("بله", { dialog, view ->
                        dialog.dismiss()
                        deleteMachinery(projectId, equipmentId)
                    }).setNegativeButton("خیر", { dialog, view ->
                        dialog.dismiss()
                    })
                    .create()
                    .show()


        }

        //edit Equipment
        saveMachineryTv.setOnClickListener {
            // setupProgress()
            editMachinery(projectId, equipmentId)
        }


        countActiveMachineryEdt.setText("0")
        machineryEnableUpArrow.setOnClickListener {
            var count = changePersianIntToEnglishInt(countActiveMachineryEdt.text.toString()).toInt() + 1

            countActiveMachineryEdt.setText("${count}")
        }

        machineryEnableDownArrow.setOnClickListener {
            var count = changePersianIntToEnglishInt(countActiveMachineryEdt.text.toString()).toInt() + -1

            countActiveMachineryEdt.setText("${count}")
        }


        timeCountMachineryEdt.setText("0.0")
        machineryTimeUpArrow.setOnClickListener {
            var count = changePersianIntToEnglishInt(timeCountMachineryEdt.text.toString()).toDouble() + 0.5

            var solution = remove3Decimal(count)

            timeCountMachineryEdt.setText("${solution}")
        }

        machineryTimeDownArrow.setOnClickListener {
            var count = changePersianIntToEnglishInt(timeCountMachineryEdt.text.toString()).toDouble() - 0.5

            var solution = remove3Decimal(count)

            timeCountMachineryEdt.setText("${solution}")
        }

        countDeactiveMachineryEdt.setText("0")
        machineryDisableUpArrow.setOnClickListener {
            var count = changePersianIntToEnglishInt(countDeactiveMachineryEdt.text.toString()).toInt() + 1

            countDeactiveMachineryEdt.setText("${count}")
        }

        machineryDisableDownArrow.setOnClickListener {
            var count = changePersianIntToEnglishInt(countDeactiveMachineryEdt.text.toString()).toInt() + -1

            countDeactiveMachineryEdt.setText("${count}")
        }

        //back clicked
        editMachineryBackIcon.setOnClickListener {
            var intent = Intent(this@EditMachineryActivity, MachineryActivity::class.java)
            intent.putExtra("projectId", projectId)
            intent.putExtra("reportDate", reportDate)
            startActivity(intent)
            this@EditMachineryActivity.finish()
        }
    }


    private fun changePersianIntToEnglishInt(number: String): String {
        return number.replace("۰", "0")
                .replace("۱", "1")
                .replace("۲", "2")
                .replace("۳", "3")
                .replace("۴", "4")
                .replace("۵", "5")
                .replace("۶", "6")
                .replace("۷", "7")
                .replace("۸", "8")
                .replace("۹", "9")
    }

    private fun remove3Decimal(number: Double): Double {
        val number: Double = number
        val number3digits: Double = Math.round(number * 1000.0) / 1000.0
        val number2digits: Double = Math.round(number3digits * 100.0) / 100.0
        val solution: Double = Math.round(number2digits * 10.0) / 10.0

        return solution
    }

    private fun editMachinery(projectId: String, equipmentId: String) {

        var service: IUserApi = IWebApi.Factory.create()

        var editMachineryBody = MachineryViewModel.setEditMachinery(
                equipmentId,
                projectId,
                TitleMachineryEdt.text.toString(),
                descriptionMachineryEdt.text.toString(),
                countActiveMachineryEdt.text.toString().toInt(),
                countDeactiveMachineryEdt.text.toString().toInt(),
                timeCountMachineryEdt.text.toString().toFloat()
        )

        var call = service.editEquipment(getToken(), editMachineryBody)

        call.enqueue(object : retrofit2.Callback<MachineryResponse> {
            override fun onFailure(call: Call<MachineryResponse>?, t: Throwable?) {
                //  progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<MachineryResponse>?, response: Response<MachineryResponse>?) {
                // progressDialog.dismiss()

                if (response!!.code() == 200) {
                    Toast.makeText(this@EditMachineryActivity, "آیتم ویرایش گردید", Toast.LENGTH_LONG).show()

                    var intent = Intent(this@EditMachineryActivity, MachineryActivity::class.java)
                    intent.putExtra("projectId", projectId)
                    intent.putExtra("reportDate", reportDate)
                    startActivity(intent)
                    this@EditMachineryActivity.finish()
                } else if (response.code() == 404) {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, تجهیزات یافت نشد!", Snackbar.LENGTH_LONG).show()

                } else if (response.code() == 405) {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "با عرض پوزش ، شما امکان ویرایش تجهیزات را ندارید!", Snackbar.LENGTH_LONG).show()

                }

            }

        })

    }

    private fun getMachineryInformation(projectId: String, equipmentId: String) {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getEquipmentInformation(getToken(), projectId, equipmentId)

        call.enqueue(object : Callback<GetMachineryInformationResponse> {
            override fun onFailure(call: Call<GetMachineryInformationResponse>?, t: Throwable?) {
                // progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<GetMachineryInformationResponse>?, response: Response<GetMachineryInformationResponse>?) {
                //   progressDialog.dismiss()

                var machineryInformationData = MachineryViewModel.getMachineryInformation(MachineryInformationOutput(response!!.body()!!.status, response.body()!!.status, response!!.body()!!.responseObject))

                if (response!!.code() == 200) {
                    TitleMachineryEdt.setText(machineryInformationData.title)
                    countActiveMachineryEdt.setText(machineryInformationData.numberOfActiveEquipment.toString())
                    countDeactiveMachineryEdt.setText(machineryInformationData.numberOfDeactiveEquipment.toString())
                    timeCountMachineryEdt.setText(machineryInformationData.workHours.toString())
                    descriptionMachineryEdt.setText(machineryInformationData.description)
                }


            }
        })
    }

    private fun deleteMachinery(projectId: String, equipmentId: String) {

        var service: IUserApi = IWebApi.Factory.create()

        var deleteMachineryBody = MachineryViewModel.setDeleteMachinery(equipmentId, projectId)

        var call = service.deleteEquipment(getToken(), deleteMachineryBody)

        call.enqueue(object : retrofit2.Callback<MachineryResponse> {
            override fun onFailure(call: Call<MachineryResponse>?, t: Throwable?) {
                //  progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<MachineryResponse>?, response: Response<MachineryResponse>?) {
                //   progressDialog.dismiss()

                if (response!!.code() == 200) {
                    Toast.makeText(this@EditMachineryActivity, "آیتم حذف گردید", Toast.LENGTH_LONG).show()

                    var intent = Intent(this@EditMachineryActivity, MachineryActivity::class.java)
                    intent.putExtra("projectId", projectId)
                    intent.putExtra("reportDate", reportDate)
                    startActivity(intent)
                    this@EditMachineryActivity.finish()
                } else if (response.code() == 404) {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, تجهیزات یافت نشد!", Snackbar.LENGTH_LONG).show()

                } else if (response.code() == 405) {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "با عرض پوزش ، شما امکان حذف تجهیزات را ندارید!", Snackbar.LENGTH_LONG).show()

                }

            }

        })

    }


    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@EditMachineryActivity.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
    }

    private fun setupProgress() {
        progressDialog = ProgressDialog(this@EditMachineryActivity)
        progressDialog.setMessage("لطفا شکیبا باشید")
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
        progressDialog.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        var intent = Intent(this@EditMachineryActivity, MachineryActivity::class.java)
        var projectId = getIntent().getStringExtra("projectId")
        intent.putExtra("projectId", projectId)
        intent.putExtra("reportDate", reportDate)
        startActivity(intent)
        this@EditMachineryActivity.finish()

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
