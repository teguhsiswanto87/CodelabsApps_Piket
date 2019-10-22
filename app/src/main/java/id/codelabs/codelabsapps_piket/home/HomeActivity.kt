package id.codelabs.codelabsapps_piket.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import id.codelabs.codelabsapps_piket.R
import id.codelabs.codelabsapps_piket.Utils

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Utils.makeSharedPreferences(this)
    }
}
