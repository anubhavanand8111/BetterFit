package com.example.betterfit.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.betterfit.R
import com.example.betterfit.db.AppDatabase
import com.example.betterfit.db.TimeData
import kotlinx.android.synthetic.main.activity_finish.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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