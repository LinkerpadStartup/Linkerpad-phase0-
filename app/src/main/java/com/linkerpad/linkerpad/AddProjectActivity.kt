package com.linkerpad.linkerpad

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.linkerpad.linkerpad.Data.DateType
import kotlinx.android.synthetic.main.add_project_layout.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import android.R.attr.data
import android.annotation.TargetApi
import android.app.Activity
import android.app.ProgressDialog
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.util.Base64
import com.linkerpad.linkerpad.ApiData.output.CreateProjectResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Models.ProjectInformationViewModel
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import javax.security.auth.callback.Callback


class AddProjectActivity : AppCompatActivity() {

    private val SELECT_IMAGE: Int = 9
    private var convertImage: String = ""

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_project_layout)

        startDateCalender.setOnClickListener {
            var intent = Intent(this@AddProjectActivity, ChooseDateActivity::class.java)
            intent.putExtra("startOrEndDate", DateType.StartDate.value)
            startActivityForResult(intent, DateType.StartDate.value)
        }

        endDateCalender.setOnClickListener {
            var intent = Intent(this@AddProjectActivity, ChooseDateActivity::class.java)
            intent.putExtra("startOrEndDate", DateType.EndDate.value)
            startActivityForResult(intent, DateType.EndDate.value)
        }


        selectProjectPictureFab.setOnClickListener {
            galleryIntent()
        }


        createProjectTv.setOnClickListener { view ->
            if (projectTitleEdt.text.toString() != "" && projectAddressEdt.text.toString() != "" && projectCodeEdt.text.toString() != "") {
                createProject()
                setupProgress()
            } else {
                Snackbar.make(view, "عنوان پروژه و آدرس و کد پروژه نمی تواند خالی باشد!", Snackbar.LENGTH_LONG).show()
            }
        }

        //back click
        addProjectBackIcon.setOnClickListener {
            /* var intent = Intent(this@AddProjectActivity, MainActivity::class.java)
             startActivity(intent)*/
            this@AddProjectActivity.finish()
        }
    }

    private fun createProject() {

        var service: IUserApi = IWebApi.Factory.create()
        var projectCreateBody = ProjectInformationViewModel.setCreateProjectInformation(ProjectInformationViewModel(
                status = "",
                message = "",
                projectPicture = convertImage,
                id = "",
                name = projectTitleEdt.text.toString(),
                code = projectCodeEdt.text.toString(),
                address = projectAddressEdt.text.toString(),
                startDate = projectStartDateEdt.text.toString(),
                endDate = projectEndDateEdt.text.toString(),
                maximunmMember = ""
        ))

        /*Toast.makeText(this@AddProjectActivity , "${projectCreateBody.projectStartDate}" , Toast.LENGTH_LONG).show()
        Toast.makeText(this@AddProjectActivity , "${projectCreateBody.projectEndDate}" , Toast.LENGTH_LONG).show()*/
        var call = service.createProject(getToken(), projectCreateBody)

        call.enqueue(object : retrofit2.Callback<CreateProjectResponse> {
            override fun onFailure(call: Call<CreateProjectResponse>?, t: Throwable?) {
                progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا هنگام ورود اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<CreateProjectResponse>?, response: Response<CreateProjectResponse>?) {
                progressDialog.dismiss()

                if (response!!.code() == 200) {
                    Toast.makeText(this@AddProjectActivity, "پروژه با موفقیت ثبت شد!", Toast.LENGTH_LONG).show()
                    var intent = Intent(this@AddProjectActivity, MainActivity::class.java)
                    startActivity(intent)
                    this@AddProjectActivity.finish()


                } else {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا در ثبت پروژه!", Snackbar.LENGTH_LONG).show()

                }
            }

        })

    }


    private fun setupProgress() {
        progressDialog = ProgressDialog(this@AddProjectActivity)
        progressDialog.setMessage("لطفا شکیبا باشید")
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
        progressDialog.show()
    }

    fun galleryIntent() {
        /*   var intent = Intent()
           intent.setType("image*//*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "انتخاب تصویر"), SELECT_IMAGE)*/

        val i = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(i, SELECT_IMAGE)
    }


    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@AddProjectActivity.getSharedPreferences("userInformation", 0)
        return sharedPreferences.getString("token", null)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        if (resultCode != Activity.RESULT_CANCELED) {
            if (data!!.data != null && requestCode == SELECT_IMAGE) {

                val uri = data.getData()

                var fixedbitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                accountPicImg.setImageBitmap(fixedbitmap);

                if (fixedbitmap != null) {
                    //  val bytes = File(uri.toString()).readBytes()
                    var outputStream = ByteArrayOutputStream()
                    fixedbitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
                    convertImage = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
                }

                //  onSelectFromGallary(data)
                //   Toast.makeText(this@AddProjectActivity , "${data.data.toString()}",Toast.LENGTH_LONG).show()
            } else {

                val projectDateType = DateType.fromInt(resultCode)
                if (projectDateType!!.value == DateType.StartDate.value) {
                    projectStartDateEdt.setText(data?.getStringExtra("date"))
                } else if (projectDateType.value == DateType.EndDate.value) {
                    projectEndDateEdt.setText(data?.getStringExtra("date"))
                }

            }
        } else {
            convertImage = ""
            accountPicImg.setImageDrawable(resources.getDrawable(R.drawable.ic_account_circle))
        }


    }


    fun onSelectFromGallary(data: Intent?) {

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

}
