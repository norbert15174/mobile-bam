package com.example.firstmobileapp2

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UserActivity : AppCompatActivity() {

    var username : String? = null
    private val numberReceiver = NumberReceiver()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val userInput = intent.getStringExtra("userInput")
        val textViewResult = findViewById<TextView>(R.id.textView)
        textViewResult.text = "Wprowadzony tekst: $userInput"
        username = userInput
        val filter = IntentFilter("com.example.number_receiver")
        registerReceiver(numberReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(numberReceiver)
    }

    fun start(v: View?) {
        val intent = Intent(baseContext, CounterService::class.java)
        intent.putExtra("username", username)
        startService(intent)
    }

    fun stop(v: View?) {
        stopService(Intent(baseContext, CounterService::class.java))
    }

}