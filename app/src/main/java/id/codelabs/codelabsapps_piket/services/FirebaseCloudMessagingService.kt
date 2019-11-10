package id.codelabs.codelabsapps_piket.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import id.codelabs.codelabsapps_piket.R
import id.codelabs.codelabsapps_piket.model.ResponseFCMSent
import id.codelabs.codelabsapps_piket.ui.splash.SplashActivity


class FirebaseCloudMessagingService : FirebaseMessagingService() {
    private val TAG = "devbct FCMS"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.d(TAG, "onMessageReceived Firebase")

        Log.d(TAG, "From: " + remoteMessage.from!!)

        if (remoteMessage.data.isNotEmpty()) {
            val data = remoteMessage.data
            val objdata = ResponseFCMSent(data["nama"], data["jenis_piket"])



            Log.d(TAG, remoteMessage.data.toString())
            Log.d(TAG, objdata.jenisPiket!!)

//            sendNotificationwk(objdata)
            sendNotification(objdata)
//            sendNotificationso(objdata.nama.toString(),objdata.jenisPiket.toString())
//
        }

    }

    private fun sendNotificationwk(data : ResponseFCMSent) {
        val notification = NotificationCompat.Builder(this)
            .setContentTitle("Piket Codelabs")
            .setContentText("Halo "+data.nama+", jangan lupa hari ini kamu piket "+data.jenisPiket+" ya...")
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()
        val manager = NotificationManagerCompat.from(applicationContext)
        manager.notify(123, notification)
    }

    private fun sendNotificationso(messageTitle: String, messageBody: String) {
        val intent = Intent(this, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0 /* request code */,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val pattern = longArrayOf(500, 500, 500, 500, 500)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_notifications_white_24dp)
            .setContentTitle(messageTitle)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setVibrate(pattern)
            .setLights(Color.BLUE, 1, 1)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent) as NotificationCompat.Builder

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }


    private lateinit var notification: Notification
    private var notifId: Int = 2000

    @SuppressLint("NewApi")
    private fun createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val context = this.applicationContext
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.parseColor("#e8334a")
            notificationChannel.description = getString(R.string.notification_channel_description)
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    companion object {
        const val CHANNEL_ID = "id.codelabs.codelabsapps_piket.services.CHANNEL_ID"
        const val CHANNEL_NAME = "Piket Notification"
    }


    private fun sendNotification(data: ResponseFCMSent) {
        createChannel()

        val title = "Piket Codelabs"
        val message: String


        val nama = data.nama!!
        val jenisPiket = data.jenisPiket!!
        message = "Halo $nama, jangan lupa hari ini kamu piket $jenisPiket ya..."


        val context = this.applicationContext
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notifyIntent = Intent(this, SplashActivity::class.java)


        notifyIntent.putExtra("title", title)
        notifyIntent.putExtra("message", message)
        notifyIntent.putExtra("notification", true)

        notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        val pendingIntent =
            PendingIntent.getActivity(context, 14, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val res = this.resources
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            notification = Notification.Builder(this, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setContentTitle(title)
                .setStyle(
                    Notification.BigTextStyle()
                        .bigText(message)
                )
                .setContentText(message).build()
        } else {

            notification = Notification.Builder(this)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle(title)
                .setStyle(
                    Notification.BigTextStyle()
                        .bigText(message)
                )
                .setSound(uri)
                .setContentText(message).build()
        }

        notificationManager.notify(notifId, notification)

    }
}