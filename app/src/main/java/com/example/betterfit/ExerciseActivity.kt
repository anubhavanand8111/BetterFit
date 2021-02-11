package com.example.betterfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_exercise.*

class ExerciseActivity : AppCompatActivity() {

    private var restTimer:CountDownTimer?=null
    private var restProgress=0
    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgress=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        val toolbar = findViewById<Toolbar>(R.id.exercise_activity_toolbar)
        setupRestView()


        setSupportActionBar(toolbar)
        val actionbar=supportActionBar
        if (actionbar!=null){
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            onBackPressed()

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

    private fun setRestProgressBar(){
        progressBar.progress= restProgress
        restTimer= object:CountDownTimer(10000,1000){
            override fun onTick(millisUntilFinished: Long) {

                restProgress++
                progressBar.progress=10-restProgress
                tvTimer.text= (10-restProgress).toString()


            }

            override fun onFinish() {

                Toast.makeText(this@ExerciseActivity, "Over", Toast.LENGTH_SHORT).show()
                llRestView.visibility=View.GONE
                llExerciseView.visibility=View.VISIBLE
                setupExerciseView()
            }

        }.start()

    }

    private fun setExerciseProgressBar(){
        progressBarExcercise.progress= exerciseProgress
        exerciseTimer= object:CountDownTimer(30000,1000){
            override fun onTick(millisUntilFinished: Long) {

                exerciseProgress++
                progressBarExcercise.progress=30-exerciseProgress
                tvExerciseTimer.text= (30-exerciseProgress).toString()


            }

            override fun onFinish() {

                Toast.makeText(this@ExerciseActivity, "Over Exercise", Toast.LENGTH_SHORT).show()
            }

        }.start()

    }


    private fun setupRestView(){
        if (restTimer!=null){
            restTimer!!.cancel()
            restProgress=0

        }
        setRestProgressBar()
    }
    private fun setupExerciseView(){
        if (exerciseTimer!=null){
            exerciseTimer!!.cancel()
            restProgress=0

        }
        setExerciseProgressBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer!==null){
            restTimer!!.cancel()
            restProgress=0
        }
    }
}