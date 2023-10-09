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
        var threadManager = ThreadManager();
        val handler = Handler(Looper.getMainLooper())
        val runnable: Runnable = object : Runnable {
            var counter = 0L;
            override fun run() {
                Log.d(TAG, "count: " + counter++)
                threadManager.setCount(counter);
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)
        threadManager.setName(intent.getStringExtra("username")!!)
        threadManager.setHandler(handler)
        threadManager.setRunnable(runnable)
        threadManagers.add(threadManager)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        threadManagers.forEach(Consumer { threadManager: ThreadManager ->
            threadManager.getHandler()?.removeCallbacks(threadManager.getRunnable()!!)
            val intent = Intent("com.example.number_receiver")
            intent.putExtra("username", threadManager.getName())
            intent.putExtra("number", threadManager.getCount())
            sendBroadcast(intent)
        })
        Log.d(TAG, "Closed all threads for counter service")
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    class ThreadManager {
        private var handler: Handler? = null
        private var runnable: Runnable? = null
        private var count: Long = 0
        private var name: String? = null

        constructor() {
            this.count = 0
        }

        // Gettery i settery dla pól
        fun getHandler(): Handler? {
            return handler
        }

        fun setHandler(handler: Handler?) {
            this.handler = handler
        }

        fun getRunnable(): Runnable? {
            return runnable
        }

        fun setRunnable(runnable: Runnable?) {
            this.runnable = runnable
        }

        fun getCount(): Long {
            return count
        }

        fun setCount(count: Long) {
            this.count = count
        }

        fun setName(name: String){
            this.name = name;
        }

        fun getName(): String? {
            return name
        }

    }
    companion object {
        private const val TAG = "Counter service"
        private val threadManagers: MutableList<ThreadManager> = ArrayList()
    }
}