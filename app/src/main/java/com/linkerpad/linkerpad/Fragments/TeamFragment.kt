package com.linkerpad.linkerpad.Fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextPaint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ViewTarget
import com.linkerpad.linkerpad.Adapters.MembersListAdapter
import com.linkerpad.linkerpad.AddMemberActivity
import com.linkerpad.linkerpad.ApiData.output.MemberListResponse
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.MemberInformationData
import com.linkerpad.linkerpad.R
import kotlinx.android.synthetic.main.project_holder_content.*
import kotlinx.android.synthetic.main.team_fragment_layout.view.*
import kotlinx.android.synthetic.main.team_items.*
import kotlinx.android.synthetic.main.team_items.view.*
import kotlinx.android.synthetic.main.team_layout.*
import retrofit2.Call
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class TeamFragment : Fragment() {
    lateinit var progressDialog: ProgressDialog

    var showcaseView: ShowcaseView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.team_fragment_layout, container, false)


        /* view.callTeamImv.setOnClickListener {

             if (activity!!.checkCallingOrSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                 var phoneIntent: Intent = Intent(Intent.ACTION_CALL)
                 phoneIntent.setData(Uri.parse("tel:09120981288"))
                 startActivity(phoneIntent)
             } else {
                 ActivityCompat.requestPermissions(activity!!.parent, arrayOf(Manifest.permission.CALL_PHONE), 1)
             }
         }

         view.emailTeamImv.setOnClickListener {
             var emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                     "mailto", "abc@gmail.com", null))

             startActivity(Intent.createChooser(emailIntent, "ارسال ایمیل با ..."))
         }

 */


        if (ActivityCompat.checkSelfPermission(context!!, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.CALL_PHONE), 1)
        } else {

        }
        // setupProgress()
        var projetcId = activity!!.intent.getStringExtra("id")
        getMemberList(projetcId)

        //add member to project activity
        view.addMemberToProjectFab.setOnClickListener {

            var intent = Intent(context, AddMemberActivity::class.java)
            intent.putExtra("id", activity!!.intent.getStringExtra("id"))
            intent.putExtra("userRole", activity!!.intent.getIntExtra("userRole", 3))
            context!!.startActivity(intent)
        }

        view.swipeTeam.setColorSchemeColors(Color.parseColor("#1E88E5"))
        view.swipeTeam.setOnRefreshListener {
            if (ActivityCompat.checkSelfPermission(context!!, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.CALL_PHONE), 1)
            } else {

            }

            view.swipeTeam.isRefreshing = false
            getMemberListUpdate(projetcId)

        }

        activity!!.editProjectInformationImv.setImageDrawable(resources.getDrawable(R.drawable.ic_autorenew))
        activity!!.editProjectInformationImv.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(context!!, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.CALL_PHONE), 1)
            } else {

            }
            getMemberListUpdate(projetcId)
        }
        return view
    }

/*    private fun setupProgress() {
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("لطفا شکیبا باشید")
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        progressDialog.setIndeterminateDrawable(resources.getDrawable(R.drawable.progress_dialog))
        progressDialog.show()
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        showCaseButton.setTextColor( Color.WHITE)
        showCaseButton.typeface = Typeface.createFromAsset(resources.assets,"IRANSansWeb(FaNum)_Light.ttf")

        var sharedPreferences: SharedPreferences = context!!.getSharedPreferences("userInformation", 0)
        if (sharedPreferences.getBoolean("guide9", true)) {

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
                    .setTarget(ViewTarget(R.id.addMemberToProjectFab, activity))
                    .withMaterialShowcase()
                    .setStyle(R.style.CustomShowcaseTheme3)
                    .setContentTextPaint(textPaint)
                    .setContentTitlePaint(titleTextPaint)
                    .setContentTitle("افزودن فرد به تیم پروژه")
                    .setContentText("برای این کار ، آیکون را لمس و سطح دسترسی فرد را تعیین نمایید.\n(افراد با سطح دسترسی «مسئول» یا «مدیر»، مجاز به انجام این کار هستند.)")
                    .hideOnTouchOutside()
                    .replaceEndButton(showCaseButton)
                    .build()

            showcaseView!!.setButtonText("بعدی")


            showcaseView!!.overrideButtonClick {
                showcaseView!!.hide()
                showcaseView!!.removeAllViews()

                ShowcaseView.Builder(activity)
                        .setTarget(ViewTarget(R.id.showCaseForMoreOption, activity))
                        .withMaterialShowcase()
                        .setStyle(R.style.CustomShowcaseTheme3)
                        .setContentTextPaint(textPaint)
                        .setContentTitlePaint(titleTextPaint)
                        .setContentTitle("مشخصات افراد")
                        .setContentText("برای نمایش جزئیات بیشتر و دسترسی به ویرایش یا حذف فرد از تیم پروژه، این آیکون را لمس نمایید.\n(افراد با سطح دسترسی «مسئول» یا «مدیر»، مجاز به انجام این کار هستند.)")
                        .hideOnTouchOutside()
                        .replaceEndButton(showCaseButton)
                        .build().setButtonText("باشه")
            }


            var sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
            sharedPreferencesEditor.putBoolean("guide9", true)
            sharedPreferencesEditor.apply()
            sharedPreferencesEditor.commit()
        }
    }

    private fun getMemberList(projectId: String) {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getProjectMemberList(getToken(), projectId)

        call.enqueue(object : retrofit2.Callback<MemberListResponse> {
            override fun onFailure(call: Call<MemberListResponse>?, t: Throwable?) {
                //  progressDialog.dismiss()
                Snackbar.make(this@TeamFragment.view!!, "خطا هنگام ورود اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<MemberListResponse>?, response: Response<MemberListResponse>?) {

                // progressDialog.dismiss()

                var membersResponse = response!!.body()

                var memberList = ArrayList<MemberInformationData>()
                memberList = membersResponse!!.responseObject

                view!!.membersListRecyclerView.layoutManager = LinearLayoutManager(activity)
                view!!.membersListRecyclerView.adapter = MembersListAdapter(activity!!.applicationContext, memberList, projectId)

            }

        })
    }


    private fun getMemberListUpdate(projectId: String) {
        var service: IUserApi = IWebApi.Factory.create()
        var call = service.getProjectMemberList(getToken(), projectId)

        call.enqueue(object : retrofit2.Callback<MemberListResponse> {
            override fun onFailure(call: Call<MemberListResponse>?, t: Throwable?) {
                //  progressDialog.dismiss()
                Snackbar.make(this@TeamFragment.view!!, "خطا هنگام ورود اتصال اینترنت خود را بررسی کنید!", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<MemberListResponse>?, response: Response<MemberListResponse>?) {

                // progressDialog.dismiss()

                var membersResponse = response!!.body()

                var memberList = ArrayList<MemberInformationData>()
                memberList = membersResponse!!.responseObject

                view!!.membersListRecyclerView.layoutManager = LinearLayoutManager(activity)
                view!!.membersListRecyclerView.adapter = MembersListAdapter(activity!!.applicationContext, memberList, projectId)

                Toast.makeText(context, "بروزرسانی انجام شد", Toast.LENGTH_LONG).show()

            }

        })
    }


    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    var phoneIntent: Intent = Intent(Intent.ACTION_CALL)
                    phoneIntent.setData(Uri.parse("tel:09120981288"))
                    startActivity(phoneIntent)
                }

                return
            }
        }
    }

    private fun getToken(): String {
        var sharedPreferences: SharedPreferences = activity!!.getSharedPreferences("userInformation", 0)
        var token = sharedPreferences.getString("token", null)
        return token
    }


}
