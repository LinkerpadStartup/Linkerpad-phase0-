package com.linkerpad.linkerpad

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.linkerpad.linkerpad.ApiData.output.EditProjectResponse
import com.linkerpad.linkerpad.ApiData.output.ProjectInformationResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.DateType
import com.linkerpad.linkerpad.Data.ProjectInformationData
import com.linkerpad.linkerpad.Models.ProjectInformationViewModel
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import kotlinx.android.synthetic.main.choose_imgae_layout.view.*
import kotlinx.android.synthetic.main.edit_project_layout.*
import retrofit2.Call
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

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
            var layoutInflater: LayoutInflater = this@EditProjectActivity.layoutInflater
            var view: View = layoutInflater.inflate(R.layout.choose_imgae_layout, null)
            var dialog: AlertDialog.Builder = AlertDialog.Builder(this@EditProjectActivity, R.style.AlertDialogTheme)
            dialog.setView(view)
            dialog.setCancelable(true)
            /* dialog.setMessage("درخواست خود را به info@linkerpad.com ارسال نمایید.")
             dialog.setPositiveButton("باشه", { dialog, view ->
                 dialog.dismiss()
             })*/
            dialog.create()
            var dialog2 = dialog.show()
            view.galleryChooseImageTv.setOnClickListener {
                //Toast.makeText(this@AddProjectActivity , "gallery",Toast.LENGTH_LONG).show()
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    dialog2.dismiss()
                    ActivityCompat.requestPermissions(this@EditProjectActivity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)

                } else {
                    galleryIntent()
                    dialog2.dismiss()
                }

            }
            view.cameraChooseImageTv.setOnClickListener {
                //  Toast.makeText(this@AddProjectActivity , "camera",Toast.LENGTH_LONG).show()
                if ((ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) || (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(this@EditProjectActivity, arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 2)

                    dialog2.dismiss()
                } else {
                    captureCamera()
                    dialog2.dismiss()
                }

            }

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

    private fun captureCamera() {
        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, 10)
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

    private fun cropImage(uri: Uri) {

        val cropIntent = Intent("com.android.camera.action.CROP")

        cropIntent.setDataAndType(uri, "image/*")
        cropIntent.putExtra("crop", "true")
        cropIntent.putExtra("aspectX", 1)
        cropIntent.putExtra("aspectY", 1)
        cropIntent.putExtra("outputX", 512)
        cropIntent.putExtra("outputY", 512)
        cropIntent.putExtra("return-data", true)
        startActivityForResult(cropIntent, 34)
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        var timeStamp: String =
                SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(Date())
        var imageFileName: String = "IMG_" + timeStamp + "_";
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "$imageFileName", null)
        return Uri.parse(path)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                galleryIntent()
            } else {
            }
        } else if (requestCode == 2) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.isNotEmpty() && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                captureCamera()
            } else {
            }


        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode != Activity.RESULT_CANCELED) {
            if (/*data!!.data != null &&*/ requestCode == SELECT_IMAGE || requestCode == 10) {

                if (requestCode == SELECT_IMAGE)
                    cropImage(data!!.data)
                else {

                    var inImage: Bitmap = data!!.extras.get("data") as Bitmap

                    val tempUri = getImageUri(applicationContext, inImage)

                    cropImage(tempUri)
                }


                /*  var fixedbitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                  accountPicImg.setImageBitmap(fixedbitmap);

                  if (fixedbitmap != null) {
                      //  val bytes = File(uri.toString()).readBytes()
                      var outputStream = ByteArrayOutputStream()
                      fixedbitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                      convertImage = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
                  }*/

                //  onSelectFromGallary(data)
                //   Toast.makeText(this@AddProjectActivity , "${data.data.toString()}",Toast.LENGTH_LONG).show()
            } else if (requestCode == 34) {


                var extras: Bundle = data!!.getExtras()
                var selectedBitmap: Bitmap = extras.getParcelable("data")
                // Set The Bitmap Data To ImageView
                projectPicImg.setImageBitmap(selectedBitmap);
                projectPicImg.setScaleType(ImageView.ScaleType.FIT_XY);

                /* val uri = data.getData()

                 var fixedbitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                 accountPicImg.setImageBitmap(fixedbitmap);
*/
                if (selectedBitmap != null) {
                    //  val bytes = File(uri.toString()).readBytes()
                    var outputStream = ByteArrayOutputStream()
                    selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
                    convertImage = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
                }

            } else {

                val projectDateType = DateType.fromInt(resultCode)
                if (projectDateType!!.value == DateType.StartDate.value) {
                    projectStartDateEdt.setText(data?.getStringExtra("date"))
                } else if (projectDateType.value == DateType.EndDate.value) {
                    projectEndDateEdt.setText(data?.getStringExtra("date"))
                }

            }
        } else {
            if (projectPicture == "") {
                convertImage = ""
                projectPicImg.setImageDrawable(resources.getDrawable(R.drawable.skyline))
            }
        }

        if (resultCode != Activity.RESULT_CANCELED) {

        }


    }

    private fun getProjectInformation(projectId: String) {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getProjectInforamtion(getToken(), projectId)

        call.enqueue(object : retrofit2.Callback<ProjectInformationResponse> {
            override fun onFailure(call: Call<ProjectInformationResponse>?, t: Throwable?) {
                //  progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید.", Snackbar.LENGTH_LONG).show()

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
                        val bitmap = BitmapFactory.decodeByteArray(b, 70, b.size)
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
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا هنگام ویرایش اتصال اینترنت خود را بررسی کنید.", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<EditProjectResponse>?, response: Response<EditProjectResponse>?) {
                //  progressDialog.dismiss()

                if (response!!.code() == 200) {
                    Toast.makeText(this@EditProjectActivity, "پروژه با موفقیت ویرایش شد.", Toast.LENGTH_LONG).show()
                    var intent = Intent(this@EditProjectActivity, ProjectHolderActivity::class.java)
                    intent.putExtra("id", getIntent().getStringExtra("id"))
                    startActivity(intent)
                    this@EditProjectActivity.finish()


                } else if (response.code() == 405) {

                    if (response.message() == "Method Not Allowed") {
                        AlertDialog.Builder(this@EditProjectActivity, R.style.AlertDialogTheme)
                                .setMessage("ویرایش پروژه برای شما امکان پذیر نمی باشد.")
                                .setPositiveButton("باشه", { dialog, view ->
                                    dialog.dismiss()
                                })
                                .create()
                                .show()
                    }
                } else {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا در ویرایش پروژه.", Snackbar.LENGTH_LONG).show()

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
