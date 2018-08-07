package com.linkerpad.linkerpad

import android.content.Context
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.team_items.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class TeamActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.team_layout)

        var i: Int = 0

        cardTestClick.setOnClickListener {

            if (i == 0) {

                showViews()

                i = 1
            } else {

                clearViews()

                i = 0
            }
        }


    }

    fun showViews() {
        var llParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        firstTeamLL.layoutParams = llParams

        /** company TextView **/
        val companyTeamTv: TextView = TextView(this@TeamActivity)
        var companyTvParams:LinearLayout.LayoutParams = LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1f)
        companyTeamTv.layoutParams = companyTvParams
        companyTeamTv.setText("شرکت")
        companyTeamTv.setPadding(0,0,20,0)
        companyTeamTv.setTextSize(16f)
        companyTeamTv.setTypeface(Typeface.createFromAsset(assets,"IRANSansWeb(FaNum).ttf"))
        firstTeamLL.addView(companyTeamTv)

        /** access level TextView **/
        val accessLevelTv: TextView = TextView(this@TeamActivity)
        accessLevelTv.setText("سطح دسترسی")
        accessLevelTv.setPadding(15,10,15,10)
        accessLevelTv.setTextSize(16f)
        accessLevelTv.gravity = Gravity.CENTER
        accessLevelTv.setTextColor(resources.getColor(R.color.white))
        accessLevelTv.background = resources.getDrawable(R.drawable.rounded_back_gray)
        accessLevelTv.setTypeface(Typeface.createFromAsset(assets,"IRANSansWeb(FaNum).ttf"))
        firstTeamLL.addView(accessLevelTv)

        /** first LinearLayout Views added 👆 **/

        secondTeamLL.layoutParams = llParams

        /** reseption TextView **/
        val reseptionTv: TextView = TextView(this@TeamActivity)
        var reseptionTvParams:LinearLayout.LayoutParams = LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1f)
        reseptionTv.layoutParams = reseptionTvParams
        reseptionTv.setText("مسئولیت")
        reseptionTv.setPadding(0,0,20,0)
        reseptionTv.setTextSize(16f)
        reseptionTv.setTypeface(Typeface.createFromAsset(assets,"IRANSansWeb(FaNum).ttf"))
        secondTeamLL.addView(reseptionTv)

        /** phone number TextView **/
        val phoneNumberTv: TextView = TextView(this@TeamActivity)
        phoneNumberTv.setText("09123456789")
        phoneNumberTv.setPadding(15,0,0,0)
        phoneNumberTv.setTextSize(16f)
        phoneNumberTv.setTypeface(Typeface.createFromAsset(assets,"IRANSansWeb(FaNum).ttf"))
        secondTeamLL.addView(phoneNumberTv)

        /** Email TextView **/
        emailTeamTv.layoutParams = llParams
        emailTeamTv.setText("email@info.com")
        emailTeamTv.setPadding(25,0,0,10)


    }

    fun clearViews() {
        var llParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
        firstTeamLL.layoutParams = llParams
        firstTeamLL.removeAllViews()

        secondTeamLL.layoutParams = llParams
        secondTeamLL.removeAllViews()

        emailTeamTv.layoutParams = llParams
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
