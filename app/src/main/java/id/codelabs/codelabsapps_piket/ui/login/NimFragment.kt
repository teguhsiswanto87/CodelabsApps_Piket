package id.codelabs.codelabsapps_piket.ui.login


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide

import id.codelabs.codelabsapps_piket.R
import id.codelabs.codelabsapps_piket.data.DataSource
import kotlinx.android.synthetic.main.fragment_nim.*

/**
 * A simple [Fragment] subclass.
 */
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nim, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivityCallback = context as LoginActivityCallback
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loginViewModel = ViewModelProviders.of(activity!!).get(LoginViewModel::class.java)
//        edt_nim.se = loginViewModel.nim.
        btn_next.setOnClickListener { clickNext() }
        edt_nim.addTextChangedListener {
            if (edt_nim.text.toString().isEmpty()) {
                edt_layout_nim.error = "ga boleh kosong"
            }else {
                edt_layout_nim.error = null
            }
        }
        Glide.with(requireContext())
            .load(R.drawable.loading_white)
            .into(iv_loading)
    }

    private fun clickNext() {
        if (edt_nim.text.toString().isEmpty()) {
            edt_layout_nim.error = "ga boleh kosong"
        } else {
            loginViewModel.nim = edt_nim.text.toString()
            loginViewModel.checkHasPassword(this)
            btn_login_loading.visibility =View.VISIBLE
            iv_loading.visibility = View.VISIBLE
            iv_loading.bringToFront()
            iv_loading.invalidate()
        }
    }

    override fun onSuccess(message: String) {
        btn_login_loading.visibility =View.INVISIBLE
        iv_loading.visibility = View.INVISIBLE
        when (message){
            LoginActivity.HASPASSWORD -> loginActivityCallback.moveNext(loginViewModel.PASS_STATE)
            LoginActivity.YETPASSWORD -> loginActivityCallback.moveNext(loginViewModel.CREATE_PASS_STATE)
            LoginActivity.NOTFOUNDNIM -> edt_layout_nim.error = "nim not found"
        }
    }

    override fun onFailure(message: String) {
    }


}
