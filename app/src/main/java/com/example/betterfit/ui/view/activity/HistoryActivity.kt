package com.example.betterfit.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.betterfit.R
import com.example.betterfit.ui.adapter.TimeAdapter
import com.example.betterfit.db.AppDatabase
import com.example.betterfit.db.TimeData
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.ArrayList

class HistoryActivity : AppCompatActivity() {

    private lateinit var timeAdapter: TimeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)



        var toolbarHistoryActivity = findViewById<Toolbar>(R.id.historyActivityToolbar)
        setSupportActionBar(toolbarHistoryActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarHistoryActivity.setNavigationOnClickListener {
            onBackPressed()

        }
        rvHistory.layoutManager = LinearLayoutManager(applicationContext)
       GlobalScope.launch(Dispatchers.IO) {
           applicationContext?.let {
               val timeLists = AppDatabase(it).getTimeDao().getTime()
               withContext(Dispatchers.Main){
                   timeAdapter = TimeAdapter(timeLists as ArrayList<TimeData>)

                   rvHistory.adapter = timeAdapter
               }

           }

       }







    }
}