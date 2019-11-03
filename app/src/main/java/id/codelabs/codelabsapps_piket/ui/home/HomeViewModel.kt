package id.codelabs.codelabsapps_piket.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import id.codelabs.codelabsapps_piket.utils.Utils
import id.codelabs.codelabsapps_piket.data.DataSource
import id.codelabs.codelabsapps_piket.model.ModelItem
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeViewModel : ViewModel() {
    var dataSource = DataSource()
    var piketList = ArrayList<ModelItem>()
    var sudahPiketList = ArrayList<ModelItem>()
    var belumPiketList = ArrayList<ModelItem>()
    var cal = Calendar.getInstance()
    @SuppressLint("SimpleDateFormat")
    val simpleDateFormat = SimpleDateFormat("YYYY-MM-dd")
    var currentDate = simpleDateFormat.format(cal.time)
    var selectedDate = ""
    var dateOnPiketList = ""
    lateinit var homeActivity : HomeActivity
    var loadingPiketListStatus = false
    var loadingSudahPiketListStatus = false


    fun getPiketList(date: String, callback: DataSource.GetPiketCallback) {
        selectedDate = date
        if (selectedDate != dateOnPiketList) {
            loadingPiketListStatus = true
            dataSource.getPiket(selectedDate, object : DataSource.GetPiketCallback {
                override fun onSuccess(list: List<ModelItem>) {
                    piketList.clear()
                    piketList.addAll(list)
                    dateOnPiketList = selectedDate
                    loadingPiketListStatus = false
                    callback.onSuccess(list)
                }

                override fun onFailure(message: String) {
                    loadingPiketListStatus = false
                    callback.onFailure(message)
                }

            })
        }else {
            callback.onSuccess(piketList)
        }

    }

    fun getSudahPiketList(date: String, callback: DataSource.GetSudahPiketCallback) {
        selectedDate = date
        if (selectedDate != dateOnPiketList) {
            loadingSudahPiketListStatus = true
            dataSource.getSudahPiket(selectedDate, object : DataSource.GetSudahPiketCallback {
                override fun onSuccess(list: List<ModelItem>) {
                    sudahPiketList.clear()
                    sudahPiketList.addAll(list)
                    dateOnPiketList = selectedDate
                    loadingSudahPiketListStatus = false
                    callback.onSuccess(list)
                }

                override fun onFailure(message: String) {
                    loadingSudahPiketListStatus = false
                    callback.onFailure(message)
                }

            })
        }else {
            callback.onSuccess(sudahPiketList)
        }

    }

    fun getBelumPiketList(callback: DataSource.GetBelumPiketCallback) {
        if(belumPiketList.isEmpty()){
            dataSource.getBelumPiket(object : DataSource.GetBelumPiketCallback {
                override fun onSuccess(list: List<ModelItem>) {
                    belumPiketList.clear()
                    belumPiketList.addAll(list)
                    callback.onSuccess(list)
                }
                override fun onFailure(message: String) {
                    callback.onFailure(message)
                }

            })
        }else{
            callback.onSuccess(belumPiketList)
        }

    }

    fun updateFCMToken(fcmToken:String , callback : DataSource.UpdateFCMTokenCallback){
        dataSource.updateFCMToken(fcmToken,object : DataSource.UpdateFCMTokenCallback{
            override fun onSuccess() {
                Utils.makeSharedPreferences(homeActivity)
                Utils.putSharedPreferences(Utils.SAVED_FCM_TOKEN,fcmToken)
                callback.onSuccess()
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }

        })
    }

    fun permohonanSelesaiPiket(id: Int, callback: DataSource.PermohonanSelesaiPiketCallback) {
        dataSource.permohonanSelesaiPiket(id, object :
            DataSource.PermohonanSelesaiPiketCallback {
            override fun onSuccess(data : ModelItem) {
                callback.onSuccess(data)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }

        })
    }

    fun inspeksiPiket(id: Int, callback: DataSource.InspeksiPiketCallback) {
        dataSource.inspeksiPiket(id, object : DataSource.InspeksiPiketCallback {
            override fun onSuccess(data : ModelItem) {
                callback.onSuccess(data)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }

        })
    }

}