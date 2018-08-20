package com.linkerpad.linkerpad.Fragments

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.linkerpad.linkerpad.Adapters.ProjectsListAdapter
import com.linkerpad.linkerpad.AddProjectActivity
import com.linkerpad.linkerpad.ApiData.output.ProjectListResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.ProjectInformationData
import com.linkerpad.linkerpad.Models.ProjectInformationViewModel
import com.linkerpad.linkerpad.ProjectHolderActivity
import com.linkerpad.linkerpad.R
import kotlinx.android.synthetic.main.projects_fragmant_layout.*
import kotlinx.android.synthetic.main.projects_fragmant_layout.view.*
import kotlinx.android.synthetic.main.projetcs_items.view.*
import retrofit2.Call
import retrofit2.Response


/**
 * Created by alihajiloo on 7/31/18.
 */

class ProjectsFragment : Fragment() {


    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
            setupProgress()
            getProjectList()
            view.projectsRefreshLayout.isRefreshing = false

        }

        setupProgress()
        getProjectList()

        //on add button clicked
        view.addProjectFab.setOnClickListener {
            var intent = Intent(context, AddProjectActivity::class.java)
            startActivity(intent)
        }

        return view

    }

    private fun getProjectList() {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getProjectList(getToken())

        call.enqueue(object : retrofit2.Callback<ProjectListResponse> {
            override fun onFailure(call: Call<ProjectListResponse>?, t: Throwable?) {
                progressDialog.dismiss()
                Snackbar.make(this@ProjectsFragment.view!!, "خطا هنگام ورود اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ProjectListResponse>?, response: Response<ProjectListResponse>?) {

                progressDialog.dismiss()

                var projectListResponse = response!!.body()

                var projectlist = ArrayList<ProjectInformationData>()
                projectlist = projectListResponse!!.responseObject

                view!!.projectsRecyclerView.layoutManager = LinearLayoutManager(activity)
                view!!.projectsRecyclerView.adapter = ProjectsListAdapter(activity!!.applicationContext, projectlist)

            }

        })
    }

    private fun setupProgress() {
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("لطفا شکیبا باشید")
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
        progressDialog.show()
    }

    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = activity!!.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
    }


}