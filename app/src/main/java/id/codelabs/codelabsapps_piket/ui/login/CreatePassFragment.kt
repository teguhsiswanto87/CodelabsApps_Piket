package id.codelabs.codelabsapps_piket.ui.login

import id.codelabs.codelabsapps_piket.utils.Utils
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
import id.codelabs.codelabsapps_piket.data.DataSource
import id.codelabs.codelabsapps_piket.model.ResponseLogin
import kotlinx.android.synthetic.main.fragment_create_pass.*
import kotlinx.android.synthetic.main.fragment_create_pass.edt_pass
import kotlinx.android.synthetic.main.fragment_create_pass.iv_loading


/**
 * A simple [Fragment] subclass.
 */
class CreatePassFragment : Fragment(), DataSource.LoginCallback {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginActivityCallback: LoginActivityCallback
    private var ignoreTextChange = false

    companion object {
        val tag = CreatePassFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_pass, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivityCallback = context as LoginActivityCallback
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loginViewModel = ViewModelProviders.of(activity!!).get(LoginViewModel::class.java)
        tv_entered_nim.text = loginViewModel.nim
        btn_create_password.setOnClickListener { clickCreatePassword() }
        edt_pass.addTextChangedListener { passTextChangedListener() }
        edt_repass.addTextChangedListener { repassTextChangedListener() }
        Glide.with(requireContext())
            .load(R.drawable.loading)
            .into(iv_loading)
    }

    private fun clickCreatePassword() {
        passTextChangedListener()
        if(edt_layout_pass.error != null){
            edt_repass.setText("")
        }
        if (edt_layout_pass.error == null && edt_layout_repass.error == null) {
            if (edt_pass.text.toString() == edt_repass.text.toString()) {
                val password = edt_pass.text.toString()
                loginViewModel.addPassword(password, this)
                btn_create_password.isClickable = false
                btn_create_password.text = ""
                iv_loading.visibility = View.VISIBLE
            } else {
                edt_layout_repass.error = "Password tidak cocok"
                ignoreTextChange = true
                edt_repass.setText("")
                ignoreTextChange = false
            }
        }

    }

    private fun passTextChangedListener() {
        if (edt_pass.text.toString().isEmpty()) {
            edt_layout_pass.error = "Password tidak boleh kosong"
        } else edt_layout_pass.error = null
    }
//    && edt_layout_repass.error != "Password tidak cocok"

    private fun repassTextChangedListener() {
        if(!ignoreTextChange){
            if (edt_repass.text.toString().isEmpty() && edt_layout_pass.error == null) {
                edt_layout_repass.error = "Masukkan kembali password"
            } else edt_layout_repass.error = null
        }
    }

    override fun onSuccess(response: ResponseLogin) {
        loginActivityCallback.successLogin()
    }

    override fun onFailure(message: String) {
        iv_loading.visibility = View.INVISIBLE
        btn_create_password.text = resources.getString(R.string.buat_password)
        btn_create_password.isClickable = true
        Utils.showToast(requireContext(), "Gagal memuat", 500)
    }

}
