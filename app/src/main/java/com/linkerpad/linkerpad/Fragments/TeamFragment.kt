package com.linkerpad.linkerpad.Fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.linkerpad.linkerpad.R
import kotlinx.android.synthetic.main.team_items.*
import kotlinx.android.synthetic.main.team_items.view.*
import kotlinx.android.synthetic.main.team_layout.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class TeamFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.team_fragment_layout, container, false)
        var i: Int = 0
        view.cardTestClick.setOnClickListener {
            if (i == 0) {
                showViews()
                i = 1
            } else {
                clearViews()
                i = 0
            }
        }

        view.callTeamImv.setOnClickListener {

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
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




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

    fun showViews() {
        var llParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        firstTeamLL.layoutParams = llParams

        /** company TextView **/
        val companyTeamTv: TextView = TextView(context)
        var companyTvParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
        companyTeamTv.layoutParams = companyTvParams
        companyTeamTv.setText("شرکت")
        companyTeamTv.setPadding(0, 0, 20, 0)
        companyTeamTv.setTextSize(16f)
        companyTeamTv.setTypeface(Typeface.createFromAsset(activity!!.assets, "IRANSansWeb(FaNum).ttf"))
        firstTeamLL.addView(companyTeamTv)

        /** access level TextView **/
        val accessLevelTv: TextView = TextView(context)
        accessLevelTv.setText("سطح دسترسی")
        accessLevelTv.setPadding(15, 10, 15, 10)
        accessLevelTv.setTextSize(16f)
        accessLevelTv.gravity = Gravity.CENTER
        accessLevelTv.setTextColor(resources.getColor(R.color.white))
        accessLevelTv.background = resources.getDrawable(R.drawable.rounded_back_gray)
        accessLevelTv.setTypeface(Typeface.createFromAsset(activity!!.assets, "IRANSansWeb(FaNum).ttf"))
        firstTeamLL.addView(accessLevelTv)

        /** first LinearLayout Views added 👆 **/

        secondTeamLL.layoutParams = llParams

        /** reseption TextView **/
        val reseptionTv: TextView = TextView(context)
        var reseptionTvParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
        reseptionTv.layoutParams = reseptionTvParams
        reseptionTv.setText("مسئولیت")
        reseptionTv.setPadding(0, 0, 20, 0)
        reseptionTv.setTextSize(16f)
        reseptionTv.setTypeface(Typeface.createFromAsset(activity!!.assets, "IRANSansWeb(FaNum).ttf"))
        secondTeamLL.addView(reseptionTv)

        /** phone number TextView **/
        val phoneNumberTv: TextView = TextView(context)
        phoneNumberTv.setText("09123456789")
        phoneNumberTv.setPadding(15, 0, 0, 0)
        phoneNumberTv.setTextSize(16f)
        phoneNumberTv.setTypeface(Typeface.createFromAsset(activity!!.assets, "IRANSansWeb(FaNum).ttf"))
        secondTeamLL.addView(phoneNumberTv)

        /** Email TextView **/
        emailTeamTv.layoutParams = llParams
        emailTeamTv.setText("email@info.com")
        emailTeamTv.setPadding(25, 0, 0, 10)


    }

    fun clearViews() {
        var llParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
        firstTeamLL.layoutParams = llParams
        firstTeamLL.removeAllViews()

        secondTeamLL.layoutParams = llParams
        secondTeamLL.removeAllViews()

        emailTeamTv.layoutParams = llParams
    }


}
