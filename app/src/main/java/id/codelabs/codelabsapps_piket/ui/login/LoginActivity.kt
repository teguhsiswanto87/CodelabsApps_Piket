package id.codelabs.codelabsapps_piket.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import id.codelabs.codelabsapps_piket.R
import id.codelabs.codelabsapps_piket.home.HomeActivity


class LoginActivity : AppCompatActivity(), LoginActivityCallback {

    private lateinit var loginViewModel: LoginViewModel
    private var fragment: Fragment? = null
    private var tag = ""
    companion object{
        val HASPASSWORD = "HASPASSWORD"
        val YETPASSWORD = "YETPASSWORD"
        val NOTFOUNDNIM = "NOTFOUNDNIM"
        val SUCCEEDLOGIN = "SUCCESSLOGIN"
        val WRONGPASSWORD = "WRONGPASSWORD"
        val PASSWORDADDED = "PASSWORDADDED"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        loginViewModel.loginActivity = this

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
                .replace(R.id.container_login, fragment!!,tag)
                .addToBackStack(null)
                .commit()
        }


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
                .replace(R.id.container_login, fragment!!,tag)
                .addToBackStack(null)
                .commit()
        }

        loginViewModel.state = to

    }


    override fun successLogin() {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

}
