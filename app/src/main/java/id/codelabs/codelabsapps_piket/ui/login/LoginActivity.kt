package id.codelabs.codelabsapps_piket.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import id.codelabs.codelabsapps_piket.R
import id.codelabs.codelabsapps_piket.toast
import id.codelabs.codelabsapps_piket.ui.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login_selanjutnya.setOnClickListener {
            if (!isPasswordValid(edt_login_password.text)) {
                edt_login_layoutpassword.error = "Password tidak boleh kosong"
            } else {
                edt_login_layoutpassword.error = null
                toast("login berhasil")
            }
        }

        edt_login_password.setOnKeyListener({ _, _, _ ->
            if (isPasswordValid(edt_login_password.text!!)) {
                edt_login_layoutpassword.error = null
            }
            false
        })

    }

    private fun isPasswordValid(text: Editable?): Boolean {
        return text != null && text.length >= 5
    }
}
