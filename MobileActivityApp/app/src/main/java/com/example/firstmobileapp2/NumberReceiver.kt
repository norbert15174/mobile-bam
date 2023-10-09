package com.example.firstmobileapp2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class NumberReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            val username = intent.getStringExtra("username")
            val number = intent.getLongExtra("number", 0L)
            Log.d("NumberReceiver", "Username: $username, Number: $number")
        }
    }
}