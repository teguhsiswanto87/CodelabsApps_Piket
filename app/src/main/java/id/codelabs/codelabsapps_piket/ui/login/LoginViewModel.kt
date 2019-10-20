package id.codelabs.codelabsapps_piket.ui.login

import androidx.lifecycle.ViewModel
import id.codelabs.codelabsapps_piket.data.DataSource

class LoginViewModel : ViewModel(){

    val NIM_STATE = "NIM_STATE"
    val PASS_STATE ="PASS_STATE"
    val CREATE_PASS_STATE = "CREATE_PASS_STATE"

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
            override fun onSuccess(token: String) {
                callback.onSuccess("success")
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