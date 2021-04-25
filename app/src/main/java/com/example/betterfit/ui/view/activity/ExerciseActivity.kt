package com.example.betterfit.ui.view.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.betterfit.*
import com.example.betterfit.data.model.ExerciseModel
import com.example.betterfit.ui.adapter.ExerciseStatusAdapter
import com.example.betterfit.ui.view.fragments.BottomFragment
import com.example.betterfit.utils.Constants
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.custom_dialog_back.*
import kotlinx.android.synthetic.main.fragment_bottom.*

import java.util.*


class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var restTimer:CountDownTimer?=null
    private var restProgress=0
    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgress=0
    private var tts:TextToSpeech?=null
    private var exerciseList : ArrayList<ExerciseModel>?=null
    private var currentPosition=-1
    private var exerciseAdapter: ExerciseStatusAdapter?=null

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        val toolbar = findViewById<Toolbar>(R.id.exercise_activity_toolbar)

        exerciseList = Constants.defaultExcerciseList()
        tts = TextToSpeech(this, this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            customDialog()


        }
        setupRestView()
        setupStatusRecyclerView()



        btnViewTutorial.setOnClickListener {

            val bundle = Bundle()
            val videoid = exerciseList!![currentPosition+1].getExerciseLink()

            bundle.putString("id",videoid)

            restTimer?.cancel()
            BottomFragment().apply {
                show(supportFragmentManager, "BottomSheet")
                arguments=bundle

            }



        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
                R.anim.slide_in_left,
                R.anim.slide_out_right
        )
    }

    private fun setRestProgressBar(){
        progressBar.progress= restProgress
        restTimer= object:CountDownTimer(10000, 1000){
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
        exerciseTimer= object:CountDownTimer(30000, 1000){
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
                    val i=Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(i)
                    finish()
                    overridePendingTransition(
                            R.anim.slide_in_left,
                            R.anim.slide_out_right
                    )
                }
            }

        }.start()

    }


    fun setupRestView(){
        btnViewTutorial.visibility=View.VISIBLE
        llRestView.visibility=View.VISIBLE
        llExerciseView.visibility=View.GONE
        if (restTimer!=null){
            restTimer!!.cancel()
            restProgress=0

        }
        tvNextExercise.text = exerciseList?.get(currentPosition + 1)?.getName() ?: "fgh"
        setRestProgressBar()
    }
    private fun setupExerciseView(){
        btnViewTutorial.visibility=View.GONE
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

    private fun speakOut(text: String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")

    }

    private fun setupStatusRecyclerView(){
        exerciseAdapter= ExerciseStatusAdapter(exerciseList!!)
        val layoutManager = LinearLayoutManager(
            applicationContext,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rvStatus.layoutManager = layoutManager
        rvStatus.adapter=exerciseAdapter
    }
    private fun customDialog(){
        val customDialog = Dialog(this)

        customDialog.setContentView(R.layout.custom_dialog_back)
        customDialog.cdYes.setOnClickListener {
            finish()
            overridePendingTransition(
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
            )
            customDialog.dismiss()
        }

        customDialog.cdNo.setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()
    }

    override fun onPause() {
        restTimer?.cancel()
        super.onPause()
    }

    override fun onResume() {
        setupRestView()
        super.onResume()
    }
}






























