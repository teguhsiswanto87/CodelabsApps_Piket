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
import kotlinx.android.synthetic.main.fragment_nim.*

/**
 * A simple [Fragment] subclass.
 */
class NimFragment : Fragment(), DataSource.CheckHasPasswordCallback {


    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginActivityCallback: LoginActivityCallback

    companion object{
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
        btn_next.setOnClickListener { clickNext() }
    }

    private fun clickNext() {
        loginViewModel.nim = edt_nim.text.toString()
        loginViewModel.checkHasPassword(this)
    }

    override fun onSuccess(message: String) {
        loginActivityCallback.moveNext(loginViewModel.PASS_STATE)
    }

    override fun onFailure(message: String) {
        loginActivityCallback.moveNext(loginViewModel.CREATE_PASS_STATE)
    }


}
