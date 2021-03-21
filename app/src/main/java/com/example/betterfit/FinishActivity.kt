package com.example.betterfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.betterfit.db.AppDatabase
import com.example.betterfit.db.TimeData
import kotlinx.android.synthetic.main.activity_finish.*
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

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

        val date = System.currentTimeMillis()

        val time = TimeData(date)




        GlobalScope.launch {
            applicationContext?.let {
                AppDatabase(it).getTimeDao().insert(time)
            }

        }


        finishBtn.setOnClickListener {
            finish()
        }


    }


}