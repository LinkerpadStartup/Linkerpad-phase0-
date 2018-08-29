package com.linkerpad.linkerpad

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.Toast
import com.linkerpad.linkerpad.ApiData.output.GetMaterialInformationResponse
import com.linkerpad.linkerpad.ApiData.output.MaterialResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.MaterialInformationOutput
import com.linkerpad.linkerpad.Models.MaterialViewModel
import kotlinx.android.synthetic.main.edit_materials_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class EditMaterialsActivity : AppCompatActivity() {


    lateinit var progressDialog: ProgressDialog

    var reportDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_materials_layout)
        setSupportActionBar(toolbar)

        var materialId = intent.getStringExtra("id")
        var projectId = intent.getStringExtra("projectId")
        reportDate = getIntent().getStringExtra("reportDate")

        setupProgress()
        getMaterialInformation(projectId, materialId)

        //save materials edit
        saveMaterialTv.setOnClickListener {
            setupProgress()
            editMaterial(projectId, materialId)

        }

        deleteMaterialTv.setOnClickListener {
            setupProgress()
            deleteMaterial(projectId, materialId)
        }

        //back clicked
        editMaterialsBackIcon.setOnClickListener {
            var intent = Intent(this@EditMaterialsActivity, MaterialsActivity::class.java)
            intent.putExtra("projectId", projectId)
            intent.putExtra("reportDate", reportDate)
            startActivity(intent)
            this@EditMaterialsActivity.finish()
        }
    }


    private fun editMaterial(projectId: String, materialId: String) {

        var service: IUserApi = IWebApi.Factory.create()

        var editMaterialBody = MaterialViewModel.setEditMaterial(
                materialId,
                projectId,
                TitleMaterialEdt.text.toString(),
                unitMaterialEdt.text.toString(),
                descriptionMaterialsEdt.text.toString(),
                countMaterialsEdt.text.toString().toFloat()
        )

        var call = service.editMaterial(getToken(), editMaterialBody)

        call.enqueue(object : retrofit2.Callback<MaterialResponse> {
            override fun onFailure(call: Call<MaterialResponse>?, t: Throwable?) {
                progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<MaterialResponse>?, response: Response<MaterialResponse>?) {
                progressDialog.dismiss()

                if (response!!.code() == 200) {
                    Toast.makeText(this@EditMaterialsActivity, "مواد و مصالح با موفقیت ویرایش گردید", Toast.LENGTH_LONG).show()

                    var intent = Intent(this@EditMaterialsActivity, MaterialsActivity::class.java)
                    intent.putExtra("projectId", projectId)
                    intent.putExtra("reportDate", reportDate)
                    startActivity(intent)
                    this@EditMaterialsActivity.finish()
                } else if (response.code() == 404) {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, مواد و مصالح یافت نشد!", Snackbar.LENGTH_LONG).show()

                } else if (response.code() == 405) {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "با عرض پوزش ، شما امکان ویرایش مواد و مصالح را ندارید!", Snackbar.LENGTH_LONG).show()

                }

            }

        })

    }

    private fun getMaterialInformation(projectId: String, materialId: String) {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getMaterialInformation(getToken(), projectId, materialId)

        call.enqueue(object : Callback<GetMaterialInformationResponse> {
            override fun onFailure(call: Call<GetMaterialInformationResponse>?, t: Throwable?) {
                progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<GetMaterialInformationResponse>?, response: Response<GetMaterialInformationResponse>?) {
                progressDialog.dismiss()

                var maaterialInformationData = MaterialViewModel.getMaterialInformation(MaterialInformationOutput(response!!.body()!!.status, response.body()!!.status, response!!.body()!!.responseObject))

                if (response!!.code() == 200) {
                    TitleMaterialEdt.setText(maaterialInformationData.title)
                    countMaterialsEdt.setText(maaterialInformationData.consumedQuantity.toString())
                    unitMaterialEdt.setText(maaterialInformationData.consumedQuantityUnit)
                    descriptionMaterialsEdt.setText(maaterialInformationData.description)
                }


            }
        })
    }

    private fun deleteMaterial(projectId: String, materialId: String) {

        var service: IUserApi = IWebApi.Factory.create()

        var deleteMaterialBody = MaterialViewModel.setDeleteMaterial(materialId, projectId)

        var call = service.deleteMaterial(getToken(), deleteMaterialBody)

        call.enqueue(object : retrofit2.Callback<MaterialResponse> {
            override fun onFailure(call: Call<MaterialResponse>?, t: Throwable?) {
                progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<MaterialResponse>?, response: Response<MaterialResponse>?) {
                progressDialog.dismiss()

                if (response!!.code() == 200) {
                    Toast.makeText(this@EditMaterialsActivity, "مواد و مصالح با موفقیت حذف گردید", Toast.LENGTH_LONG).show()

                    var intent = Intent(this@EditMaterialsActivity, MaterialsActivity::class.java)
                    intent.putExtra("projectId", projectId)
                    intent.putExtra("reportDate", reportDate)
                    startActivity(intent)
                    this@EditMaterialsActivity.finish()
                } else if (response.code() == 404) {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, مواد و مصالح یافت نشد!", Snackbar.LENGTH_LONG).show()

                } else if (response.code() == 405) {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "با عرض پوزش ، شما امکان حذف مواد و مصالح را ندارید!", Snackbar.LENGTH_LONG).show()

                }

            }

        })

    }


    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@EditMaterialsActivity.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
    }

    private fun setupProgress() {
        progressDialog = ProgressDialog(this@EditMaterialsActivity)
        progressDialog.setMessage("لطفا شکیبا باشید")
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
        progressDialog.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        var intent = Intent(this@EditMaterialsActivity, MaterialsActivity::class.java)
        var projectId = getIntent().getStringExtra("projectId")
        intent.putExtra("projectId", projectId)
        intent.putExtra("reportDate", reportDate)
        startActivity(intent)
        this@EditMaterialsActivity.finish()

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
