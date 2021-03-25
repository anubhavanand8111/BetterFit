package com.example.betterfit

import android.annotation.SuppressLint
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.betterfit.Services.FitnessCalculatorApiService

import kotlinx.android.synthetic.main.activity_b_m_i.*
import kotlinx.android.synthetic.main.fragment_bottom.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal
import java.math.RoundingMode

class ActivityBMI : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_m_i)

        setSupportActionBar(bmiToolbar)
        val actionbar = supportActionBar
        if (actionbar!=null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title= "Calculate BMI"
        }
        bmiToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        calculateBtn.setOnClickListener {
            val weight: String = etWeight.text.toString()
            val height: String = etHeight.text.toString()
            if (weight.isEmpty() || height.isEmpty()){
                Toast.makeText(this, "Please Enter Value", Toast.LENGTH_SHORT).show()
            }
            else{
                getBmi()
                var bmi= weight.toDouble()/(height.toFloat() * height.toDouble())*10000

                tvBmi.text ="Your BMI is ${bmi.toString()}"
            }
        }

        calculateBtn2.setOnClickListener {

            val weight : String = etWeight2.text.toString()
            val heightFeet : String = etHeightFeet.text.toString()
            val heightInch : String = etHeightInch.text.toString()
            val height = 2.54 * (heightFeet.toDouble()*12+heightInch.toDouble())

            if (weight.isEmpty() || heightFeet.isEmpty() || heightInch.isEmpty()){
                Toast.makeText(this, "Please Enter Value", Toast.LENGTH_SHORT).show()
            }
            else{
                var bmi= weight.toDouble()/(height.toFloat() * height.toDouble())*10000

                tvBmi2.text ="Your BMI is ${bmi.toString()}"
            }

        }

         radioGroup.setOnCheckedChangeListener { _, checkedId ->

             if (checkedId == R.id.metricUnit){

                 makeMetricUnitVisible()
             }
             else{
                 makeFeetricUnitVisible()
             }
         }


    }

    private fun getBmi() {
        GlobalScope.launch { val bmi=FitnessCalculatorApiService.bmiInstance.getBmi("22","95","172")
            bmi.enqueue(object :Callback<BmiResponse>{
                override fun onResponse(call: Call<BmiResponse>, response: Response<BmiResponse>) {
                    val bmi = response.body().toString()
                    Log.d("API",bmi)
                }
               
                override fun onFailure(call: Call<BmiResponse>, t: Throwable) {
                    Log.d("API","error")
                }

            }) }

    }

    private fun makeFeetricUnitVisible() {
        heightInCm.visibility = View.GONE
        heightInFoot.visibility = View.VISIBLE
    }

    private fun makeMetricUnitVisible() {
        heightInCm.visibility = View.VISIBLE
        heightInFoot.visibility = View.GONE
        System.out.print("cc")
    }


}