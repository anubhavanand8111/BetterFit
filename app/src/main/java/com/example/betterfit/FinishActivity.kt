package com.example.betterfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_finish.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        var toolbarFinishActivity = findViewById<Toolbar>(R.id.finishActivityToolbar)
        setSupportActionBar(toolbarFinishActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarFinishActivity.setNavigationOnClickListener {
            onBackPressed()

        }

        finishBtn.setOnClickListener{
            finish()
        }
    }
}