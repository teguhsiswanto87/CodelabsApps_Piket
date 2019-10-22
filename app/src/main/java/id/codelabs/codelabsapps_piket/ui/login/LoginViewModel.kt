package id.codelabs.codelabsapps_piket.ui.login

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import id.codelabs.codelabsapps_piket.Utils
import id.codelabs.codelabsapps_piket.data.DataSource
import id.codelabs.codelabsapps_piket.model.ResponseLogin

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
                token = response.data.token

                Log.d("devnkjsakdn",nim)
                Log.d("devnkjsakdn",password)
                Log.d("devnkjsakdn",token)

                Utils.makeSharedPreferences(loginActivity)

                Utils.putSharedPreferences(Utils.SAVED_NIM,nim)
                Utils.putSharedPreferences(Utils.SAVED_PASSWORD,password)
                Utils.putSharedPreferences(Utils.SAVED_TOKEN,token)

                Log.d("prefdasdsd",Utils.getSharedPreferences(Utils.SAVED_NIM)!!)
                Log.d("prefdasdsd",Utils.getSharedPreferences(Utils.SAVED_PASSWORD)!!)
                Log.d("prefdasdsd",Utils.getSharedPreferences(Utils.SAVED_TOKEN)!!)


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