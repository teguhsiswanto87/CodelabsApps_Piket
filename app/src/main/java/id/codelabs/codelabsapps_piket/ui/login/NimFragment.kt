package id.codelabs.codelabsapps_piket.ui.login


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide

import id.codelabs.codelabsapps_piket.R
import id.codelabs.codelabsapps_piket.Utils
import id.codelabs.codelabsapps_piket.data.DataSource
import kotlinx.android.synthetic.main.fragment_nim.*
import kotlinx.android.synthetic.main.fragment_nim.iv_loading

class NimFragment : Fragment(), DataSource.CheckHasPasswordCallback {


    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginActivityCallback: LoginActivityCallback

    companion object {
        val tag = NimFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nim, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivityCallback = context as LoginActivityCallback
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loginViewModel = ViewModelProviders.of(activity!!).get(LoginViewModel::class.java)
        btn_next.setOnClickListener { clickNext() }
        edt_nim.addTextChangedListener { textChangedListener() }
        Glide.with(requireContext())
            .load(R.drawable.loading)
            .into(iv_loading)
    }

    private fun clickNext() {
        if (edt_layout_nim.error == null) {
            val nim = edt_nim.text.toString()
            loginViewModel.checkHasPassword(nim,this)
            btn_next.text = ""
            btn_next.isClickable = false
            iv_loading.visibility = View.VISIBLE

        }
    }

    private fun textChangedListener() {
        if (edt_nim.text.toString().isEmpty()) {
            edt_layout_nim.error = "NIM tidak boleh kosong"
        } else {
            edt_layout_nim.error = null
        }
    }

    override fun onSuccess(message: String) {
        iv_loading.visibility = View.INVISIBLE
        btn_next.text = resources.getString(R.string.selanjutnya)
        btn_next.isClickable = true
        when (message) {
            LoginActivity.HAS_PASSWORD -> loginActivityCallback.moveNext(loginViewModel.PASS_STATE)
            LoginActivity.YET_PASSWORD -> loginActivityCallback.moveNext(loginViewModel.CREATE_PASS_STATE)
            LoginActivity.NOT_FOUND_NIM -> edt_layout_nim.error = "NIM tidak di temukan"
        }
    }

    override fun onFailure(message: String) {
        iv_loading.visibility = View.VISIBLE
        btn_next.text = resources.getString(R.string.selanjutnya)
        btn_next.isClickable = true
        Utils.showToast(requireContext(), "Gagal memuat", 500)
    }


}
