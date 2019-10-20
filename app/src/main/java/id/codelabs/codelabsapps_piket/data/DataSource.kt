package id.codelabs.codelabsapps_piket.data

import id.codelabs.codelabsapps_piket.data.remote.RemoteDataSource

class DataSource {
    
    private val remoteDataSource = RemoteDataSource() 

    interface LoginCallback {
        fun onSuccess(token : String)
        fun onFaillure(message : String)
    }
    
    fun login( nim : String, pasword : String, callback : LoginCallback){
        remoteDataSource.login(nim,pasword, object : LoginCallback{
            override fun onSuccess(token: String) {
                callback.onSuccess(token)
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