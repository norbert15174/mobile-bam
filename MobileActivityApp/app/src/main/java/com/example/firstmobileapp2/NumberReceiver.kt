package com.example.firstmobileapp2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class NumberReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            val username = intent.getStringExtra("username")
            val number = intent.getLongExtra("number", 0L)
            Log.d("NumberReceiver", "Username: $username, Number: $number")
            CoroutineScope(Dispatchers.IO).launch {
                val dataEntity = DataEntity(uid = UUID.randomUUID().toString(), name = username, number = number)
                if (context != null) {
                    DatabaseSingleton.getDatabase(context.applicationContext).dataDao().insert(dataEntity)
                }
            }
        }
    }
}