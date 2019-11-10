package id.codelabs.codelabsapps_piket.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import id.codelabs.codelabsapps_piket.R
import id.codelabs.codelabsapps_piket.utils.Utils
import id.codelabs.codelabsapps_piket.ui.home.HomeActivity
import id.codelabs.codelabsapps_piket.ui.home.customDatePicker.CustomDatePickerUtils
import id.codelabs.codelabsapps_piket.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private val splashTime = 3000L
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler = Handler()
        handler.postDelayed({
            goToMainActivity()
        }, splashTime)
    }

    private fun goToMainActivity() {
        Utils.makeSharedPreferences(this)
        val intent: Intent = if (Utils.getSharedPreferences(Utils.SAVED_NIM) == Utils._404) {
            Intent(applicationContext, LoginActivity::class.java)
        } else {
            Log.d("devbct savednim :", Utils.getSharedPreferences(
                Utils.SAVED_NIM)!!)
            Intent(applicationContext, HomeActivity::class.java)
        }
        CustomDatePickerUtils.selectedDateMarker = null
        startActivity(intent)
        finish()
    }
}
