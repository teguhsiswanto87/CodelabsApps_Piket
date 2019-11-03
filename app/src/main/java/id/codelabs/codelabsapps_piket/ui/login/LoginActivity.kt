package id.codelabs.codelabsapps_piket.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import id.codelabs.codelabsapps_piket.R
import id.codelabs.codelabsapps_piket.ui.home.HomeActivity


class LoginActivity : AppCompatActivity(), LoginActivityCallback {

    private lateinit var loginViewModel: LoginViewModel
    private var mFragment: Fragment? = null
    private var tag = ""
    private val ROTATION = "ROTATION"

    companion object {
        const val HAS_PASSWORD = "HAS_PASSWORD"
        const val YET_PASSWORD = "YET_PASSWORD"
        const val NOT_FOUND_NIM = "NOT_FOUND_NIM"
        const val WRONG_PASSWORD = "WRONG_PASSWORD"
        const val PASSWORD_ADDED = "PASSWORD_ADDED"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        loginViewModel.loginActivity = this

        if (savedInstanceState == null) {
            mFragment = NimFragment()
            tag = NimFragment.tag
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_login, mFragment!!, tag)
                .commit()
        }
    }

    override fun moveNext(to: String) {

        when (to) {
            loginViewModel.PASS_STATE -> {
                mFragment = PassFragment()
                tag = PassFragment.tag
            }
            loginViewModel.CREATE_PASS_STATE -> {
                mFragment = CreatePassFragment()
                tag = CreatePassFragment.tag
            }
        }

        if (mFragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_login, mFragment!!, tag)
                .addToBackStack(tag)
                .commit()
        }

        loginViewModel.state = to

    }


    override fun successLogin() {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ROTATION, ROTATION)
    }

}

