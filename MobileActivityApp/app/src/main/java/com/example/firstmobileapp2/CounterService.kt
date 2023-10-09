package com.example.firstmobileapp2

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import java.util.function.Consumer

class CounterService : Service() {

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val handler = Handler(Looper.getMainLooper())
        val runnable: Runnable = object : Runnable {
            var i = 0
            override fun run() {
                Log.d(TAG, "count: " + i++)
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)
        threadManagers.add(ThreadManager(handler, runnable))
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        threadManagers.forEach(Consumer { threadManager: ThreadManager ->
            threadManager.handler.removeCallbacks(
                threadManager.runnable
            )
        })
        Log.d(TAG, "Closed all threads for counter service")
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    class ThreadManager(val handler: Handler, val runnable: Runnable)
    companion object {
        private const val TAG = "Counter service"
        private val threadManagers: MutableList<ThreadManager> = ArrayList()
    }
}