package id.codelabs.codelabsapps_piket.data

import id.codelabs.codelabsapps_piket.data.remote.RemoteDataSource
import id.codelabs.codelabsapps_piket.model.ResponseLogin

class DataSource {
    
    private val remoteDataSource = RemoteDataSource() 

    interface LoginCallback {
        fun onSuccess(response : ResponseLogin)
        fun onFaillure(message : String)
    }
    
    fun login( nim : String, pasword : String, callback : LoginCallback){
        remoteDataSource.login(nim,pasword, object : LoginCallback{
            override fun onSuccess(response : ResponseLogin) {
                callback.onSuccess(response)
            }

            override fun onFaillure(message: String) {
                callback.onFaillure(message)
            }
        } )
    }

    interface CheckHasPasswordCallback{
        fun onSuccess(message: String)
        fun onFailure(message: String)
    }

    fun checkHasPassword(nim : String, callback: CheckHasPasswordCallback){
        remoteDataSource.checkHasPassword(nim, object : CheckHasPasswordCallback{
            override fun onSuccess(message: String) {
                callback.onSuccess(message)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }
        })
    }

    interface AddPasswordCallback {
        fun onSuccess(message : String)
        fun onFaillure(message : String)
    }

    fun addPassword( nim : String, pasword : String, callback : AddPasswordCallback){
        remoteDataSource.addPassword(nim,pasword, object : AddPasswordCallback{
            override fun onSuccess(message: String) {
                callback.onSuccess(message)
            }

            override fun onFaillure(message: String) {
                callback.onFaillure(message)
            }
        } )
    }

}