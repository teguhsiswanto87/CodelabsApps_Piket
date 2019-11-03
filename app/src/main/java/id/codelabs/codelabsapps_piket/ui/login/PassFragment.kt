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
import id.codelabs.codelabsapps_piket.model.ResponseLogin
import kotlinx.android.synthetic.main.fragment_pass.*

/**
 * A simple [Fragment] subclass.
 */
class PassFragment : Fragment(), DataSource.LoginCallback {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginActivityCallback: LoginActivityCallback

    companion object {
        val tag = PassFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pass, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivityCallback = context as LoginActivityCallback
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loginViewModel = ViewModelProviders.of(activity!!).get(LoginViewModel::class.java)
        tv_entered_nim.text = loginViewModel.nim
        btn_login.setOnClickListener { clickLogin() }
        edt_pass.addTextChangedListener { textChangeListener() }
        Glide.with(requireContext())
            .load(R.drawable.loading)
            .into(iv_loading)
    }

    private fun clickLogin() {
        if (edt_layout_pass.error == null) {
            val password = edt_pass.text.toString()
            loginViewModel.login(password, this)
            btn_login.isClickable = false
            btn_login.text = ""
            iv_loading.visibility = View.VISIBLE
        }
    }

    private fun textChangeListener() {
        if (edt_pass.text.toString().isEmpty()) {
            edt_layout_pass.error = "Password tidak boleh kosong"
        } else edt_layout_pass.error = null
    }

    override fun onSuccess(response: ResponseLogin) {
        iv_loading.visibility = View.INVISIBLE
        btn_login.isClickable = true
        btn_login.text = resources.getString(R.string.masuk)
        loginActivityCallback.successLogin()
    }

    override fun onFailure(message: String) {
        iv_loading.visibility = View.INVISIBLE
        btn_login.isClickable = true
        btn_login.text = resources.getString(R.string.masuk)
        if (message == LoginActivity.WRONG_PASSWORD) {
            edt_layout_pass.error = "Password salah"
        }
        Utils.showToast(requireContext(), "Gagal memuat", 500)
    }

}
