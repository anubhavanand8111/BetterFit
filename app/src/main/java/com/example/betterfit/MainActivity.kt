package com.example.betterfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startBtn = findViewById<View>(R.id.ll_start_btn)
        startBtn.setOnClickListener {
            val i =Intent(this,ExerciseActivity::class.java)
            startActivity(i)

        }

    }
}