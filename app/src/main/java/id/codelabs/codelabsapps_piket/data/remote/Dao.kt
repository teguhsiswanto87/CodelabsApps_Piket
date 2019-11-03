package id.codelabs.codelabsapps_piket.data.remote

import id.codelabs.codelabsapps_piket.model.*
import retrofit2.Call
import retrofit2.http.*

interface Dao {

    @FormUrlEncoded
    @POST("mobile/login")
    fun login(@Field("nim") nim: String, @Field("password") password: String): Call<ResponseLogin>

    @FormUrlEncoded
    @POST("mobile/login/check-password")
    fun checkHasPassword(@Field("nim") nim: String): Call<ResponseCheckHasPassword>

    @FormUrlEncoded
    @POST("mobile/login/add-password")
    fun addPassword(@Field("nim") nim: String, @Field("password") password: String): Call<ResponseAddPassword>

    @GET("mobile/cari-piket")
    fun getPiket(@Header("token") token: String, @Query("tanggal") tanggal: String): Call<ResponseListPiket>

    @GET("mobile/sudah-piket-hari-ini")
    fun getSudahPiketTgl(@Header("token") token: String, @Query("tanggal") tanggal: String): Call<ResponseListPiket>

    @GET("mobile/belum-piket")
    fun getBelumPiket(@Header("token") token: String): Call<ResponseListPiket>

    @FormUrlEncoded
    @POST("mobile/update-fcm")
    fun updateFCMToken(@Header("token") token: String, @Field("fcm_token") fcmToken: String): Call<ResponseUpdateFCMToken>

    @FormUrlEncoded
    @POST("mobile/selesai-piket")
    fun permohonanSelesaiPiket(@Header("token") token: String, @Field("id") id: Int): Call<ResponsePermohonanSelesaiPiket>

    @FormUrlEncoded
    @POST("mobile/inspeksi-piket")
    fun inspeksiPiket(@Header("token") token: String, @Field("id") id: Int): Call<ResponseInspeksiPiket>

}