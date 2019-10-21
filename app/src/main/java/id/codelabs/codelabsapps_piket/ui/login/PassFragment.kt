package id.codelabs.codelabsapps_piket.ui.login


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import id.codelabs.codelabsapps_piket.R
import id.codelabs.codelabsapps_piket.data.DataSource
import id.codelabs.codelabsapps_piket.model.ResponseLogin
import kotlinx.android.synthetic.main.fragment_create_pass.btn_login
import kotlinx.android.synthetic.main.fragment_create_pass.edt_pass

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
    }

    private fun clickLogin(){
        if(edt_pass.text.toString().isEmpty()){
            edt_pass.error = "ga boleh kosong"
        }else{
            loginViewModel.password = edt_pass.text.toString()
            loginViewModel.login(this)
        }
    }

    override fun onSuccess(response : ResponseLogin) {
        loginActivityCallback.successLogin()
    }

    override fun onFaillure(message: String) {
    }

}
