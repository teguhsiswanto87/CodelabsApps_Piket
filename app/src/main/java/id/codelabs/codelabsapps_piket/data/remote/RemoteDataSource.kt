package id.codelabs.codelabsapps_piket.data.remote

import android.icu.text.DateTimePatternGenerator.PatternInfo.OK
import android.util.Log
import id.codelabs.codelabsapps_piket.data.DataSource
import id.codelabs.codelabsapps_piket.model.ResponseAddPassword
import id.codelabs.codelabsapps_piket.model.ResponseCheckPassword
import id.codelabs.codelabsapps_piket.model.ResponseLogin
import id.codelabs.codelabsapps_piket.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource{

    private var dao : Dao = ApiClient.getClient().create(Dao::class.java)
    private val _200 = 200
    private val _404 = 404
    private val _401 = 401


    fun login(nim : String, password : String, callback : DataSource.LoginCallback){
        var call : Call<ResponseLogin> =  dao.login(nim,password)
        call.enqueue( object : Callback<ResponseLogin>{
            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                callback.onFaillure("gagal")
            }
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                when(response.code()){
                    _200 -> callback.onSuccess(response.body()!!)
                    _404 -> callback.onFaillure(LoginActivity.WRONGPASSWORD)
                }
            }
        })
    }

    fun checkHasPassword(nim : String,callback : DataSource.CheckHasPasswordCallback){
        var call : Call<ResponseCheckPassword> = dao.checkHasPassword(nim)
        call.enqueue((object : Callback<ResponseCheckPassword>{
            override fun onFailure(call: Call<ResponseCheckPassword>, t: Throwable) {
                callback.onFailure("gagal")
            }

            override fun onResponse(call: Call<ResponseCheckPassword>,response: Response<ResponseCheckPassword>) {
                Log.i("adasdsadsa",response.code().toString())
                when (response.code()){
                    _200 -> callback.onSuccess(LoginActivity.HASPASSWORD)
                    _401 -> callback.onSuccess(LoginActivity.YETPASSWORD)
                    _404 -> callback.onSuccess(LoginActivity.NOTFOUNDNIM)
                }
            }
        }))
    }

    fun addPassword(nim : String, password : String, callback : DataSource.AddPasswordCallback){
        var call : Call<ResponseAddPassword> =  dao.addPassword(nim,password)
        call.enqueue( object : Callback<ResponseAddPassword>{
            override fun onFailure(call: Call<ResponseAddPassword>, t: Throwable) {
                callback.onFaillure("gagal")
            }
            override fun onResponse(call: Call<ResponseAddPassword>, response: Response<ResponseAddPassword>) {
                when(response.code()){
                    _200 -> callback.onSuccess(LoginActivity.PASSWORDADDED)
                    _401 -> callback.onFaillure("gagal")
                }

            }
        })
    }

}