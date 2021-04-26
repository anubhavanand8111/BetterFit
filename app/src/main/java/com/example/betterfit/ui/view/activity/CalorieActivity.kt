package com.example.betterfit.ui.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.betterfit.R
import com.example.betterfit.ui.viewmodel.CalorieViewModel
import com.example.betterfit.utils.Status
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_b_m_i.*
import kotlinx.android.synthetic.main.activity_calorie.*
import kotlinx.android.synthetic.main.activity_input_diet.*
import kotlinx.android.synthetic.main.macro_nutrients_bottom_sheet.*


class CalorieActivity : AppCompatActivity() {

    var protein:String?=null
    var fat:String?=null
    var carb:String?=null
    var calorie:String?=null

    var pieData: PieData? = null
    var pieDataSet: PieDataSet? = null
    lateinit var pieEntries:ArrayList<PieEntry>
    lateinit var PieEntryLabels:ArrayList<Float>

    private val vm by lazy {
        ViewModelProvider(this).get(CalorieViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calorie)

        setSupportActionBar(calorieToolbar)
        val actionbar = supportActionBar
        if (actionbar!=null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title= "Calorie"
        }
        val mAge = intent.getStringExtra("AGE")
        val mGender = intent.getStringExtra("GENDER")
        val mHeight = intent.getStringExtra("HEIGHT")
        val mWeight = intent.getStringExtra("WEIGHT")
        val mActivityLevel = intent.getStringExtra("ACTIVITYLEVEL")
        val mGoal = intent.getStringExtra("GOAL")

        getCaloriesBalanced(mAge!!,mGender!!,mHeight!!,mWeight!!,mActivityLevel!!,mGoal!!)


        getBalanceDietEntries()
        setChart()



        selectDietTypeTv.setOnClickListener {
            val bsd = BottomSheetDialog(this)
            bsd.setContentView(R.layout.macro_nutrients_bottom_sheet)

            bsd.balancedDietTv.setOnClickListener {
              getCaloriesBalanced(mAge, mGender, mHeight, mWeight, mActivityLevel, mGoal)
                getBalanceDietEntries()
                pieChart.clear()
                setChart()
                dietTypeTv.text="Balanced Diet (40:30:30)"
                bsd.dismiss()
            }
            bsd.lowCarbsDietTv.setOnClickListener {
              getCaloriesLowCarbs(mAge, mGender, mHeight, mWeight, mActivityLevel, mGoal)
                getLowCarbsEntries()
                pieChart.clear()
                setChart()
                dietTypeTv.text="Low Carbs Diet (35:25:40)"
                bsd.dismiss()
            }

            bsd.lowFatDietTv.setOnClickListener {
              getCaloriesLowFat(mAge, mGender, mHeight, mWeight, mActivityLevel, mGoal)
                getLowFatEntries()
                pieChart.clear()
                setChart()
                this.dietTypeTv.text="Low Fat Diet (50:30:20)"
                bsd.dismiss()
            }
            bsd.highProteinDietTv.setOnClickListener {
               getCaloriesHighProtein(mAge, mGender, mHeight, mWeight, mActivityLevel, mGoal)
                getHighProteinEntries()
                pieChart.clear()
                setChart()
                dietTypeTv.text="High Protein Diet (30:40:30)"
                bsd.dismiss()
            }

            bsd.show()
        }

    }

    private fun getBalanceDietEntries() {
        pieEntries = ArrayList()
        pieEntries.add(PieEntry(BalancedDiet.protein, "Protein"))
        pieEntries.add(PieEntry(BalancedDiet.fat, "Fat"))
        pieEntries.add(PieEntry(BalancedDiet.carb, "Carb"))

    }
    private fun getLowFatEntries() {
        pieEntries = ArrayList()
        pieEntries.add(PieEntry(LowFat.protein, "Protein"))
        pieEntries.add(PieEntry(LowFat.fat, "Fat"))
        pieEntries.add(PieEntry(LowFat.carb, "Carb"))

    }
    private fun getLowCarbsEntries() {
        pieEntries = ArrayList()
        pieEntries.add(PieEntry(LowCarbs.protein, "Protein"))
        pieEntries.add(PieEntry(LowCarbs.fat, "Fat"))
        pieEntries.add(PieEntry(LowCarbs.carb, "Carb"))

    }
    private fun getHighProteinEntries() {
        pieEntries = ArrayList()
        pieEntries.add(PieEntry(HighProtein.protein, "Protein"))
        pieEntries.add(PieEntry(HighProtein.fat, "Fat"))
        pieEntries.add(PieEntry(HighProtein.carb, "Carb"))

    }

