package com.linkerpad.linkerpad.Fragments

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getSystemService
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import com.linkerpad.linkerpad.Business.IUserApi
import com.linkerpad.linkerpad.Business.IUserLogic
import com.linkerpad.linkerpad.Business.IWebApi
import com.linkerpad.linkerpad.Data.RegisterResponse
import com.linkerpad.linkerpad.Data.UsersData
import com.linkerpad.linkerpad.R
import kotlinx.android.synthetic.main.register_fragment_layout.*
import kotlinx.android.synthetic.main.register_fragment_layout.view.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import javax.security.auth.callback.Callback


/**
 * Created by alihajiloo on 7/30/18.
 */
class RegisterFragment : Fragment() {

    private var userLogic: IUserLogic? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        /* userLogic=UserLogic(IUserApi.Factory.create());
         userLogic!!.Register(UsersData("","","","","",""))*/
        var view: View = inflater.inflate(R.layout.register_fragment_layout, container, false)

      /*  val service:IUserApi = IWebApi.Factory.create()*/

        val retrofit = retrofit2.Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://31.184.132.134/")
                .build()

        var service:IUserApi = retrofit.create(IUserApi::class.java)

        view.registerBtn.setOnClickListener {
            if (!nameEdt.text.contains(" ")) {
                Snackbar.make(view, "نام خانوادگی را وارد کنید!", Snackbar.LENGTH_LONG).show()
            } else {
                /*var iUserApi: IUserApi? = null
                iUserApi = IWebApi.apiService
                iUserApi!!.register(UsersData("Ali", "hajiloo", "linkerpad", "989123832387", "email@gmail.com", "1234567"))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe ({
                            result ->
                            Toast.makeText(context, "There are ${result.toString()} Java developers in Lagos", Toast.LENGTH_SHORT).show()
                        }, { error ->
                            error.printStackTrace()
                        })*/

                var userData = UsersData("Ali", "hajiloo", "linkerpad", "989133832387", "emwr3ail@gmail.com", "123454367")
                /* userData.FirstName ="ali"
                 userData.LastName = "hashemi"
                 userData.EmailAddress = "al234i@gmail.com"
                 userData.Company= "Linkerpad323"
                 userData.MobileNumber = "989149879899"
                 userData.Password = "flia332f03"*/


                var userDataHashed = HashMap<String, String>()
                userDataHashed.put("FirstName", "hossein")
                userDataHashed.put("LastName", "Hosseinjaafl")
                userDataHashed.put("Company", "Hossein3")
                userDataHashed.put("MobileNumber", "989123456780")
                userDataHashed.put("EmailAddress", "Hos6se3ein@gmail.com")
                userDataHashed.put("Password", "Hosd23sein")


                try {

                } catch (e: JSONException) {

                }
                var jsonObject: JSONObject = JSONObject()
                jsonObject.put("FirstName", "hossein")
                jsonObject.put("LastName", "Hosinjaafl")
                jsonObject.put("Company", "Hossein3")
                jsonObject.put("MobileNumber", "989123456780")
                jsonObject.put("EmailAddress", "Hos87sein@gmail.com")
                jsonObject.put("Password", "Hosd23sein")


                //  val call = service.register("info@gmaill.com" , "kljlksdf")
                // val call = service.register(userData)
                 val call = service.register("Ali", "haeiloo", "elin3kerpad", "989123981288", "alijaj34iloo@gmail.com", "123Jhg_987654")
                //val call = service.register(userDataHashed)
                try {
                    call.enqueue(object : retrofit2.Callback<RegisterResponse> {
                        override fun onFailure(call: Call<RegisterResponse>?, t: Throwable?) {
                            Toast.makeText(context, "error: ${t!!.message}", Toast.LENGTH_SHORT).show()
                            Toast.makeText(context, "error: ${t!!.message}", Toast.LENGTH_SHORT).show()
                            Toast.makeText(context, "error: ${t!!.message}", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<RegisterResponse>?, response: Response<RegisterResponse>?) {

                            Toast.makeText(context, "body(token): ${response!!.body().toString()} -" +
                                    " message: ${response.message()} - raw: ${response.raw()} -" +
                                    " code:${response.code()} -header:  - error:${response.errorBody().toString()} - " +
                                    "success:${response.isSuccessful}", Toast.LENGTH_LONG).show()
                            Toast.makeText(context, "body(token): ${response!!.body().toString()} -" +
                                    " message: ${response.message()} - raw: ${response.raw()} -" +
                                    " code:${response.code()} -header:  - error:${response.errorBody().toString()} - " +
                                    "success:${response.isSuccessful}", Toast.LENGTH_LONG).show()
                            Toast.makeText(context, "body(token): ${response!!.body().toString()} -" +
                                    " message: ${response.message()} - raw: ${response.raw()} -" +
                                    " code:${response.code()} -header:  - error:${response.errorBody().toString()} - " +
                                    "success:${response.isSuccessful}", Toast.LENGTH_LONG).show()

                        }
                    })
                } catch (e: Exception) {
                   Toast.makeText(context ,"${e.printStackTrace().toString()}",Toast.LENGTH_LONG ).show()
                    Toast.makeText(context ,"${e.printStackTrace().toString()}",Toast.LENGTH_LONG ).show()
                    Toast.makeText(context ,"${e.printStackTrace().toString()}",Toast.LENGTH_LONG ).show()
                }


            }
        }


        view.nameEdt.setOnClickListener {  var layoutParam : LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , 1000)
            view.registerSpace.layoutParams=layoutParam }

        /*if (view != null) {
            val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm.isAcceptingText){
                var layoutParam : LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , 1000)
                view.registerSpace.layoutParams=layoutParam
            }else{
                var layoutParam : LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , 30)
               view.registerSpace.layoutParams=layoutParam
            }

        }*/

        return view
    }

}