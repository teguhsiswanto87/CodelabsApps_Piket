package id.codelabs.codelabsapps_piket.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import id.codelabs.codelabsapps_piket.R
import id.codelabs.codelabsapps_piket.data.DataSource
import id.codelabs.codelabsapps_piket.model.ModelItem
import id.codelabs.codelabsapps_piket.ui.home.customDatePicker.DatePickerAdapter
import id.codelabs.codelabsapps_piket.ui.home.customDatePicker.OnClickItemCustomDatePickerListener
import id.codelabs.codelabsapps_piket.utils.AlertDialogResultListener
import id.codelabs.codelabsapps_piket.utils.Utils
import kotlinx.android.synthetic.main.activity_home.*
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity(), OnClickItemCustomDatePickerListener,
    PiketAdapter.OnClickButtonItemPiketListListener,
    SudahPiketAdapter.OnClickButtonItemSudahPiketListListener {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var belumPiketAdapter: BelumPiketAdapter
    private lateinit var piketAdapter: PiketAdapter
    private lateinit var sudahPiketAdapter: SudahPiketAdapter
    private lateinit var datePickerAdapter: DatePickerAdapter

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Utils.makeSharedPreferences(this)

        srl_home.setOnRefreshListener {
            onRefresh()
        }

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel.homeActivity = this

        if (homeViewModel.currentDate.isEmpty()) {
            val currentDate = SimpleDateFormat("YYYY-MM-dd", Locale.getDefault()).format(Date())
            homeViewModel.currentDate = currentDate
        }

        val strBuilder = StringBuilder()
        strBuilder.append(resources.getString(R.string.belum_piket))
        strBuilder.append(" - ")
        strBuilder.append(SimpleDateFormat("MMMM", Locale.ENGLISH).format(homeViewModel.cal.time))
        tv_title_rv_belum_piket.text = strBuilder.toString()

        perpareDatePicker(homeViewModel.cal, 5)
        preparePiketList()
        prepareSudahPiketList()
        prepareBelumPiketList()

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("devbct fcmToken", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result?.token

                if (token!!.isNotEmpty()) {
                    homeViewModel.updateFCMToken(token, updateFCMTokenCallback)
                    Log.d("devbct fcmToken", token)
                }
            })

    }

    private fun perpareDatePicker(calendar: Calendar, numberOfWeek: Int) {
        datePickerAdapter = DatePickerAdapter(this, calendar, this, numberOfWeek, rv_date_picker)
    }

    private fun preparePiketList() {
        piketAdapter = PiketAdapter(this@HomeActivity)
        rv_piket.layoutManager = LinearLayoutManager(this)
        rv_piket.setHasFixedSize(true)
        rv_piket.adapter = piketAdapter
        getItemPiketList(homeViewModel.currentDate)
    }

    private fun getItemPiketList(date: String) {
        datePickerAdapter.isClickable = false
        srl_home.isRefreshing = true
        tv_information_piket_list.visibility = View.INVISIBLE
        homeViewModel.getPiketList(date, getPiketCallback)
    }

    private val getPiketCallback = object : DataSource.GetPiketCallback {
        override fun onSuccess(list: List<ModelItem>) {
            piketAdapter.list.clear()

            if (list.isNotEmpty()) {
                piketAdapter.list = list as ArrayList<ModelItem>
            } else {
                tv_information_piket_list.setText(R.string.empty)
                tv_information_piket_list.visibility = View.VISIBLE
            }
            rv_piket.adapter = piketAdapter
            piketAdapter.notifyDataSetChanged()

            if (!homeViewModel.loadingPiketListStatus && !homeViewModel.loadingSudahPiketListStatus && !homeViewModel.loadingBelumPiketStatus) {
                datePickerAdapter.isClickable = true
                srl_home.isRefreshing = false
            }
        }

        override fun onFailure(message: String) {
            piketAdapter.list.clear()
            rv_piket.adapter = piketAdapter
            piketAdapter.notifyDataSetChanged()
            tv_information_piket_list.setText(R.string.failed)
            tv_information_piket_list.visibility = View.VISIBLE

            if (!homeViewModel.loadingPiketListStatus && !homeViewModel.loadingSudahPiketListStatus && !homeViewModel.loadingBelumPiketStatus) {
                datePickerAdapter.isClickable = true
                srl_home.isRefreshing = false
            }
        }
    }

    private fun prepareSudahPiketList() {
        sudahPiketAdapter = SudahPiketAdapter(this@HomeActivity)
        rv_sudah_piket.layoutManager = LinearLayoutManager(this)
        rv_sudah_piket.setHasFixedSize(true)
        rv_sudah_piket.adapter = sudahPiketAdapter
        getItemSudahPiketList(homeViewModel.currentDate)
    }

    private fun getItemSudahPiketList(date: String) {
        datePickerAdapter.isClickable = false
        tv_information_sudah_piket_list.visibility = View.INVISIBLE
        homeViewModel.getSudahPiketList(date, getSudahPiketCallback)
    }

    private val getSudahPiketCallback = object : DataSource.GetSudahPiketCallback {
        override fun onSuccess(list: List<ModelItem>) {
            sudahPiketAdapter.list.clear()

            if (list.isNotEmpty()) {
                sudahPiketAdapter.list.addAll(list)
            } else {
                tv_information_sudah_piket_list.setText(R.string.empty)
                tv_information_sudah_piket_list.visibility = View.VISIBLE
            }
            rv_sudah_piket.adapter = sudahPiketAdapter
            sudahPiketAdapter.notifyDataSetChanged()

            if (!homeViewModel.loadingPiketListStatus && !homeViewModel.loadingSudahPiketListStatus && !homeViewModel.loadingBelumPiketStatus) {
                datePickerAdapter.isClickable = true
                srl_home.isRefreshing = false
            }
        }

        override fun onFailure(message: String) {
            sudahPiketAdapter.list.clear()
            rv_sudah_piket.adapter = sudahPiketAdapter
            sudahPiketAdapter.notifyDataSetChanged()
            tv_information_sudah_piket_list.setText(R.string.failed)
            tv_information_sudah_piket_list.visibility = View.VISIBLE

            if (!homeViewModel.loadingPiketListStatus && !homeViewModel.loadingSudahPiketListStatus && !homeViewModel.loadingBelumPiketStatus) {
                datePickerAdapter.isClickable = true
                srl_home.isRefreshing = false
            }
        }
    }

    private fun prepareBelumPiketList() {
        belumPiketAdapter = BelumPiketAdapter()
        rv_belum_piket.layoutManager = LinearLayoutManager(this)
        rv_belum_piket.setHasFixedSize(true)
        rv_belum_piket.adapter = belumPiketAdapter
        getItemBelumPiketList()
    }

    private fun getItemBelumPiketList() {
        tv_information_belum_piket_list.visibility = View.INVISIBLE
        homeViewModel.getBelumPiketList(getBelumPiketCallback)

    }

    private val getBelumPiketCallback = object : DataSource.GetBelumPiketCallback {
        override fun onSuccess(list: List<ModelItem>) {
            belumPiketAdapter.list.clear()
            if (list.isNotEmpty()) {
                belumPiketAdapter.list.addAll(list)
            } else {
                tv_information_belum_piket_list.setText(R.string.empty)
                tv_information_belum_piket_list.visibility = View.VISIBLE
            }
            rv_belum_piket.adapter = belumPiketAdapter
            belumPiketAdapter.notifyDataSetChanged()
            if (!homeViewModel.loadingPiketListStatus && !homeViewModel.loadingSudahPiketListStatus && !homeViewModel.loadingBelumPiketStatus) {
                datePickerAdapter.isClickable = true
                srl_home.isRefreshing = false
            }
        }

        override fun onFailure(message: String) {
            rv_belum_piket.adapter = belumPiketAdapter
            belumPiketAdapter.notifyDataSetChanged()
            belumPiketAdapter.list.clear()
            tv_information_belum_piket_list.setText(R.string.failed)
            tv_information_belum_piket_list.visibility = View.VISIBLE

            if (!homeViewModel.loadingPiketListStatus && !homeViewModel.loadingSudahPiketListStatus && !homeViewModel.loadingBelumPiketStatus) {
                datePickerAdapter.isClickable = true
                srl_home.isRefreshing = false
            }
        }
    }


    @SuppressLint("SimpleDateFormat")
    override fun onClickItemCustomDatePicker(date: String) {
        homeViewModel.dateOnPiketList = ""
        homeViewModel.piketList.clear()
        getItemPiketList(date)
        homeViewModel.sudahPiketList.clear()
        getItemSudahPiketList(homeViewModel.selectedDate)
        getItemBelumPiketList()
        val strBuilder = StringBuilder()
        strBuilder.append(
            SimpleDateFormat("MMM", Locale.ENGLISH)
                .format(SimpleDateFormat("yyyy-MM-dd").parse(date)!!)
        )
        strBuilder.append(", ")

        val date1 = date.split("-")
        val date2 = homeViewModel.currentDate.split("-")


        if ((date1[0].toInt() == date2[0].toInt())
            && (date1[1].toInt() == date2[1].toInt())
            && (date1[2].toInt() == date2[2].toInt())
        ) {
            strBuilder.clear()
            strBuilder.append("Hari Ini")
        } else {
            strBuilder.append(date.split("-")[2])
        }

        tv_full_selected_date.text = strBuilder.toString()
    }

    override fun onNotClickableClickCDP() {
        Utils.showToast(this, "Mohon tunggu...", 500)
    }

    private fun onRefresh() {
        homeViewModel.dateOnPiketList = ""
        homeViewModel.piketList.clear()
        getItemPiketList(homeViewModel.selectedDate)
        homeViewModel.sudahPiketList.clear()
        getItemSudahPiketList(homeViewModel.selectedDate)
        homeViewModel.belumPiketList.clear()
        getItemBelumPiketList()
    }


    private val updateFCMTokenCallback = object : DataSource.UpdateFCMTokenCallback {
        override fun onSuccess() {
            Log.d("devbct fcmToken", "success update fcm token")
        }

        override fun onFailure(message: String) {
            Log.d("devbct fcmToken", "failed update fcm token")
            Log.d("devbct fcmToken", message)
        }

    }

    override fun onClickButtonSelesaiItemPiket(
        member: ModelItem,
        loadingView: ImageView,
        btn: Button
    ) {
        Utils.showAlertDialog(
            this,
            "Are you sure?",
            "Yes",
            "No",
            object : AlertDialogResultListener {
                override fun onPositiveResult() {
                    btn.text = ""
                    btn.isClickable = false
                    loadingView.visibility = View.VISIBLE
                    homeViewModel.permohonanSelesaiPiket(member.id, object :
                        DataSource.PermohonanSelesaiPiketCallback {
                        override fun onSuccess(data: ModelItem) {
                            onRefresh()
                        }

                        override fun onFailure(message: String) {
                            loadingView.visibility = View.INVISIBLE
                            btn.isClickable = true
                            Utils.showToast(this@HomeActivity, "Gagal", 800)
                            btn.text = resources.getString(R.string.selesai)
                        }
                    })
                }

                override fun onNegativeResult() {
                }
            })
    }


    override fun onClickButtonSelesaiItemSudahPiket(
        member: ModelItem,
        loadingView: ImageView,
        btn: Button,
        checklist: ImageView
    ) {
        Utils.showAlertDialog(
            this,
            "Are you sure?",
            "Yes",
            "No",
            object : AlertDialogResultListener {
                override fun onPositiveResult() {
                    btn.text = ""
                    btn.isClickable = false
                    loadingView.visibility = View.VISIBLE
                    homeViewModel.inspeksiPiket(
                        member.id,
                        object : DataSource.InspeksiPiketCallback {
                            override fun onSuccess(data: ModelItem) {
                                loadingView.visibility = View.GONE
                                btn.visibility = View.GONE
                                checklist.visibility = View.VISIBLE
                            }

                            override fun onFailure(message: String) {
                                loadingView.visibility = View.INVISIBLE
                                btn.isClickable = true
                                Utils.showToast(this@HomeActivity, "Gagal", 800)
                                btn.text = resources.getString(R.string.selesai)
                            }
                        })
                }

                override fun onNegativeResult() {
                }
            }
        )
    }

}
