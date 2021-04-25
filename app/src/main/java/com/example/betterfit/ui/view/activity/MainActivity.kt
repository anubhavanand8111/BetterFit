package com.example.betterfit.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.betterfit.R
import com.example.betterfit.ui.view.fragments.TabViewPager
import com.example.betterfit.ui.view.fragments.DietFragment
import com.example.betterfit.ui.view.fragments.ExerciseFragment
import com.example.betterfit.ui.view.fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(mainToolBar)

        val adapterViewPager = TabViewPager(supportFragmentManager)
        adapterViewPager.addFragment(HomeFragment(),"Home")
        adapterViewPager.addFragment(DietFragment(),"Diet")
        adapterViewPager.addFragment(ExerciseFragment(),"Exercise")

        viewPager.adapter = adapterViewPager
        tabs.setupWithViewPager(viewPager)

//        val startBtn = findViewById<View>(R.id.ll_start_btn)
//        startBtn.setOnClickListener {
//            val i =Intent(this,ExerciseActivity::class.java)
//            startActivity(i)
//            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
//        }
//        tvBmi.setOnClickListener {
//            val i =Intent(this,ActivityBMI::class.java)
//            startActivity(i)
//            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
//        }
//        tvHistory.setOnClickListener {
//            val i =Intent(this,HistoryActivity::class.java)
//            startActivity(i)
//            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
//        }


    }
}