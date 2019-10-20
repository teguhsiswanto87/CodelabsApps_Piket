package id.codelabs.codelabsapps_piket.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import id.codelabs.codelabsapps_piket.R
import id.codelabs.codelabsapps_piket.home.HomeActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class LoginActivity : AppCompatActivity(), LoginActivityCallback {

    private lateinit var loginViewModel: LoginViewModel
    private var fragment: Fragment? = null
    private var tag = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        when {
            loginViewModel.state == loginViewModel.NIM_STATE -> {
                fragment = NimFragment()
                tag = NimFragment.tag
            }
            loginViewModel.state == loginViewModel.PASS_STATE -> {
                fragment = PassFragment()
                tag = PassFragment.tag
            }
            loginViewModel.state == loginViewModel.CREATE_PASS_STATE -> {
                fragment = CreatePassFragment()
                tag = CreatePassFragment.tag
            }
        }
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container_login, fragment!!,tag)
                .commit()
        }


        //        btn_login_selanjutnya.setOnClickListener {

//            if (!isPasswordValid(edt_login_password.text)) {
//                edt_login_layoutpassword.error = "Password tidak boleh kosong"
//            } else {
//                edt_login_layoutpassword.error = null
//                toast("login berhasil")
//            }
//        }
//
//        edt_login_password.setOnKeyListener({ _, _, _ ->
//            if (isPasswordValid(edt_login_password.text!!)) {
//                edt_login_layoutpassword.error = null
//            }
//            false
//        })

    }

    override fun moveNext(to: String) {

        when (to) {
            loginViewModel.PASS_STATE -> {
                fragment = PassFragment()
                tag = PassFragment.tag
            }
            loginViewModel.CREATE_PASS_STATE -> {
                fragment = CreatePassFragment()
                tag = CreatePassFragment.tag
            }
        }

        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container_login, fragment!!,tag)
                .commit()
        }

        loginViewModel.state = to

    }

    fun movePrev(from: String) {

        when (from) {
            loginViewModel.PASS_STATE -> {
                tag = PassFragment.tag
            }
            loginViewModel.CREATE_PASS_STATE -> {
                tag = CreatePassFragment.tag
            }
        }

        fragment = supportFragmentManager.findFragmentByTag(tag)

        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .remove(fragment!!)
                .commit()
        }

        loginViewModel.state = loginViewModel.NIM_STATE

    }

    override fun onBackPressed() {

        when {
            loginViewModel.state == loginViewModel.NIM_STATE -> {
                super.onBackPressed()
            }
            loginViewModel.state == loginViewModel.PASS_STATE -> {
                movePrev(loginViewModel.PASS_STATE)
            }
            loginViewModel.state == loginViewModel.CREATE_PASS_STATE -> {
                movePrev(loginViewModel.CREATE_PASS_STATE)
            }
        }
    }

    override fun successLogin() {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }


//    private fun isPasswordValid(text: Editable?): Boolean {
//        return text != null && text.length >= 5
//    }
}
