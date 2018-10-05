package com.linkerpad.linkerpad

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.text.TextPaint
import android.widget.Button
import android.widget.Toast
import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ViewTarget
import com.linkerpad.linkerpad.Adapters.MaterialAdapter
import com.linkerpad.linkerpad.ApiData.output.MaterialListResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.MaterialInformationData
import kotlinx.android.synthetic.main.materials_item.*
import kotlinx.android.synthetic.main.materials_layout.*
import retrofit2.Call
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class MaterialsActivity : AppCompatActivity() {

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.materials_layout)

        setSupportActionBar(toolbar)

        var projectId = intent.getStringExtra("projectId")
        var reportDate = getIntent().getStringExtra("reportDate")

        // setupProgress()
        getMaterialList(projectId, reportDate)

        //Add Materials
        materialsActivityFab.setOnClickListener {
            var intent = Intent(this@MaterialsActivity, AddMaterialsActivity::class.java)
            intent.putExtra("projectId", projectId)
            intent.putExtra("reportDate", reportDate)
            startActivity(intent)
            this@MaterialsActivity.finish()
        }

        //refreshing
        materialRefresh.setColorSchemeColors(Color.parseColor("#1E88E5"))
        materialRefresh.setOnRefreshListener {

            getMaterialListUpdate(projectId, reportDate)
            materialRefresh.isRefreshing = false

        }

        //refreshing
        refreshBtnImv.setOnClickListener {

            getMaterialListUpdate(projectId, reportDate)

        }


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
        if (sharedPreferences.getBoolean("guide6", true)) {

            //showCase font


            /*    val target = ViewTarget(R.id.addProjectFab, activity)
                showcaseView = ShowcaseView.Builder(this.activity)
                        .setTarget(target)
                        .withMaterialShowcase()
                        .setContentTitlePaint(textPaint)
                        .setContentTextPaint(textPaint)
                        .setContentText("برای شروع، پروژه جدیدی را اضافه و مشخصات آن را وارد نمایید.")
                        .setStyle(R.style.CustomShowcaseTheme3)
                        .build()

                showcaseView!!.setButtonText("بعدی")*/

            var viewTarget: ViewTarget = ViewTarget(R.id.materialsActivityFab, this)

            ShowcaseView.Builder(this)
                    .setTarget(viewTarget)
                    .withMaterialShowcase()
                    .setStyle(R.style.CustomShowcaseTheme3)
                    .setContentTextPaint(textPaint)
                    .setContentTitlePaint(titleTextPaint)
                    .setContentTitle("ثبت آیتم های جدید")
                    .setContentText("برای ثبت، جزئیات هر آیتم را وارد نمایید. \n لیست کل آیتم های روزانه، در این صفحه دیده می شود اما هر فرد می تواند آیتم های خود را ویرایش یا حذف نماید.\n(افراد با سطح دسترسی «مسئول» یا «مدیر»، مجاز به ویرایش یا حذف تمامی آیتم ها می باشند)")
                    .hideOnTouchOutside()
                    .replaceEndButton(showCaseButton)
                    .build().setButtonText("باشه")

            var sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
            sharedPreferencesEditor.putBoolean("guide6", true)
            sharedPreferencesEditor.apply()
            sharedPreferencesEditor.commit()

        }

        //back clicked
        materialsBackIcon.setOnClickListener { this@MaterialsActivity.finish() }
    }

    private fun getMaterialList(projectId: String, reportDate: String = "2020-02-02") {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getProjectMaterialList(getToken(), projectId, reportDate)

        call.enqueue(object : retrofit2.Callback<MaterialListResponse> {
            override fun onFailure(call: Call<MaterialListResponse>?, t: Throwable?) {
                //  progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<MaterialListResponse>?, response: Response<MaterialListResponse>?) {

                // progressDialog.dismiss()

                var materialListResponse = response!!.body()

                var materialList = ArrayList<MaterialInformationData>()
                materialList = materialListResponse!!.responseObject

                materialRecyclerView.layoutManager = LinearLayoutManager(this@MaterialsActivity)
                materialRecyclerView.adapter = MaterialAdapter(this@MaterialsActivity, materialList, projectId)

            }

        })

    }

    private fun getMaterialListUpdate(projectId: String, reportDate: String = "2020-02-02") {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getProjectMaterialList(getToken(), projectId, reportDate)

        call.enqueue(object : retrofit2.Callback<MaterialListResponse> {
            override fun onFailure(call: Call<MaterialListResponse>?, t: Throwable?) {
                //  progressDialog.dismiss()
                Snackbar.make(findViewById(R.id.dummy_layout_for_snackbar), "خطا، اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<MaterialListResponse>?, response: Response<MaterialListResponse>?) {

                // progressDialog.dismiss()

                Toast.makeText(this@MaterialsActivity, "بروزرسانی انجام شد", Toast.LENGTH_LONG).show()

                var materialListResponse = response!!.body()

                var materialList = ArrayList<MaterialInformationData>()
                materialList = materialListResponse!!.responseObject

                materialRecyclerView.layoutManager = LinearLayoutManager(this@MaterialsActivity)
                materialRecyclerView.adapter = MaterialAdapter(this@MaterialsActivity, materialList, projectId)

            }

        })

    }

    private fun setupProgress() {
        progressDialog = ProgressDialog(this@MaterialsActivity)
        progressDialog.setMessage("لطفا شکیبا باشید")
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
        progressDialog.show()
    }

    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = this@MaterialsActivity.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
