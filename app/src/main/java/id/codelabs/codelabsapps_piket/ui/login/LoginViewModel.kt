package id.codelabs.codelabsapps_piket.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import id.codelabs.codelabsapps_piket.utils.Utils
import id.codelabs.codelabsapps_piket.data.DataSource
import id.codelabs.codelabsapps_piket.model.ResponseLogin

class LoginViewModel : ViewModel() {

    val NIM_STATE = "NIM_STATE"
    val PASS_STATE = "PASS_STATE"
    val CREATE_PASS_STATE = "CREATE_PASS_STATE"

    lateinit var loginActivity: LoginActivity

    private lateinit var token: String
    var state = NIM_STATE
    var nim = ""
    var password = ""

    val dataSource = DataSource()

    fun checkHasPassword(nim: String, callback: DataSource.CheckHasPasswordCallback) {
        this.nim = nim
        dataSource.checkHasPassword(nim, object : DataSource.CheckHasPasswordCallback {
            override fun onSuccess(message: String) {
                callback.onSuccess(message)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }
        })

    }

    fun login(password: String, callback: DataSource.LoginCallback) {
        this.password = password
        dataSource.login(nim, password, object : DataSource.LoginCallback {
            override fun onSuccess(response: ResponseLogin) {
                token = response.data.token

                Utils.makeSharedPreferences(loginActivity)

                Utils.putSharedPreferences(Utils.SAVED_NIM, nim)
                Utils.putSharedPreferences(Utils.SAVED_PASSWORD, password)
                Utils.putSharedPreferences(Utils.SAVED_TOKEN, token)

                Log.d("devbct pref : ", Utils.getSharedPreferences(
                    Utils.SAVED_NIM)!!)
                Log.d("devbct pref : ", Utils.getSharedPreferences(
                    Utils.SAVED_PASSWORD)!!)

                callback.onSuccess(response)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }

        })
    }

    fun addPassword(password: String, callback: DataSource.LoginCallback) {
        this.password = password
        dataSource.addPassword(nim, password, object : DataSource.AddPasswordCallback {
            override fun onSuccess(message: String) {
                login(password, callback)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }

        })
    }
}