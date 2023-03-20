package com.example.cryptoanalytic.screens.notificationDetails.repository.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.example.cryptoanalytic.R
import com.example.cryptoanalytic.common.di.DispatcherIOScope
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.repository.CryptocurrencyDetailsRepository
import com.example.cryptoanalytic.screens.notificationDetails.repository.NotificationDetailsRepository
import com.example.database.entity.DbNotification
import com.example.database.wrapper.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


//TODO: service updates to much times when persistence is set to false then changed to true
@AndroidEntryPoint
class CryptoChangeNotificationService : Service() {
    private val TAG = CryptoChangeNotificationService::class.java.simpleName

    companion object {
        //        public const val NOTIFICATION_PUSH_CHANNEL_ID = 11
        public const val NOTIFICATION_PUSH_CHANNEL_ID = "notificationChannel"
    }

    @Inject
    lateinit var notificationDetailsRepository: NotificationDetailsRepository
    @Inject
    lateinit var cryptocurrencyDetailsRepository: CryptocurrencyDetailsRepository
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
            notificationDetailsRepository.getNotifications()
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            result.data?.let {
                                it.filter { it.isActive }
                                    .map { launchPersistentTimerNotifications(it) }
                            }
                        }
                    }
                }
        }
    }


    private fun launchPersistentTimerNotifications(dbNotification: DbNotification) {
        notificationJob = scope.launch {
            while (notificationJob?.isActive == true) {

                //repeat Task Here
                Log.d(TAG, "delay time started: ${dbNotification.alertFrequency / 1000} s")
                delay(dbNotification.alertFrequency)

                if (!dbNotification.isPersistent) {
                    notificationDetailsRepository.updateNotificationActiveState(dbNotification.notificationId, false).collect { }
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

    private fun getCryptocurrencyMarketData(dbNotification: DbNotification) {
        cryptocurrencyMarketDataJob = scope.launch {
            cryptocurrencyDetailsRepository.getCryptocurrencyMarketData(dbNotification.cryptocurrencyId)
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            result.data?.let { cryptocurrency ->
                                when {
                                    dbNotification.lessThan < cryptocurrency.dbCryptocurrency.currentPrice ||
                                    dbNotification.moreThan > cryptocurrency.dbCryptocurrency.currentPrice ||
                                    dbNotification.increasedBy <= cryptocurrency.dbCryptocurrency.currentPrice - dbNotification.cryptocurrencyPrice ||
                                    dbNotification.decreasedBy <= dbNotification.cryptocurrencyPrice - cryptocurrency.dbCryptocurrency.currentPrice ||
                                    dbNotification.changedBy != dbNotification.cryptocurrencyPrice - cryptocurrency.dbCryptocurrency.currentPrice -> {

                                        Log.d(TAG, "Notification sent for ${dbNotification.cryptocurrencyShortName}")
                                        notificationManager.notify(dbNotification.notificationId.toInt(), getRemoteNotification(dbNotification).build())
                                        cryptocurrencyMarketDataJob?.cancel()
                                    }
                                    else -> {
                                        Log.d(TAG, "No parameters triggered for notification")
                                    }
                                }
                            }
                        }
                    }
                }
        }
    }

    private fun getRemoteNotification(dbNotification: DbNotification): Notification.Builder {
        notificationManager.createNotificationChannel(NotificationChannel(NOTIFICATION_PUSH_CHANNEL_ID, "Change price Channel", NotificationManager.IMPORTANCE_LOW))

        return Notification.Builder(this)
            .setContentIntent(null)
            .setChannelId(NOTIFICATION_PUSH_CHANNEL_ID)
            .setContentTitle("${dbNotification.cryptocurrencyName} is changed")
            .setSmallIcon(R.drawable.coinmarketcap)
            .setLargeIcon(getDrawable(R.drawable.coinmarketcap)?.toBitmap(300, 300))
            .setStyle(Notification.BigTextStyle().bigText(dbNotification.getAllParamsInMultilineString()))
    }

}
