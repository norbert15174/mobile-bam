package com.example.firstmobileapp2

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UserActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val userInput = intent.getStringExtra("userInput")
        val textViewResult = findViewById<TextView>(R.id.textView)
        textViewResult.text = "Wprowadzony tekst: $userInput"
    }
}