package com.linkerpad.linkerpad

import android.annotation.TargetApi
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.text.TextPaint
import android.util.Base64
import android.view.View
import android.widget.Toast
import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ViewTarget
import com.linkerpad.linkerpad.ApiData.output.EditProjectResponse
import com.linkerpad.linkerpad.ApiData.output.ProjectInformationResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.DateType
import com.linkerpad.linkerpad.Data.ProjectInformationData
import com.linkerpad.linkerpad.Models.ProjectInformationViewModel
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import kotlinx.android.synthetic.main.edit_project_layout.*
import retrofit2.Call
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.io.ByteArrayOutputStream

class EditProjectActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        if (view!!.tag.equals(DATEPICKERSART))
            projectStartDateEdt.setText("$year/${monthOfYear + 1}/$dayOfMonth")
        else if (view!!.tag.equals(DATEPICKEREND))
            projectEndDateEdt.setText("$year/${monthOfYear + 1}/$dayOfMonth")

    }

    private val DATEPICKERSART = "DatePickerDialogStart"
    private val DATEPICKEREND = "DatePickerDialogEnd"

//    lateinit var progressDialog: ProgressDialog

    private val SELECT_IMAGE: Int = 9
    private var convertImage: String = ""
    private var convertedImage: Boolean = false
    var projectPicture: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_project_layout)
        setSupportActionBar(toolbar)

        // setupProgress()
        getProjectInformation(intent.getStringExtra("id"))

        startDateCalender.setOnClickListener {
            /* var intent = Intent(this@EditProjectActivity, ChooseDateActivity::class.java)
             intent.putExtra("startOrEndDate", DateType.StartDate.value)
             startActivityForResult(intent, DateType.StartDate.value)*/

            val now = PersianCalendar()
            val dpd = DatePickerDialog.newInstance(
                    this@EditProjectActivity,
                    now.persianYear,
                    now.persianMonth,
                    now.persianDay
            )
            //dpd.isThemeDark = modeDarkDate!!.isChecked
            //dpd.typeface = fontName
            dpd.show(fragmentManager, DATEPICKERSART)
        }

        endDateCalender.setOnClickListener {
            /*   var intent = Intent(this@EditProjectActivity, ChooseDateActivity::class.java)
               intent.putExtra("startOrEndDate", DateType.EndDate.value)
               startActivityForResult(intent, DateType.EndDate.value)*/
            val now = PersianCalendar()
            val dpd = DatePickerDialog.newInstance(
                    this@EditProjectActivity,
                    now.persianYear,
                    now.persianMonth,
                    now.persianDay
            )
            //dpd.isThemeDark = modeDarkDate!!.isChecked
            //dpd.typeface = fontName
            dpd.show(fragmentManager, DATEPICKEREND)
        }


        selectProjectPictureFab.setOnClickListener {
            galleryIntent()
        }

        editProjectTv.setOnClickListener {
            // setupProgress()
            editProject(intent.getStringExtra("id"))
        }

        editProjectBackIcon.setOnClickListener {
            var intent = Intent(this@EditProjectActivity, ProjectHolderActivity::class.java)
            intent.putExtra("id", getIntent().getStringExtra("id"))
            startActivity(intent)
            this@EditProjectActivity.finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()

        var intent = Intent(this@EditProjectActivity, ProjectHolderActivity::class.java)
        intent.putExtra("id", getIntent().getStringExtra("id"))
        startActivity(intent)
        this@EditProjectActivity.finish()


    }

    fun galleryIntent() {
        /*   var intent = Intent()
           intent.setType("image*//*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "انتخاب تصویر"), SELECT_IMAGE)*/

        val i = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(i, SELECT_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode != Activity.RESULT_CANCELED) {
            if (data!!.data != null && requestCode == SELECT_IMAGE) {

                val uri = data.getData()

                var fixedbitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                projectPicImg.setImageBitmap(fixedbitmap);

                if (fixedbitmap != null) {
                    //  val bytes = File(uri.toString()).readBytes()
                    var outputStream = ByteArrayOutputStream()
                    fixedbitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
                    convertImage = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
                    convertedImage = true
                }

                //  onSelectFromGallary(data)
                //   Toast.makeText(this@AddProjectActivity , "${data.data.toString()}",Toast.LENGTH_LONG).show()
            } else {

                val projectDateType = DateType.fromInt(resultCode)
                if (projectDateType!!.value == DateType.StartDate.value) {
                    if (data?.getStringExtra("date") != "")
                        projectStartDateEdt.setText(data?.getStringExtra("date"))
                } else if (projectDateType.value == DateType.EndDate.value) {
                    if (data?.getStringExtra("date") != "")
                        projectEndDateEdt.setText(data?.getStringExtra("date"))
                }

            }
        } else {
            convertedImage = true
            convertImage = ""
            projectPicImg.setImageDrawable(resources.getDrawable(R.drawable.ic_account_circle_blue))
        }


    }

    private fun getProjectInformation(projectId: String) {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getProjectInforamtion(getToken(), projectId)

        call.enqueue(object : retrofit2.Callback<ProjectInformationResponse> {
            override fun onFailure(call: Call<ProjectInformationResponse>?, t: Throwable?) {
                //  progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()

            }

            override fun onResponse(call: Call<ProjectInformationResponse>?, response: Response<ProjectInformationResponse>?) {
                // progressDialog.dismiss()


                if (response!!.code() == 200) {
                    var projectInformationData: ProjectInformationData = response!!.body()!!.responseObject
                    var reponseProjectInformation = ProjectInformationViewModel.getProjectInformation(projectInformationData)

                    projectTitleEdt.setText(reponseProjectInformation.name)
                    projectCodeEdt.setText(reponseProjectInformation.code)
                    projectAddressEdt.setText(reponseProjectInformation.address)
                    projectStartDateEdt.setText(reponseProjectInformation.startDate)
                    projectEndDateEdt.setText(reponseProjectInformation.endDate)

                    if (reponseProjectInformation.projectPicture != "") {

                        val b = Base64.decode(reponseProjectInformation.projectPicture, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(b, 0, b.size)
                        //  var profileBitmap:Bitmap = BitmapFactory.decodeByteArray(Base64.getDecoder().decode(itemModel.projectPicture), 0, Base64.getDecoder().decode(itemModel.projectPicture).size)
                        projectPicImg.setImageDrawable(BitmapDrawable(resources, bitmap))
                        projectPicture = reponseProjectInformation.projectPicture
                    }
                }

            }

        })
    }

    private fun editProject(id: String) {


        var service: IUserApi = IWebApi.Factory.create()
        if (convertedImage) {
            projectPicture = convertImage
        }
        var projectCreateBody = ProjectInformationViewModel.setEditProjectInformation(ProjectInformationViewModel(
                status = "",
                message = "",
                projectPicture = projectPicture,
                id = id,
                name = projectTitleEdt.text.toString(),
                code = projectCodeEdt.text.toString(),
                address = projectAddressEdt.text.toString(),
                startDate = projectStartDateEdt.text.toString(),
                endDate = projectEndDateEdt.text.toString(),
                maximunmMember = ""
        ))

        /*Toast.makeText(this@AddProjectActivity , "${projectCreateBody.projectStartDate}" , Toast.LENGTH_LONG).show()
        Toast.makeText(this@AddProjectActivity , "${projectCreateBody.projectEndDate}" , Toast.LENGTH_LONG).show()*/
        var call = service.editProject(getToken(), projectCreateBody)

        call.enqueue(object : retrofit2.Callback<EditProjectResponse> {
            override fun onFailure(call: Call<EditProjectResponse>?, t: Throwable?) {
                // progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا هنگام ویرایش اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<EditProjectResponse>?, response: Response<EditProjectResponse>?) {
                //  progressDialog.dismiss()

                if (response!!.code() == 200) {
                    Toast.makeText(this@EditProjectActivity, "پروژه با موفقیت ویرایش شد!", Toast.LENGTH_LONG).show()
                    var intent = Intent(this@EditProjectActivity, ProjectHolderActivity::class.java)
                    intent.putExtra("id", getIntent().getStringExtra("id"))
                    startActivity(intent)
                    this@EditProjectActivity.finish()


                } else if (response.code() == 405) {

                    if (response.message() == "Method Not Allowed") {
                        AlertDialog.Builder(this@EditProjectActivity, R.style.AlertDialogTheme)
                                .setMessage("ویرایش پروژه برای شما امکان پذیر نمی باشد")
                                .setPositiveButton("باشه", { dialog, view ->
                                    dialog.dismiss()
                                })
                                .create()
                                .show()
                    }
                } else {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا در ویرایش پروژه!", Snackbar.LENGTH_LONG).show()

                }
            }

        })

    }


    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@EditProjectActivity.getSharedPreferences("userInformation", 0)
        return sharedPreferences.getString("token", null)
    }

    /*   private fun setupProgress() {
           progressDialog = ProgressDialog(this@EditProjectActivity)
           progressDialog.setMessage("لطفا شکیبا باشید")
           progressDialog.setCancelable(false)
           progressDialog.isIndeterminate = true
           progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
           progressDialog.show()
       }*/

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
