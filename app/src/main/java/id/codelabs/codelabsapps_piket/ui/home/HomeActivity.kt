package id.codelabs.codelabsapps_piket.ui.home

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.iid.FirebaseInstanceId
import id.codelabs.codelabsapps_piket.R
import id.codelabs.codelabsapps_piket.utils.AlertDialogResultListener
import id.codelabs.codelabsapps_piket.utils.Utils
import id.codelabs.codelabsapps_piket.data.DataSource
import id.codelabs.codelabsapps_piket.model.ModelItem
import id.codelabs.codelabsapps_piket.ui.home.customDatePicker.DatePickerAdapter
import id.codelabs.codelabsapps_piket.ui.home.customDatePicker.OnClickItemCustomDatePickerListener
import kotlinx.android.synthetic.main.activity_home.*
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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

        Glide.with(this)
            .load(R.drawable.loading_green)
            .into(iv_loading_rv_piket)
        Glide.with(this)
            .load(R.drawable.loading_green)
            .into(iv_loading_rv_sudah_piket)
        Glide.with(this)
            .load(R.drawable.loading_green)
            .into(iv_loading_rv_belum_piket)

        srl_home.setOnRefreshListener {
            srl_home.isRefreshing = false
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


        val fcmToken = FirebaseInstanceId.getInstance().token
        if (fcmToken!!.isNotEmpty()) {
            homeViewModel.updateFCMToken(fcmToken, updateFCMTokenCallback)
            Log.d("{devbacot}fcmToken : ", fcmToken)
        }


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
        iv_loading_rv_piket.visibility = View.VISIBLE
        tv_information_piket_list.visibility = View.INVISIBLE
        homeViewModel.getPiketList(date, getPiketCallback)
    }

    private val getPiketCallback = object : DataSource.GetPiketCallback {
        override fun onSuccess(list: List<ModelItem>) {
            iv_loading_rv_piket.visibility = View.GONE
            piketAdapter.list.clear()

            if (list.isNotEmpty()) {
                piketAdapter.list = list as ArrayList<ModelItem>
            } else {
                tv_information_piket_list.setText(R.string.empty)
                tv_information_piket_list.visibility = View.VISIBLE
            }
            rv_piket.adapter = piketAdapter
            piketAdapter.notifyDataSetChanged()

            if (!homeViewModel.loadingPiketListStatus && !homeViewModel.loadingSudahPiketListStatus) {
                datePickerAdapter.isClickable = true
            }
        }

        override fun onFailure(message: String) {
            iv_loading_rv_piket.visibility = View.GONE
            piketAdapter.list.clear()
            rv_piket.adapter = piketAdapter
            piketAdapter.notifyDataSetChanged()
            tv_information_piket_list.setText(R.string.failed)
            tv_information_piket_list.visibility = View.VISIBLE

            if (!homeViewModel.loadingPiketListStatus && !homeViewModel.loadingSudahPiketListStatus) {
                datePickerAdapter.isClickable = true
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
        iv_loading_rv_sudah_piket.visibility = View.VISIBLE
        tv_information_sudah_piket_list.visibility = View.INVISIBLE
        homeViewModel.getSudahPiketList(date, getSudahPiketCallback)
    }

    private val getSudahPiketCallback = object : DataSource.GetSudahPiketCallback {
        override fun onSuccess(list: List<ModelItem>) {
            iv_loading_rv_sudah_piket.visibility = View.GONE
            sudahPiketAdapter.list.clear()

            if (list.isNotEmpty()) {
                sudahPiketAdapter.list.addAll(list)
            } else {
                tv_information_sudah_piket_list.setText(R.string.empty)
                tv_information_sudah_piket_list.visibility = View.VISIBLE
            }
            rv_sudah_piket.adapter = sudahPiketAdapter
            sudahPiketAdapter.notifyDataSetChanged()

            if (!homeViewModel.loadingPiketListStatus && !homeViewModel.loadingSudahPiketListStatus) {
                datePickerAdapter.isClickable = true
            }
        }

        override fun onFailure(message: String) {
            iv_loading_rv_sudah_piket.visibility = View.GONE
            sudahPiketAdapter.list.clear()
            rv_sudah_piket.adapter = sudahPiketAdapter
            sudahPiketAdapter.notifyDataSetChanged()
            tv_information_sudah_piket_list.setText(R.string.failed)
            tv_information_sudah_piket_list.visibility = View.VISIBLE

            if (!homeViewModel.loadingPiketListStatus && !homeViewModel.loadingSudahPiketListStatus) {
                datePickerAdapter.isClickable = true
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
        iv_loading_rv_belum_piket.visibility = View.VISIBLE
        tv_information_belum_piket_list.visibility = View.INVISIBLE
        homeViewModel.getBelumPiketList(getBelumPiketCallback)

    }

    private val getBelumPiketCallback = object : DataSource.GetBelumPiketCallback {
        override fun onSuccess(list: List<ModelItem>) {
            iv_loading_rv_belum_piket.visibility = View.GONE
            belumPiketAdapter.list.clear()
            if (list.isNotEmpty()) {
                belumPiketAdapter.list.addAll(list)
            } else {
                tv_information_belum_piket_list.setText(R.string.empty)
                tv_information_belum_piket_list.visibility = View.VISIBLE
            }
            rv_belum_piket.adapter = belumPiketAdapter
            belumPiketAdapter.notifyDataSetChanged()
        }

        override fun onFailure(message: String) {
            iv_loading_rv_belum_piket.visibility = View.GONE
            rv_belum_piket.adapter = belumPiketAdapter
            belumPiketAdapter.notifyDataSetChanged()
            belumPiketAdapter.list.clear()
            tv_information_belum_piket_list.setText(R.string.failed)
            tv_information_belum_piket_list.visibility = View.VISIBLE
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
            Log.d("{devbacot}fcmToken : ", "success update fcm token")
        }

        override fun onFailure(message: String) {
            Log.d("{devbacot}fcmToken : ", "failed update fcm token")
            Log.d("{devbacot}fcmToken : ", message)
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
