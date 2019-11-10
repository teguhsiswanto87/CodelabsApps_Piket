package id.codelabs.codelabsapps_piket.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import id.codelabs.codelabsapps_piket.R

class Utils {

    companion object {

        const val MY_PREFS_NAME = "MY_PREFS_NAME"
        const val SAVED_PASSWORD = "PASSWORD"
        const val SAVED_NIM = "SAVED_NIM"
        const val SAVED_TOKEN = "SAVED_TOKEN"
        const val SAVED_FCM_TOKEN = "SAVED_FCM_TOKEN"
        const val _404 = "404 Not Found SharedPreferences "
        private lateinit var prefs: SharedPreferences

        fun makeSharedPreferences(activity: Activity) {
            prefs = activity.getSharedPreferences(
                MY_PREFS_NAME, Context.MODE_PRIVATE
            )
            prefs.edit().apply()
            prefs.edit().commit()
        }

        fun putSharedPreferences(key: String, value: String) {
            var editPref = prefs.edit()
            editPref.putString(key, value)
            editPref.apply()
            editPref.commit()
        }

        fun getSharedPreferences(key: String): String? {
            return prefs.getString(
                key,
                _404
            )
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

        fun showAlertDialog(
            context: Context,
            message: String,
            positiveText: String,
            negativeText: String,
            listener: AlertDialogResultListener
        ) {

            val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        listener.onPositiveResult()
                    }

                    DialogInterface.BUTTON_NEGATIVE -> {
                        listener.onNegativeResult()
                    }
                }
            }

            val builder = AlertDialog.Builder(context)
            val dialog =
                builder.setMessage(message).setPositiveButton(positiveText, dialogClickListener)
                    .setNegativeButton(negativeText, dialogClickListener).create()

            dialog.setOnShowListener {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(context.resources.getColor(R.color.red))
            }

            dialog.show()

        }

    }
}