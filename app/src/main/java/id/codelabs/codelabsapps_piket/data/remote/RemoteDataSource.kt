package id.codelabs.codelabsapps_piket.data.remote

import id.codelabs.codelabsapps_piket.Utils
import id.codelabs.codelabsapps_piket.data.DataSource
import id.codelabs.codelabsapps_piket.model.*
import id.codelabs.codelabsapps_piket.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    private var dao: Dao = ApiClient.getClient().create(Dao::class.java)
    private val _200 = 200
    private val _404 = 404
    private val _401 = 401
    private val _400 = 400


    fun login(nim: String, password: String, callback: DataSource.LoginCallback) {

        try {
            val call: Call<ResponseLogin> = dao.login(nim, password)
            call.enqueue(object : Callback<ResponseLogin> {
                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                    callback.onFailure(t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseLogin>,
                    response: Response<ResponseLogin>
                ) {
                    when (response.code()) {
                        _200 -> callback.onSuccess(response.body()!!)
                        _401 -> callback.onFailure(LoginActivity.WRONG_PASSWORD)
                    }
                }
            })
        } catch (e: Exception) {
            callback.onFailure(e.toString())
        }
    }

    fun checkHasPassword(nim: String, callback: DataSource.CheckHasPasswordCallback) {

        try {
            val call: Call<ResponseCheckHasPassword> = dao.checkHasPassword(nim)
            call.enqueue((object : Callback<ResponseCheckHasPassword> {
                override fun onFailure(call: Call<ResponseCheckHasPassword>, t: Throwable) {
                    callback.onFailure(t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseCheckHasPassword>,
                    response: Response<ResponseCheckHasPassword>
                ) {
                    when (response.code()) {
                        _200 -> callback.onSuccess(LoginActivity.HAS_PASSWORD)
                        _401 -> callback.onSuccess(LoginActivity.YET_PASSWORD)
                        _404 -> callback.onSuccess(LoginActivity.NOT_FOUND_NIM)
                    }
                }
            }))
        } catch (e: Exception) {
            callback.onFailure(e.toString())
        }
    }

    fun addPassword(nim: String, password: String, callback: DataSource.AddPasswordCallback) {

        try {
            val call: Call<ResponseAddPassword> = dao.addPassword(nim, password)
            call.enqueue(object : Callback<ResponseAddPassword> {
                override fun onFailure(call: Call<ResponseAddPassword>, t: Throwable) {
                    callback.onFailure(t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseAddPassword>,
                    response: Response<ResponseAddPassword>
                ) {
                    when (response.code()) {
                        _200 -> callback.onSuccess(LoginActivity.PASSWORD_ADDED)
                        _401 -> callback.onFailure("401")
                        _404 -> callback.onFailure("404")
                    }

                }
            })
        } catch (e: Exception) {
            callback.onFailure(e.toString())
        }
    }

    fun getPiket(date: String, callback: DataSource.GetPiketCallback) {
        try {
            val call: Call<ResponseListPiket> =
                dao.getPiket(Utils.getSharedPreferences(Utils.SAVED_TOKEN)!!, date)
            call.enqueue(object : Callback<ResponseListPiket> {
                override fun onFailure(call: Call<ResponseListPiket>, t: Throwable) {
                    callback.onFailure(t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseListPiket>,
                    response: Response<ResponseListPiket>
                ) {
                    when (response.code()) {
                        _200 -> {
                            callback.onSuccess(response.body()!!.data)
                        }
                        _401 -> {
                            callback.onFailure("401")
                        }
                    }
                }

            })
        } catch (e: Exception) {
            callback.onFailure(e.toString())
        }
    }

    fun getSudahPiket(date: String, callback: DataSource.GetSudahPiketCallback) {
        try {
            val call: Call<ResponseListPiket> =
                dao.getSudahPiketTgl(Utils.getSharedPreferences(Utils.SAVED_TOKEN)!!, date)
            call.enqueue(object : Callback<ResponseListPiket> {
                override fun onFailure(call: Call<ResponseListPiket>, t: Throwable) {
                    callback.onFailure(t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseListPiket>,
                    response: Response<ResponseListPiket>
                ) {
                    when (response.code()) {
                        _200 -> {
                            callback.onSuccess(response.body()!!.data)
                        }
                        _401 -> {
                            callback.onFailure("401")
                        }
                    }
                }

            })
        } catch (e: Exception) {
            callback.onFailure(e.toString())
        }
    }

    fun getBelumPiket(callback: DataSource.GetBelumPiketCallback) {

        try {
            val call: Call<ResponseListPiket> =
                dao.getBelumPiket(Utils.getSharedPreferences(Utils.SAVED_TOKEN)!!)
            call.enqueue(object : Callback<ResponseListPiket> {
                override fun onFailure(call: Call<ResponseListPiket>, t: Throwable) {
                    callback.onFailure(t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseListPiket>,
                    response: Response<ResponseListPiket>
                ) {
                    when (response.code()) {
                        _200 -> {
                            callback.onSuccess(response.body()!!.data)
                        }
                        _401 -> {
                            callback.onFailure("401")
                        }
                    }
                }

            })
        } catch (e: Exception) {
            callback.onFailure(e.toString())
        }

    }

    fun updateFCMToken(fcmToken: String, callback: DataSource.UpdateFCMTokenCallback) {
        try {
            val call: Call<ResponseUpdateFCMToken> = dao.updateFCMToken(
                Utils.getSharedPreferences(Utils.SAVED_TOKEN)!!,
                fcmToken
            )
            call.enqueue(object : Callback<ResponseUpdateFCMToken> {
                override fun onFailure(call: Call<ResponseUpdateFCMToken>, t: Throwable) {
                    callback.onFailure(t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseUpdateFCMToken>,
                    response: Response<ResponseUpdateFCMToken>
                ) {
                    when (response.code()) {
                        _200 -> {
                            callback.onSuccess()
                        }
                        _400 -> {
                            callback.onFailure("400")
                        }
                        _401 -> {
                            callback.onFailure("401")
                        }
                    }
                }

            })
        } catch (e: Exception) {
            callback.onFailure(e.toString())
        }
    }

    fun permohonanSelesaiPiket(id: Int, callback: DataSource.PermohonanSelesaiPiketCallback) {
        try {
            val call: Call<ResponsePermohonanSelesaiPiket> = dao.permohonanSelesaiPiket(
                Utils.getSharedPreferences(Utils.SAVED_TOKEN)!!,
                id
            )
            call.enqueue(object : Callback<ResponsePermohonanSelesaiPiket> {
                override fun onFailure(call: Call<ResponsePermohonanSelesaiPiket>, t: Throwable) {
                    callback.onFailure(t.toString())
                }

                override fun onResponse(
                    call: Call<ResponsePermohonanSelesaiPiket>,
                    response: Response<ResponsePermohonanSelesaiPiket>
                ) {
                    when (response.code()) {
                        _200 -> {
                            callback.onSuccess(response.body()!!.data)
                        }
                        _401 -> {
                            callback.onFailure("401")
                        }

                    }
                }

            })
        } catch (e: Exception) {
            callback.onFailure(e.toString())
        }
    }

    fun inspeksiPiket(id: Int, callback: DataSource.InspeksiPiketCallback) {
        try {
            val call: Call<ResponseInspeksiPiket> = dao.inspeksiPiket(
                Utils.getSharedPreferences(Utils.SAVED_TOKEN)!!,
                id
            )
            call.enqueue(object : Callback<ResponseInspeksiPiket> {
                override fun onFailure(call: Call<ResponseInspeksiPiket>, t: Throwable) {
                    callback.onFailure(t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseInspeksiPiket>,
                    response: Response<ResponseInspeksiPiket>
                ) {
                    when (response.code()) {
                        _200 -> {
                            callback.onSuccess(response.body()!!.data)
                        }
                        _401 -> {
                            callback.onFailure("401")
                        }

                    }
                }

            })
        } catch (e: Exception) {
            callback.onFailure(e.toString())
        }
    }

}