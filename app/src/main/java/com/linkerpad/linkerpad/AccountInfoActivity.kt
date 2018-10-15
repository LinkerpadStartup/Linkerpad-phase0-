package com.linkerpad.linkerpad

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import butterknife.BindView
import com.linkerpad.linkerpad.ApiData.output.EditUserResponse
import com.linkerpad.linkerpad.ApiData.output.GetUserInformationResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.DateType
import com.linkerpad.linkerpad.Data.UserInformationOutputData
import com.linkerpad.linkerpad.Models.UserInformationViewModel
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.mobsandgeeks.saripaar.annotation.Pattern
import kotlinx.android.synthetic.main.account_info_layout.*
import kotlinx.android.synthetic.main.choose_imgae_layout.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class AccountInfoActivity : AppCompatActivity(), Validator.ValidationListener {

    @NotEmpty
    @BindView(R.id.nameEdt)
    lateinit var nameEdt: EditText

    @NotEmpty
    @BindView(R.id.lastNameEdt)
    lateinit var lastNameEdt: EditText

    @NotEmpty
    @Email
    @BindView(R.id.emailEdt)
    lateinit var emailEdt: EditText

    @NotEmpty
    @Pattern(regex = "^[9][0-9]{9}$")
    @BindView(R.id.phoneEdt)
    lateinit var phoneEdt: EditText

    @BindView(R.id.companyEdt)
    lateinit var companyEdt: EditText

    lateinit var progressDialog: ProgressDialog

    private val SELECT_IMAGE: Int = 9
    private val CORPIMAGECODE: Int = 34
    private var convertImage: String = ""
    private var convertedImage: Boolean = false
    var projectPicture: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_info_layout)
        setSupportActionBar(toolbar)

        //Initialize views
        Initialize()

        //get user information and set to EditTexts
        getUserInformation()

        //save button
        var validator: Validator = Validator(this@AccountInfoActivity)
        validator.setValidationListener(this@AccountInfoActivity)
        saveAccountInformationTv.setOnClickListener {
            validator.validate()
        }

        //back click
        accountInfoBackIcon.setOnClickListener {
            /*var intent = Intent(this@AccountInfoActivity, MainActivity::class.java)
            startActivity(intent)*/
            this@AccountInfoActivity.finish()
        }

        accountPicFab.setOnClickListener {


            var layoutInflater: LayoutInflater = this@AccountInfoActivity.layoutInflater
            var view: View = layoutInflater.inflate(R.layout.choose_imgae_layout, null)
            var dialog: AlertDialog.Builder = AlertDialog.Builder(this@AccountInfoActivity, R.style.AlertDialogTheme)
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
                    ActivityCompat.requestPermissions(this@AccountInfoActivity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
                    dialog2.dismiss()
                } else {
                    galleryIntent()
                    dialog2.dismiss()
                }
            }
            view.cameraChooseImageTv.setOnClickListener {
                //  Toast.makeText(this@AddProjectActivity , "camera",Toast.LENGTH_LONG).show()

                if ((ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) || (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(this@AccountInfoActivity, arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 2)

                    dialog2.dismiss()
                } else {
                    captureCamera()
                    dialog2.dismiss()
                }

            }
        }

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
        //  convertedImage = true

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


            } else if (requestCode == 34) {


                var extras: Bundle = data!!.extras
                //    var selectedBitmap: Bitmap = extras.getParcelable("data")
                var selectedBitmap: Bitmap = extras.getParcelable("data") as Bitmap
                //  var selectedBitmap: Bitmap =  data!!.extras.get("data") as Bitmap
                // Set The Bitmap Data To ImageView
                accountPicImg.setImageBitmap(selectedBitmap);
                accountPicImg.setScaleType(ImageView.ScaleType.FIT_XY);


                if (selectedBitmap != null) {

                    var outputStream = ByteArrayOutputStream()
                    selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
                    convertImage = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
                    convertedImage = true
                }

            } else {


            }
        } else {
            if (projectPicture == "" || projectPicture == null) {
                convertImage = ""
                accountPicImg.setImageDrawable(resources.getDrawable(R.drawable.noun_user))
            }
        }
        if (resultCode != Activity.RESULT_CANCELED) {

        }

    }

    fun galleryIntent() {
        val i = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(i, SELECT_IMAGE)
    }

    private fun captureCamera() {
        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, 10)
    }

    private fun editUserInformation(firstName: String, lastName: String, company: String, mobileNumber: String, skill: String) {

        if (convertedImage) {
            projectPicture = convertImage
        }

        var service: IUserApi = IWebApi.Factory.create()
        var call = service.editUserInformation(getToken(), UserInformationViewModel.setUserEditedInformation(
                firstName = firstName,
                lastName = lastName,
                company = company,
                mobileNumber = mobileNumber,
                profilePicture = projectPicture,
                skill = skill
        ))

        call.enqueue(object : Callback<EditUserResponse> {
            override fun onFailure(call: Call<EditUserResponse>?, t: Throwable?) {
                //  progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, اتصال اینترنت خود را بررسی کنید.", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<EditUserResponse>?, response: Response<EditUserResponse>?) {

                //  progressDialog.dismiss()

                if (response!!.code() == 200) {
                    // getUserInformation()
                    Toast.makeText(this@AccountInfoActivity, "اطلاعات با موفقیت ویرایش شد.", Toast.LENGTH_LONG).show()
                    /* var intent = Intent(this@AccountInfoActivity, MainActivity::class.java)
                     startActivity(intent)*/

                    var sharedPreferences: SharedPreferences = this@AccountInfoActivity.getSharedPreferences("userInformation", 0)
                    var sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
                    sharedPreferencesEditor.putString("firstName", firstName)
                    sharedPreferencesEditor.putString("lastName", lastName)
                    sharedPreferencesEditor.apply()
                    sharedPreferencesEditor.commit()

                    val returnIntent = Intent()
                    setResult(Activity.RESULT_OK, returnIntent)


                    this@AccountInfoActivity.finish()


                } else {
                    Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا در ویرایش اطلاعات.", Snackbar.LENGTH_LONG).show()

                }
            }

        })

    }

    private fun Initialize() {
        nameEdt = findViewById(R.id.nameEdt) as EditText
        lastNameEdt = findViewById(R.id.lastNameEdt) as EditText
        emailEdt = findViewById(R.id.emailEdt) as EditText
        companyEdt = findViewById(R.id.companyEdt) as EditText
        phoneEdt = findViewById(R.id.phoneEdt) as EditText


    }

    //get login token
    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@AccountInfoActivity.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
    }

    //get user Information API
    private fun getUserInformation() {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getUserInformation(getToken())

        call.enqueue(object : Callback<GetUserInformationResponse> {
            override fun onFailure(call: Call<GetUserInformationResponse>?, t: Throwable?) {

                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا, اتصال اینترنت خود را بررسی کنید.", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<GetUserInformationResponse>?, response: Response<GetUserInformationResponse>?) {
                if (response!!.code() == 200) {
                    var userInformation: GetUserInformationResponse? = response!!.body()

                    var userInformationOutput = UserInformationViewModel.getUserInformation(UserInformationOutputData(userInformation!!.status,
                            userInformation.message, userInformation.responseObject))

                    nameEdt.setText("${userInformationOutput.firstName}")
                    lastNameEdt.setText("${userInformationOutput.lastName}")
                    emailEdt.setText("${userInformationOutput.emailAddress}")
                    companyEdt.setText("${userInformationOutput.company}")
                    phoneEdt.setText("${userInformationOutput.mobileNumber.substring(2)}")
                    if (userInformationOutput.skill == null || userInformationOutput.skill.equals("") || userInformationOutput.skill.equals("null")) {
                        skillEdt.setText("بدون تخصص")
                    } else {
                        skillEdt.setText("${userInformationOutput.skill}")
                    }

                    if (userInformationOutput.profilePicture != "" && userInformationOutput.profilePicture != null) {

                        val b = Base64.decode(userInformationOutput.profilePicture, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(b, 0, b.size)
                        //  var profileBitmap:Bitmap = BitmapFactory.decodeByteArray(Base64.getDecoder().decode(itemModel.projectPicture), 0, Base64.getDecoder().decode(itemModel.projectPicture).size)
                        accountPicImg.setImageDrawable(BitmapDrawable(resources, bitmap))
                        projectPicture = userInformationOutput.profilePicture
                    }
                }
            }

        })
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        for (error: ValidationError in errors!!) {
            val view: View = error.view
            val message = error.getCollatedErrorMessage(this@AccountInfoActivity)

            if (view == nameEdt) {
                nameEdt.error = "اجباری"
            } else if (view == lastNameEdt) {
                lastNameEdt.error = "نام خانوادگی اجباری"
            } else if (view == emailEdt) {
                if (emailEdt.text.toString() == "")
                    emailEdt.error = "اجباری"
                else
                    emailEdt.error = "فرمت نادرست"
            } else if (view == phoneEdt) {
                phoneEdt.error = "فرمت نادرست"
            } else if (view == companyEdt) {

            }
        }


    }

    override fun onValidationSucceeded() {
        //  setupProgress()

        editUserInformation(nameEdt.text.toString(), lastNameEdt.text.toString(), companyEdt.text.toString(), "98" + phoneEdt.text.toString(), skillEdt.text.toString())
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


}
