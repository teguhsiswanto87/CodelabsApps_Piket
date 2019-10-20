package id.codelabs.codelabsapps_piket.data.remote

import id.codelabs.codelabsapps_piket.model.ResponseAddPassword
import id.codelabs.codelabsapps_piket.model.ResponseCheckPassword
import id.codelabs.codelabsapps_piket.model.ResponseLogin
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Dao{

    @POST("mobile/login")
    @FormUrlEncoded
    fun login(@Field("nim")nim : String , @Field("password") password : String ) : Call<ResponseLogin>

    @POST("mobile/login/check-password")
    @FormUrlEncoded
    fun checkHasPassword(@Field("nim")nim : String  ) : Call<ResponseCheckPassword>

    @POST("mobile/login/add-password")
    @FormUrlEncoded
    fun addPassword(@Field("nim")nim : String,@Field("password") password : String  ) : Call<ResponseAddPassword>


}