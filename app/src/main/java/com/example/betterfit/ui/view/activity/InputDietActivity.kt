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



    private var mAge:String=""
    private var mHeight:String=""
    private var mWeight:String=""
    private var mActivityLevel:String ="1"
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
                    val i=Intent(applicationContext,CalorieActivity::class.java)
                    i.putExtra("AGE",mAge)
                    i.putExtra("GENDER",mGender)
                    i.putExtra("HEIGHT",mHeight)
                    i.putExtra("WEIGHT",mWeight)
                    i.putExtra("ACTIVITYLEVEL",mActivityLevel)
                    i.putExtra("GOAL",goal)
                    startActivity(i)

                }
            }
        }

    }


}