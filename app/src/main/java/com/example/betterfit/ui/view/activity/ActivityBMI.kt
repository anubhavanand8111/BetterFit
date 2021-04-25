package com.example.betterfit.ui.view.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.betterfit.R
import com.example.betterfit.data.api.service.FitnessCalculatorApiService
import com.example.betterfit.ui.viewmodel.BmiViewModel
import com.example.betterfit.utils.Status

import kotlinx.android.synthetic.main.activity_b_m_i.*
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivityBMI : AppCompatActivity() {


    private val vm by lazy {
        ViewModelProvider(this).get(BmiViewModel::class.java)
    }

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
        //showBmiProgress1()

        vm.getUserBmi("22",weight,height).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        bmiProgressBar1.visibility=View.GONE
                        resource.data.let { v ->
                            tvBmi.text = "Your Bmi: ${v?.bmi.toString().substring(0,5)}"
                            healthTv1.text = "Your Health: ${v?.health.toString()}"

                        }
                    }

                    Status.ERROR -> {
                        bmiProgressBar1.visibility=View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        showBmiProgress1()
                    }
                }
            }
        })


    }

    private fun showBmiProgress1() {

        bmiProgressBar1.max=100
        bmiProgressBar1.progress=20
        bmiProgressBar1.visibility=View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    private fun getBmitv2(weight:String, height:String)   {
        vm.getUserBmi("22",weight,height).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        bmiProgressBar2.visibility=View.GONE
                        resource.data.let { v ->
                            tvBmi2.text = "Your Bmi: ${v?.bmi.toString().substring(0,5)}"
                            healthTv2.text = "Your Health: ${v?.health.toString()}"

                        }
                    }

                    Status.ERROR -> {
                        bmiProgressBar2.visibility=View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        showBmiProgress2()
                    }
                }
            }
        })

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