package com.example.firstmobileapp2

import android.content.Intent
import android.os.Bundle
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

}