    private fun setChart(){

        pieDataSet = PieDataSet(pieEntries, "\t Macro Nutrients Proportion")
        pieData = PieData(pieDataSet)
        pieChart.data=pieData
        pieDataSet!!.setColors(*ColorTemplate.MATERIAL_COLORS)
        pieDataSet!!.sliceSpace = 2f
        pieDataSet!!.valueTextColor = Color.WHITE
        pieDataSet!!.valueTextSize = 10f

        pieDataSet!!.sliceSpace = 5f
        pieChart.animateY(1000);

        val legend: Legend = pieChart.legend
        legend.isEnabled=false

        Log.d("TEST","check")
    }

    object BalancedDiet{
        const val protein =30.0f
        const val fat =30.0f
        const val carb =40.0f
    }

    object LowFat{
        const val protein =30.0f
        const val fat =20.0f
        const val carb =50.0f
    }

    object LowCarbs{
        const val protein =35.0f
        const val fat =40.0f
        const val carb =25.0f
    }

    object HighProtein{
        const val protein =40.0f
        const val fat =30.0f
        const val carb =30.0f
    }

    private fun getCaloriesBalanced(mAge: String, mGender: String, mHeight: String, mWeight: String, mActivityLevel: String, goal: String) {

        vm.getUserCalorie(mAge,mGender,mHeight,mWeight,mActivityLevel,goal).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        calorieProgressBar.visibility = View.GONE
                        resource.data.let { v ->

                            calorie = v?.calorie.toString()
                            protein = v?.balanced?.protein.toString()
                            fat = v?.balanced?.fat.toString()
                            carb = v?.balanced?.carbs.toString()

                            calorieTv.text=calorie?.toFloat()?.toInt().toString()
                            proteinGramTv.text=protein?.substring(0,5)+" gm"
                            carbsGramTv.text=carb?.substring(0,5)+" gm"
                            fatsGramTv.text=fat?.substring(0,5)+" gm"

                        }
                    }

                    Status.ERROR -> {
                       calorieProgressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        calorieProgressBar.visibility = View.VISIBLE

                    }
                }
            }
        })

    }

    private fun getCaloriesLowFat(mAge: String, mGender: String, mHeight: String, mWeight: String, mActivityLevel: String, goal: String) {

        vm.getUserCalorie(mAge,mGender,mHeight,mWeight,mActivityLevel,goal).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        calorieProgressBar.visibility = View.GONE
                        resource.data.let { v ->

                            calorie = v?.calorie.toString()
                            protein = v?.lowfat?.protein.toString()
                            fat = v?.lowfat?.fat.toString()
                            carb = v?.lowfat?.carbs.toString()

                            calorieTv.text=calorie?.toFloat()?.toInt().toString()
                            proteinGramTv.text=protein?.substring(0,5)+" gm"
                            carbsGramTv.text=carb?.substring(0,5)+" gm"
                            fatsGramTv.text=fat?.substring(0,5)+" gm"

                        }
                    }

                    Status.ERROR -> {
                        calorieProgressBar.visibility = View.GONE

                    }
                    Status.LOADING -> {
                        calorieProgressBar.visibility=View.VISIBLE
                    }
                }
            }
        })

    }
    private fun getCaloriesHighProtein(mAge: String, mGender: String, mHeight: String, mWeight: String, mActivityLevel: String, goal: String) {

        vm.getUserCalorie(mAge,mGender,mHeight,mWeight,mActivityLevel,goal).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        calorieProgressBar.visibility = View.GONE
                        resource.data.let { v ->

                            calorie = v?.calorie.toString()
                            protein = v?.highprotein?.protein.toString()
                            fat = v?.highprotein?.fat.toString()
                            carb = v?.highprotein?.carbs.toString()

                            calorieTv.text=calorie?.toFloat()?.toInt().toString()
                            proteinGramTv.text=protein?.substring(0,5)+" gm"
                            carbsGramTv.text=carb?.substring(0,5)+" gm"
                            fatsGramTv.text=fat?.substring(0,5)+" gm"

                        }
                    }

                    Status.ERROR -> {
                        calorieProgressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        calorieProgressBar.visibility=View.VISIBLE
                    }
                }
            }
        })

    }

    private fun getCaloriesLowCarbs(mAge: String, mGender: String, mHeight: String, mWeight: String, mActivityLevel: String, goal: String) {

        vm.getUserCalorie(mAge,mGender,mHeight,mWeight,mActivityLevel,goal).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        calorieProgressBar.visibility = View.GONE
                        resource.data.let { v ->

                            calorie = v?.calorie.toString()
                            protein = v?.lowcarbs?.protein.toString()
                            fat = v?.lowcarbs?.fat.toString()
                            carb = v?.lowcarbs?.carbs.toString()

                            calorieTv.text=calorie?.toFloat()?.toInt().toString()
                            proteinGramTv.text=protein?.substring(0,5)+" gm"
                            carbsGramTv.text=carb?.substring(0,5)+" gm"
                            fatsGramTv.text=fat?.substring(0,5)+" gm"

                        }
                    }

                    Status.ERROR -> {
                        calorieProgressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        calorieProgressBar.visibility=View.VISIBLE
                    }
                }
            }
        })

    }



}