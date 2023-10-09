package com.example.firstmobileapp2

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit);
        val editText = findViewById<EditText>(R.id.editText);

        buttonSubmit.setOnClickListener {
            val inputText = editText.text.toString()
            val intent = Intent(this, UserActivity::class.java)
            intent.putExtra("userInput", inputText)
            startActivity(intent)
        }

    }

    fun start(v: View?) {
        startService(Intent(baseContext, CounterService::class.java))
    }

    fun stop(v: View?) {
        stopService(Intent(baseContext, CounterService::class.java))
    }

}