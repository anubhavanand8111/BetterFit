package com.example.betterfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_calorie.*

class CalorieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calorie)
        val calorie = intent.getStringExtra("CALORIE")
        val protein = intent.getStringExtra("PROTEIN")
        val fat = intent.getStringExtra("FAT")
        val carb = intent.getStringExtra("CARB")

        result.text="calorie: $calorie \n protein: $protein \n fat: $fat \n carb: $carb"
    }
}