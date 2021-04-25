package com.example.betterfit.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.betterfit.R
import com.example.betterfit.ui.viewmodel.CalorieViewModel
import com.example.betterfit.utils.Status
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_input_diet.*

class InputDietActivity : AppCompatActivity() {

    private val vm by lazy {
        ViewModelProvider(this).get(CalorieViewModel::class.java)
    }

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

        vm.getUserCalorie(mAge,mGender,mHeight,mWeight,mActivityLevel,goal).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        llPleaseWait.visibility=View.GONE
                        resource.data.let { v ->

                            val calorie=v?.calorie.toString()
                            val protein=v?.balanced?.protein.toString()
                            val fat=v?.balanced?.fat.toString()
                            val carb=v?.balanced?.carbs.toString()

                            val i=Intent(applicationContext,CalorieActivity::class.java)
                            i.putExtra("CALORIE",calorie)
                            i.putExtra("PROTEIN",protein)
                            i.putExtra("FAT",fat)
                            i.putExtra("CARB",carb)
                            startActivity(i)

                        }
                    }

                    Status.ERROR -> {
                        llPleaseWait.visibility=View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                       showPleaseWaitProgressBar()
                    }
                }
            }
        })

    }

    private fun showPleaseWaitProgressBar() {
        calorieProgressBar.max=100
        calorieProgressBar.progress=20
        llPleaseWait.visibility= View.VISIBLE
    }
}