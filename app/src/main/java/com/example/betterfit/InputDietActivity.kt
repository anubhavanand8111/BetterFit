package com.example.betterfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.betterfit.Services.FitnessCalculatorApiService
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_b_m_i.*
import kotlinx.android.synthetic.main.activity_input_diet.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InputDietActivity : AppCompatActivity() {

    private var mAge:String=""
    private var mHeight:String=""
    private var mWeight:String=""
    private var mActivityLevel:String ="0"
    private var mGender:String="male"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_diet)
        val goal = intent.getStringExtra("GOAL")


        genderRg.setOnCheckedChangeListener { _, checkedId ->
            mGender = if (checkedId == R.id.femaleRb){

                femaleRb.setBackgroundResource(R.drawable.gender_radio_selected_background)
                maleRb.setBackgroundResource(R.drawable.gender_radio_not_selected_background)
                "female"
            } else{
                maleRb.setBackgroundResource(R.drawable.gender_radio_selected_background)
                femaleRb.setBackgroundResource(R.drawable.gender_radio_not_selected_background)
                "male"
            }
        }



        activitySl.addOnChangeListener { _, value, _ ->
            mActivityLevel = value.toInt().toString()
        }

        detailsFab.setOnClickListener {
            mAge = ageTv.text.toString()
            mHeight = heightTv.text.toString()
            mWeight =weightTv.text.toString()

            if(mAge.isEmpty() || mHeight.isEmpty() || mWeight.isEmpty()){
                Snackbar.make(detailRootLayout,"Enter all the Details",Snackbar.LENGTH_SHORT).show()

            }
            else{

                if (goal != null) {
                    getCalories(mAge,mGender,mHeight,mWeight,mActivityLevel,goal)
                }
            }
        }

    }

    private fun getCalories(mAge: String, mGender: String, mHeight: String, mWeight: String, mActivityLevel: String, goal: String) {


        GlobalScope.launch(Dispatchers.Main) {

            val response = withContext(Dispatchers.IO) {
                FitnessCalculatorApiService.bmiInstance.getCalorie(mAge,mGender, mHeight,mWeight,mActivityLevel,goal)
            }
            if (response.isSuccessful){
                Log.d("CALORIE", "calories: ${response.body()?.calorie.toString()}")

            }
            else{
                Log.d("CALORIE", "Error Fetching Bmi")

            }
        }
    }
}