package id.codelabs.codelabsapps_piket.data.remote

import id.codelabs.codelabsapps_piket.data.DataSource
import id.codelabs.codelabsapps_piket.model.ResponseAddPassword
import id.codelabs.codelabsapps_piket.model.ResponseCheckPassword
import id.codelabs.codelabsapps_piket.model.ResponseLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource{

    private var dao : Dao = ApiClient.getClient().create(Dao::class.java)
    private val OK = 200


    fun login(nim : String, password : String, callback : DataSource.LoginCallback){
        var call : Call<ResponseLogin> =  dao.login(nim,password)
        call.enqueue( object : Callback<ResponseLogin>{
            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                callback.onFaillure("gagal")
            }
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.body()!!.status == OK){
                    callback.onSuccess(response.body()!!.token)
                } else callback.onFaillure(response.body()!!.message)
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
                if (response.body()!!.status == OK){
                    callback.onSuccess(response.body()!!.message)
                } else callback.onFailure(response.body()!!.message)
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
                if (response.body()!!.status == OK){
                    callback.onSuccess(response.body()!!.message)
                } else callback.onFaillure(response.body()!!.message)
            }
        })
    }

}