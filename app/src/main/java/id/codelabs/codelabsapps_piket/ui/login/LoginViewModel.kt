package id.codelabs.codelabsapps_piket.ui.login

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import id.codelabs.codelabsapps_piket.Utils
import id.codelabs.codelabsapps_piket.data.DataSource
import id.codelabs.codelabsapps_piket.model.ResponseLogin
import okhttp3.internal.Util

class LoginViewModel : ViewModel(){

    val NIM_STATE = "NIM_STATE"
    val PASS_STATE ="PASS_STATE"
    val CREATE_PASS_STATE = "CREATE_PASS_STATE"

    lateinit var loginActivity : LoginActivity

    private lateinit var  token : String
    var state = NIM_STATE
    var nim = ""
    var password = ""

    val DataSource = DataSource()

    fun checkHasPassword(callback : DataSource.CheckHasPasswordCallback){
        DataSource.checkHasPassword(nim, object : DataSource.CheckHasPasswordCallback{
            override fun onSuccess(message: String) {
                callback.onSuccess(message)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }
        })

    }

    fun login(callback : DataSource.LoginCallback){
        DataSource.login(nim,password,object : DataSource.LoginCallback {
            override fun onSuccess(response : ResponseLogin) {
                Utils.makeSharedPreferences(loginActivity as Activity)
                Log.d("dasdasdasda","dasdsad")
                Utils.putSharedPreferences("hoho","fg")
                Log.d("cdasadasd",Utils.getSharedPreferences("hoho")!!)
                Log.d("dsadsad",nim)
                Log.d("dsadsad",password)
                Log.d("dsadsad",response.token)

                Utils.putSharedPreferences(Utils.SAVED_PASSWORD,"gf")
                Log.d(
                    "dsadasf",
                    Utils.getSharedPreferences(Utils.getSharedPreferences(Utils.SAVED_PASSWORD)!!)!!)

                Utils.putSharedPreferences(Utils.SAVED_PASSWORD,"gd")
                Log.d(
                    "dsadasf",
                    Utils.getSharedPreferences(Utils.getSharedPreferences(Utils.SAVED_PASSWORD)!!)!!)

                Utils.putSharedPreferences(Utils.SAVED_TOKEN,response.token)
                Log.d(
                    "dsadasf",
                    Utils.getSharedPreferences(Utils.getSharedPreferences(Utils.SAVED_TOKEN)!!)!!)
                callback.onSuccess(response)
            }

            override fun onFaillure(message: String) {
                callback.onFaillure(message)
            }

        })
    }

    fun addPassword(callback : DataSource.LoginCallback){
        DataSource.addPassword(nim,password,object : DataSource.AddPasswordCallback{
            override fun onSuccess(message: String) {
                login(callback)
            }

            override fun onFaillure(message: String) {
                callback.onFaillure(message)
            }

        })
    }
}