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
import android.app.Activity
import android.app.ProgressDialog
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Environment
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.text.TextPaint
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ViewTarget
import com.linkerpad.linkerpad.ApiData.output.CreateProjectResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Models.ProjectInformationViewModel
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.multidate.MultiDatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import kotlinx.android.synthetic.main.choose_imgae_layout.view.*
import kotlinx.android.synthetic.main.projects_fragmant_layout.*
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class AddProjectActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        if (view!!.tag.equals(DATEPICKERSART))
            projectStartDateEdt.setText("$year/${monthOfYear + 1}/$dayOfMonth")
        else if (view!!.tag.equals(DATEPICKEREND))
            projectEndDateEdt.setText("$year/${monthOfYear + 1}/$dayOfMonth")

    }

    private val SELECT_IMAGE: Int = 9
    private var convertImage: String = ""

    lateinit var progressDialog: ProgressDialog

    private val DATEPICKERSART = "DatePickerDialogStart"
    private val DATEPICKEREND = "DatePickerDialogEnd"

    var showcaseView: ShowcaseView? = null

    lateinit var createProjectImv: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_project_layout)

        createProjectImv = findViewById(R.id.createProjectTv)

        startDateCalender.setOnClickListener {
            /*  var intent = Intent(this@AddProjectActivity, ChooseDateActivity::class.java)
              intent.putExtra("startOrEndDate", DateType.StartDate.value)
              startActivityForResult(intent, DateType.StartDate.value)*/

            val now = PersianCalendar()
            val dpd = DatePickerDialog.newInstance(
                    this@AddProjectActivity,
                    now.persianYear,
                    now.persianMonth,
                    now.persianDay
            )
            //dpd.isThemeDark = modeDarkDate!!.isChecked
            //dpd.typeface = fontName
            dpd.show(fragmentManager, DATEPICKERSART)
        }

        endDateCalender.setOnClickListener {
            /*var intent = Intent(this@AddProjectActivity, ChooseDateActivity::class.java)
            intent.putExtra("startOrEndDate", DateType.EndDate.value)
            startActivityForResult(intent, DateType.EndDate.value)*/
            val now = PersianCalendar()
            val dpd = DatePickerDialog.newInstance(
                    this@AddProjectActivity,
                    now.persianYear,
                    now.persianMonth,
                    now.persianDay
            )
            //dpd.isThemeDark = modeDarkDate!!.isChecked
            //dpd.typeface = fontName
            dpd.show(fragmentManager, DATEPICKEREND)
        }


        selectProjectPictureFab.setOnClickListener {
            // galleryIntent()
            //captureCamera()

            var layoutInflater: LayoutInflater = this@AddProjectActivity.layoutInflater
            var view: View = layoutInflater.inflate(R.layout.choose_imgae_layout, null)
            var dialog: AlertDialog.Builder = AlertDialog.Builder(this@AddProjectActivity, R.style.AlertDialogTheme)
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
                galleryIntent()
                dialog2.dismiss()
            }
            view.cameraChooseImageTv.setOnClickListener {
                //  Toast.makeText(this@AddProjectActivity , "camera",Toast.LENGTH_LONG).show()
                captureCamera()
                dialog2.dismiss()

            }

            // dialog.show()


            /* if (checkSelfPermission(Manifest.permission.CAMERA)
                     != PackageManager.PERMISSION_GRANTED) {
                 requestPermissions(new String[]{Manifest.permission.CAMERA},
                         MY_CAMERA_PERMISSION_CODE);
             } else {

             }*/
        }


        createProjectTv.setOnClickListener { view ->
            if (projectTitleEdt.text.toString() != "" && projectAddressEdt.text.toString() != "" && projectCodeEdt.text.toString() != "") {
                createProject()
                //setupProgress()
            } else {
                Snackbar.make(view, "عنوان پروژه و آدرس و کد پروژه نمی تواند خالی باشد!", Snackbar.LENGTH_LONG).show()
            }
        }


        /**Show Case start**/

        //showCase font
        var textPaint = TextPaint(Paint.LINEAR_TEXT_FLAG)
        textPaint.setColor(Color.WHITE)
        textPaint.setTextSize(40f)
        textPaint.setTypeface(Typeface.createFromAsset(this.assets, "IRANSansWeb(FaNum).ttf"))

        var titleTextPaint = TextPaint(Paint.LINEAR_TEXT_FLAG)
        titleTextPaint.setColor(Color.parseColor("#1E88E5"))
        titleTextPaint.setTextSize(50f)
        titleTextPaint.setTypeface(Typeface.createFromAsset(this.assets, "IRANSansWeb(FaNum)_Medium.ttf"))

        var showCaseButton = Button(this)
        showCaseButton.background = resources.getDrawable(R.drawable.round_btn_primary)
        showCaseButton.setTextColor( Color.WHITE)
        showCaseButton.typeface = Typeface.createFromAsset(resources.assets,"IRANSansWeb(FaNum)_Light.ttf")


        var sharedPreferences: SharedPreferences = this.getSharedPreferences("userInformation", 0)
        if (sharedPreferences.getBoolean("guide2", true)) {


            showcaseView = ShowcaseView.Builder(this)
                    .setTarget(ViewTarget(R.id.createProjectTv, this))
                    .withMaterialShowcase()
                    .setStyle(R.style.CustomShowcaseTheme3)
                    .setContentTextPaint(textPaint)
                    .setContentTitlePaint(titleTextPaint)
                    .setContentTitle("ثبت اطلاعات")
                    .setContentText("برای ثبت اطلاعات قبل از خروج، آن را ذخیره نمایید.")
                    .hideOnTouchOutside()
                    .replaceEndButton(showCaseButton)
                    .build()

            showcaseView!!.setButtonText("باشه")


            showcaseView!!.overrideButtonClick {
                showcaseView!!.hide()
                showcaseView!!.removeAllViews()

            }


            var sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
            sharedPreferencesEditor.putBoolean("guide2", true)
            sharedPreferencesEditor.apply()
            sharedPreferencesEditor.commit()
        }

        //back click
        addProjectBackIcon.setOnClickListener {
            var intent = Intent(this@AddProjectActivity, MainActivity::class.java)
            startActivity(intent)
            this@AddProjectActivity.finish()
        }
    }

    private fun captureCamera() {
        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, 10)
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
                // progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا هنگام ورود اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<CreateProjectResponse>?, response: Response<CreateProjectResponse>?) {
                // progressDialog.dismiss()

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
                accountPicImg.setImageBitmap(selectedBitmap);
                accountPicImg.setScaleType(ImageView.ScaleType.FIT_XY);

                /* val uri = data.getData()

                 var fixedbitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                 accountPicImg.setImageBitmap(fixedbitmap);
*/
                if (selectedBitmap != null) {
                    //  val bytes = File(uri.toString()).readBytes()
                    var outputStream = ByteArrayOutputStream()
                    selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
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
            convertImage = ""
            accountPicImg.setImageDrawable(resources.getDrawable(R.drawable.skyline2))
        }

        if (resultCode != Activity.RESULT_CANCELED) {

        }

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


    override fun onBackPressed() {
        super.onBackPressed()

        var intent = Intent(this@AddProjectActivity, MainActivity::class.java)
        startActivity(intent)
        this@AddProjectActivity.finish()

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

}
