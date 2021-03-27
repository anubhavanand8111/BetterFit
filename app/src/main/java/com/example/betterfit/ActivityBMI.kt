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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal
import java.math.RoundingMode

class ActivityBMI : AppCompatActivity() {


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
        makeMetricUnitVisible()

        calculateBtn.setOnClickListener {
           tvBmi.text =""
            healthTv1.text=""
            val weight: String = etWeight.text.toString()
            val height: String = etHeight.text.toString()
            if (weight.isEmpty() || height.isEmpty()){
                Toast.makeText(this, "Please Enter Value", Toast.LENGTH_SHORT).show()
            }

            else{
              getBmitv1(weight,height)

            }
        }

        calculateBtn2.setOnClickListener {
            tvBmi2.text =""
            healthTv2.text=""
            val weight : String = etWeight2.text.toString()
            val heightFeet : String = etHeightFeet.text.toString()
            val heightInch : String = etHeightInch.text.toString()
            val height = (2.54 * (heightFeet.toDouble()*12+heightInch.toDouble())).toString()

            if (weight.isEmpty() || heightFeet.isEmpty() || heightInch.isEmpty()){
                Toast.makeText(this, "Please Enter Value", Toast.LENGTH_SHORT).show()
            }
            if (etHeightInch.text.toString().toInt()>11){
                Toast.makeText(this, "Inch should be less than 12", Toast.LENGTH_SHORT).show()
            }
            else{
                getBmitv2(weight,height)


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

    @SuppressLint("SetTextI18n")
    private fun getBmitv1(weight:String, height:String)   {
        showBmiProgress1()

       GlobalScope.launch(Dispatchers.Main) {

           val response = withContext(Dispatchers.IO) {
               FitnessCalculatorApiService.bmiInstance.getBmi("22",weight, height)
           }
           if (response.isSuccessful){
               bmiProgressBar1.visibility=View.GONE
               tvBmi.text = "Your BMI: ${response.body()?.bmi.toString().substring(0,4)}"
               healthTv1.text="Your Health: ${response.body()?.health.toString()}"
           }
           else{
               tvBmi.text = "Error Fetching Bmi"
           }
       }

    }

    private fun showBmiProgress1() {

        bmiProgressBar1.max=100
        bmiProgressBar1.progress=20
        bmiProgressBar1.visibility=View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    private fun getBmitv2(weight:String, height:String)   {
        showBmiProgress2()
        GlobalScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO) {
                FitnessCalculatorApiService.bmiInstance.getBmi("22",weight, height)
            }
            if (response.isSuccessful){
                bmiProgressBar2.visibility=View.GONE
                tvBmi2.text = "Your BMI: ${response.body()?.bmi.toString().substring(0,4)}"
                healthTv2.text="Your Health: ${response.body()?.health.toString()}"
            }
            else{
                tvBmi2.text = "Error Fetching Bmi"
            }
        }

    }

    private fun showBmiProgress2() {
        bmiProgressBar2.max=100
        bmiProgressBar2.progress=20
        bmiProgressBar2.visibility=View.VISIBLE
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