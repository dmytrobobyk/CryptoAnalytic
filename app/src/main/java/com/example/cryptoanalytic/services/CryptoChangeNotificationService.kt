package com.example.cryptoanalytic.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.cryptoanalytic.domain.usecases.cryptocurrencies.GetCryptocurrencyMarketDataUseCase
import com.cryptoanalytic.domain.usecases.notifications.GetNotificationsUseCase
import com.cryptoanalytic.domain.usecases.notifications.UpdateNotificationActiveStateUseCase
import com.example.cryptoanalytic.R
import com.example.cryptoanalytic.di.DispatcherIOScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import com.cryptoanalytic.domain.wrapper.Result
import javax.inject.Inject


//TODO: service updates to much times when persistence is set to false then changed to true
@AndroidEntryPoint
class CryptoChangeNotificationService : Service() {
    private val TAG = CryptoChangeNotificationService::class.java.simpleName

    companion object {
        const val NOTIFICATION_PUSH_CHANNEL_ID = "notificationChannel"
    }

    @Inject
    lateinit var getNotificationsUseCase: GetNotificationsUseCase

    @Inject
    lateinit var updateNotificationActiveStateUseCase: UpdateNotificationActiveStateUseCase

    @Inject
    lateinit var getCryptocurrencyMarketDataUseCase: GetCryptocurrencyMarketDataUseCase

    @Inject
    @DispatcherIOScope
    lateinit var ioDispatcher: CoroutineDispatcher

    private val scope: CoroutineScope by lazy { CoroutineScope(ioDispatcher) }

    private var notificationJob: Job? = null
    private var cryptocurrencyMarketDataJob: Job? = null

    private lateinit var notificationManager: NotificationManager

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind()")
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "Started onStartCommand()")
        notificationManager = (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)

        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate()")

        scope.launch {
            getNotificationsUseCase()
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            result.data?.let {
                                it.filter { it.isActive }
                                    .map { launchPersistentTimerNotifications(it) }
                            }
                        }
                        else -> {}
                    }
                }
        }
    }


    private fun launchPersistentTimerNotifications(dbNotification: com.cryptoanalytic.domain.entity.Notification) {
        notificationJob = scope.launch {
            while (notificationJob?.isActive == true) {

                //repeat Task Here
                Log.d(TAG, "delay time started: ${dbNotification.alertFrequency / 1000} s")
                delay(dbNotification.alertFrequency)

                if (!dbNotification.isPersistent) {
                    updateNotificationActiveStateUseCase(dbNotification.notificationId, false).collect { }
                    getCryptocurrencyMarketData(dbNotification)
                    notificationJob?.cancel()
                } else {
                    getCryptocurrencyMarketData(dbNotification)
                }
            }
        }
    }


    override fun onDestroy() {
        Toast.makeText(this, "NotificationService finished", Toast.LENGTH_SHORT).show()
        scope.cancel()
        super.onDestroy()
    }

    private fun getCryptocurrencyMarketData(dbNotification: com.cryptoanalytic.domain.entity.Notification) {
        cryptocurrencyMarketDataJob = scope.launch {
            getCryptocurrencyMarketDataUseCase(dbNotification.cryptocurrencyId)
                .collect { result ->
                    when (result) {
                        is com.cryptoanalytic.domain.wrapper.Result.Success -> {
                            result.data?.let { cryptocurrency ->
                                when {
                                    dbNotification.lessThan < cryptocurrency.currentPrice ||
                                            dbNotification.moreThan > cryptocurrency.currentPrice ||
                                            dbNotification.increasedBy <= cryptocurrency.currentPrice - dbNotification.cryptocurrencyPrice ||
                                            dbNotification.decreasedBy <= dbNotification.cryptocurrencyPrice - cryptocurrency.currentPrice ||
                                            dbNotification.changedBy != dbNotification.cryptocurrencyPrice - cryptocurrency.currentPrice -> {

                                        Log.d(TAG, "Notification sent for ${dbNotification.cryptocurrencyShortName}")
                                        notificationManager.notify(
                                            dbNotification.notificationId.toInt(),
                                            getRemoteNotification(dbNotification).build()
                                        )
                                        cryptocurrencyMarketDataJob?.cancel()
                                    }
                                    else -> {
                                        Log.d(TAG, "No parameters triggered for notification")
                                    }
                                }
                            }
                        }
                        else -> {}
                    }
                }
        }
    }

    private fun getRemoteNotification(dbNotification: com.cryptoanalytic.domain.entity.Notification): Notification.Builder {

        val notification = Notification.Builder(this)
            .setContentIntent(null)
//            .setChannelId(NOTIFICATION_PUSH_CHANNEL_ID)
            .setContentTitle("${dbNotification.cryptocurrencyName} is changed")
            .setSmallIcon(R.drawable.coinmarketcap)
            .setLargeIcon(getDrawable(R.drawable.coinmarketcap)?.toBitmap(300, 300))
            .setStyle(Notification.BigTextStyle().bigText(dbNotification.getAllParamsInMultilineString()))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    NOTIFICATION_PUSH_CHANNEL_ID,
                    "Change price Channel",
                    NotificationManager.IMPORTANCE_LOW
                )
            )
            notification.setChannelId(NOTIFICATION_PUSH_CHANNEL_ID)
        }

        return notification
    }

}
