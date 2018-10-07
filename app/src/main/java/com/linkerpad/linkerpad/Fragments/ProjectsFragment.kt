package com.linkerpad.linkerpad.Fragments

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ViewTarget
import com.linkerpad.linkerpad.Adapters.ProjectsListAdapter
import com.linkerpad.linkerpad.AddProjectActivity
import com.linkerpad.linkerpad.ApiData.output.ProjectListResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.ProjectInformationData
import com.linkerpad.linkerpad.R
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.projects_fragmant_layout.view.*
import retrofit2.Call
import retrofit2.Response
import android.graphics.Typeface
import android.text.TextPaint
import android.view.MotionEvent
import android.widget.Button
import android.widget.Toast
import com.github.amlcurran.showcaseview.OnShowcaseEventListener


/**
 * Created by alihajiloo on 7/31/18.
 */

class ProjectsFragment : Fragment(), OnShowcaseEventListener {

    override fun onShowcaseViewShow(showcaseView: ShowcaseView?) {

    }

    override fun onShowcaseViewHide(showcaseView: ShowcaseView?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onShowcaseViewDidHide(showcaseView: ShowcaseView?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onShowcaseViewTouchBlocked(motionEvent: MotionEvent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    lateinit var progressDialog: ProgressDialog
    lateinit var showCase: ShowcaseView

    var showcaseView: ShowcaseView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view: View = inflater.inflate(R.layout.projects_fragmant_layout, container, false)

        /*  view.cardTestClick.setOnClickListener {
              var intent = Intent(context, ProjectHolderActivity::class.java)
              startActivity(intent)
          }*/

        //on swipe to refresh
        view.projectsRefreshLayout.setColorSchemeColors(Color.parseColor("#1E88E5"))
        view.projectsRefreshLayout.setOnRefreshListener {
            // Handler().postDelayed(Runnable { view.projectsRefreshLayout.setRefreshing(false) }, 2000)
            //  setupProgress()
            getProjectListUpdate()
            view.projectsRefreshLayout.isRefreshing = false

        }

        //  setupProgress()
        getProjectList()




        activity!!.refreshBtnImv.setOnClickListener {
            //  setupProgress()
            getProjectListUpdate()

            view.projectsRefreshLayout.isRefreshing = false
        }


        //on add button clicked
        view.addProjectFab.setOnClickListener {

            var intent = Intent(context, AddProjectActivity::class.java)
            startActivity(intent)
            activity!!.finish()
        }


        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var textPaint = TextPaint(Paint.LINEAR_TEXT_FLAG)
        textPaint.setColor(Color.WHITE)
        textPaint.setTextSize(40f)
        textPaint.setTypeface(Typeface.createFromAsset(activity!!.assets, "IRANSansWeb(FaNum).ttf"))

        var titleTextPaint = TextPaint(Paint.LINEAR_TEXT_FLAG)
        titleTextPaint.setColor(Color.parseColor("#1E88E5"))
        titleTextPaint.setTextSize(50f)
        titleTextPaint.setTypeface(Typeface.createFromAsset(activity!!.assets, "IRANSansWeb(FaNum)_Medium.ttf"))

        var showCaseButton = Button(activity)
        showCaseButton.background = resources.getDrawable(R.drawable.round_btn_primary)
        showCaseButton.setTextColor(Color.WHITE)
        showCaseButton.typeface = Typeface.createFromAsset(resources.assets, "IRANSansWeb(FaNum)_Light.ttf")

        var sharedPreferences: SharedPreferences = context!!.getSharedPreferences("userInformation", 0)
        if (sharedPreferences.getBoolean("guide1", true)) {

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

            showcaseView = ShowcaseView.Builder(this.activity)
                    .setTarget(ViewTarget(R.id.addProjectFab, activity))
                    .withMaterialShowcase()
                    .setStyle(R.style.CustomShowcaseTheme3)
                    .setContentTextPaint(textPaint)
                    .setContentTitlePaint(titleTextPaint)
                    .setContentTitle("پروژه جدید")
                    .setContentText("برای شروع، پروژه جدیدی را اضافه و مشخصات آن را وارد نمایید.")
                    .replaceEndButton(showCaseButton)
                    .build()

            showcaseView!!.setButtonText("بعدی")


            showcaseView!!.overrideButtonClick {
                showcaseView!!.hide()
                showcaseView!!.removeAllViews()

                ShowcaseView.Builder(activity)
                        .setTarget(ViewTarget(R.id.refreshBtnImv, activity))
                        .withMaterialShowcase()
                        .setStyle(R.style.CustomShowcaseTheme3)
                        .setContentTextPaint(textPaint)
                        .setContentTitlePaint(titleTextPaint)
                        .setContentTitle("بروز رسانی")
                        .setContentText("جهت بروز رسانی لیست پروژه ها، این آیکون را لمس نموده یا صفحه را به سمت پایین بکشید.")
                        .replaceEndButton(showCaseButton)
                        .build().setButtonText("باشه")
            }


            var sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
            sharedPreferencesEditor.putBoolean("guide1", true)
            sharedPreferencesEditor.apply()
            sharedPreferencesEditor.commit()
        }
    }

    private fun getProjectList() {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getProjectList(getToken())

        call.enqueue(object : retrofit2.Callback<ProjectListResponse> {
            override fun onFailure(call: Call<ProjectListResponse>?, t: Throwable?) {
                //   progressDialog.dismiss()
                Snackbar.make(this@ProjectsFragment.view!!, "خطا هنگام دریافت اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ProjectListResponse>?, response: Response<ProjectListResponse>?) {

                //   progressDialog.dismiss()

                if (response!!.code() == 200) {
                    var projectListResponse = response.body()

                    var projectlist = ArrayList<ProjectInformationData>()
                    projectlist = projectListResponse!!.responseObject

                    view!!.projectsRecyclerView.layoutManager = LinearLayoutManager(activity)
                    view!!.projectsRecyclerView.adapter = ProjectsListAdapter(activity!!, projectlist)
                }
            }

        })
    }

    private fun getProjectListUpdate() {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getProjectList(getToken())

        call.enqueue(object : retrofit2.Callback<ProjectListResponse> {
            override fun onFailure(call: Call<ProjectListResponse>?, t: Throwable?) {
                //   progressDialog.dismiss()
                Snackbar.make(this@ProjectsFragment.view!!, "خطا هنگام دریافت اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ProjectListResponse>?, response: Response<ProjectListResponse>?) {

                //   progressDialog.dismiss()

                if (response!!.code() == 200) {
                    var projectListResponse = response.body()

                    var projectlist = ArrayList<ProjectInformationData>()
                    projectlist = projectListResponse!!.responseObject

                    view!!.projectsRecyclerView.layoutManager = LinearLayoutManager(activity)
                    view!!.projectsRecyclerView.adapter = ProjectsListAdapter(activity!!, projectlist)

                    Toast.makeText(context, "بروزرسانی انجام شد", Toast.LENGTH_LONG).show()

                }
            }

        })
    }

    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = activity!!.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
    }


}