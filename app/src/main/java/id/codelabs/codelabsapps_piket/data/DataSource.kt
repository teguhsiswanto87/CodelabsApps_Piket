package id.codelabs.codelabsapps_piket.data

import id.codelabs.codelabsapps_piket.data.remote.RemoteDataSource
import id.codelabs.codelabsapps_piket.model.ModelItem
import id.codelabs.codelabsapps_piket.model.ResponseLogin

class DataSource {

    private val remoteDataSource = RemoteDataSource()

    interface LoginCallback {
        fun onSuccess(response: ResponseLogin)
        fun onFailure(message: String)
    }

    fun login(nim: String, pasword: String, callback: LoginCallback) {
        remoteDataSource.login(nim, pasword, object : LoginCallback {
            override fun onSuccess(response: ResponseLogin) {
                callback.onSuccess(response)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }
        })
    }

    interface CheckHasPasswordCallback {
        fun onSuccess(message: String)
        fun onFailure(message: String)
    }

    fun checkHasPassword(nim: String, callback: CheckHasPasswordCallback) {
        remoteDataSource.checkHasPassword(nim, object : CheckHasPasswordCallback {
            override fun onSuccess(message: String) {
                callback.onSuccess(message)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }
        })
    }

    interface AddPasswordCallback {
        fun onSuccess(message: String)
        fun onFailure(message: String)
    }

    fun addPassword(nim: String, pasword: String, callback: AddPasswordCallback) {
        remoteDataSource.addPassword(nim, pasword, object : AddPasswordCallback {
            override fun onSuccess(message: String) {
                callback.onSuccess(message)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }
        })
    }

    interface GetPiketCallback {
        fun onSuccess(list: List<ModelItem>)
        fun onFailure(message: String)
    }

    fun getPiket(date: String, callback: GetPiketCallback) {
        remoteDataSource.getPiket(date, object : GetPiketCallback {
            override fun onSuccess(list: List<ModelItem>) {
                callback.onSuccess(list)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }

        })
    }

    interface GetSudahPiketCallback {
        fun onSuccess(list: List<ModelItem>)
        fun onFailure(message: String)
    }

    fun getSudahPiket(date: String, callback: GetSudahPiketCallback) {
        remoteDataSource.getSudahPiket(date, object : GetSudahPiketCallback {
            override fun onSuccess(list: List<ModelItem>) {
                callback.onSuccess(list)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }

        })
    }

    interface GetBelumPiketCallback {
        fun onSuccess(list: List<ModelItem>)
        fun onFailure(message: String)
    }

    fun getBelumPiket(callback: GetBelumPiketCallback) {
        remoteDataSource.getBelumPiket(object : GetBelumPiketCallback {
            override fun onSuccess(list: List<ModelItem>) {
                callback.onSuccess(list)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }

        })
    }

    interface UpdateFCMTokenCallback {
        fun onSuccess()
        fun onFailure(message: String)
    }

    fun updateFCMToken(fcmToken: String, callback: UpdateFCMTokenCallback) {
        remoteDataSource.updateFCMToken(fcmToken, object : UpdateFCMTokenCallback {
            override fun onSuccess() {
                callback.onSuccess()
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }

        })
    }

    interface PermohonanSelesaiPiketCallback {
        fun onSuccess(data: ModelItem)
        fun onFailure(message: String)
    }

    fun permohonanSelesaiPiket(id: Int, callback: PermohonanSelesaiPiketCallback) {
        remoteDataSource.permohonanSelesaiPiket(id, object : PermohonanSelesaiPiketCallback {
            override fun onSuccess(data: ModelItem) {
                callback.onSuccess(data)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }

        })
    }

    interface InspeksiPiketCallback {
        fun onSuccess(data: ModelItem)
        fun onFailure(message: String)
    }

    fun inspeksiPiket(id: Int, callback: InspeksiPiketCallback) {
        remoteDataSource.inspeksiPiket(id, object : InspeksiPiketCallback {
            override fun onSuccess(data: ModelItem) {
                callback.onSuccess(data)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }

        })
    }


}