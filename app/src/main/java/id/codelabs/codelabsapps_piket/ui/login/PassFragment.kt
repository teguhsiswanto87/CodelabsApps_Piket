package id.codelabs.codelabsapps_piket.ui.login


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
class PassFragment : Fragment(), DataSource.LoginCallback  {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginActivityCallback: LoginActivityCallback

    companion object{
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
        btn_login.setOnClickListener { clickLogin() }
        Glide.with(requireContext())
            .load(R.drawable.loading_white)
            .into(iv_loading)
    }

    private fun clickLogin(){
        if(edt_pass.text.toString().isEmpty()){
            edt_layout_pass.error = "ga boleh kosong"
        }else{
            loginViewModel.password = edt_pass.text.toString()
            loginViewModel.login(this)
            btn_login_loading.visibility =View.VISIBLE
            iv_loading.visibility = View.VISIBLE
        }
    }

    override fun onSuccess(response : ResponseLogin) {
        btn_login_loading.visibility =View.INVISIBLE
        iv_loading.visibility = View.INVISIBLE
        loginActivityCallback.successLogin()
    }

    override fun onFaillure(message: String) {
        btn_login_loading.visibility =View.INVISIBLE
        iv_loading.visibility = View.INVISIBLE
        edt_layout_pass.error="password salah"
    }

}
