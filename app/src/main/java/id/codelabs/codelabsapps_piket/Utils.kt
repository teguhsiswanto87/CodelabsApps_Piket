package id.codelabs.codelabsapps_piket

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.widget.Toast
import java.util.*

class Utils {

    companion object {

        var maxDayMonth = arrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        private var thisYear: Int = Calendar.getInstance().get(Calendar.YEAR)

        init {
            if (thisYear % 4 == 0) maxDayMonth[1] = 29
        }

        const val MY_PREFS_NAME = "MY_PREFS_NAME"
        const val SAVED_PASSWORD = "PASSWORD"
        const val SAVED_NIM = "SAVED_NIM"
        const val SAVED_TOKEN = "SAVED_TOKEN"
        const val SAVED_FCM_TOKEN = "SAVED_FCM_TOKEN"
        const val _404 = "404 Not Found SharedPreferences "
        private lateinit var prefs: SharedPreferences

        fun makeSharedPreferences(activity: Activity) {
            prefs = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
            prefs.edit().commit()
        }

        fun putSharedPreferences(key: String, value: String) {
            var editPref = prefs.edit()
            editPref.putString(key, value)
            editPref.commit()
        }

        fun getSharedPreferences(key: String): String? {
            return prefs.getString(key, _404)
        }

        fun showToast(context: Context, text: String, durationInMillis: Int) {
            val mToastToShow = Toast.makeText(context, text, Toast.LENGTH_LONG)
            val toastCountDown: CountDownTimer
            toastCountDown = object : CountDownTimer(durationInMillis.toLong(), 100) {
                override fun onTick(millisUntilFinished: Long) {
                }

                override fun onFinish() {
                    mToastToShow.cancel()
                }
            }
            mToastToShow.show()
            toastCountDown.start()
        }

    }
}