package com.example.betterfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var restTimer:CountDownTimer?=null
    private var restProgress=0
    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgress=0
    private var tts:TextToSpeech?=null
    private var exerciseList : ArrayList<ExerciseModel>?=null
    private var currentPosition=-1
    private var exerciseAdapter:ExerciseStatusAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        val toolbar = findViewById<Toolbar>(R.id.exercise_activity_toolbar)

        exerciseList = Constants.defaultExcerciseList()
        tts = TextToSpeech(this,this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()

        }
        setupRestView()
        setupStatusRecyclerView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right)
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


                currentPosition++
                exerciseList!![currentPosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
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
                if (currentPosition< exerciseList!!.size-1){
                    exerciseList!![currentPosition].setIsSelected(false)
                    exerciseList!![currentPosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()


                }
                else{
                    val i=Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(i)
                    finish()
                }
            }

        }.start()

    }


    private fun setupRestView(){
        llRestView.visibility=View.VISIBLE
        llExerciseView.visibility=View.GONE
        if (restTimer!=null){
            restTimer!!.cancel()
            restProgress=0

        }
        tvNextExercise.text = exerciseList?.get(currentPosition+1)?.getName() ?: "fgh"
        setRestProgressBar()
    }
    private fun setupExerciseView(){
        llRestView.visibility=View.GONE
        llExerciseView.visibility=View.VISIBLE
        if (exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgress=0

        }
        speakOut(exerciseList!![currentPosition].getName())
        ivImage.setImageResource(exerciseList!![currentPosition].getImage())
        tvExerciseName.text = exerciseList!![currentPosition].getName()

        setExerciseProgressBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer!==null){
            restTimer!!.cancel()
            restProgress=0
        }
        if(exerciseTimer!==null){
            exerciseTimer!!.cancel()
            exerciseProgress=0
        }
        if (tts!==null){
            tts!!.stop()
            tts!!.shutdown()
        }
    }

    override fun onInit(status: Int) {
        if (status==TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this, "Locale.US is Not supported", Toast.LENGTH_SHORT).show()
            }
        }
        else
        {
            Toast.makeText(this, "Text To speech Initialisation failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun speakOut(text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")

    }

    private fun setupStatusRecyclerView(){
        exerciseAdapter= ExerciseStatusAdapter(exerciseList!!)
        val layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL,false)
        rvStatus.layoutManager = layoutManager
        rvStatus.adapter=exerciseAdapter
    }
}






























