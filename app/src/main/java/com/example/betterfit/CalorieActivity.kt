package com.example.betterfit

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_calorie.*


class CalorieActivity : AppCompatActivity() {


    var pieData: PieData? = null
    var pieDataSet: PieDataSet? = null
    lateinit var pieEntries:ArrayList<PieEntry>
    lateinit var PieEntryLabels:ArrayList<Float>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calorie)
        val calorie = intent.getStringExtra("CALORIE")
        val protein = intent.getStringExtra("PROTEIN")
        val fat = intent.getStringExtra("FAT")
        val carb = intent.getStringExtra("CARB")

        //result.text="calorie: $calorie \n protein: $protein \n fat: $fat \n carb: $carb"
        calorieTv.text=calorie?.toFloat()?.toInt().toString()
        getBalanceDietEntries()



        pieDataSet = PieDataSet(pieEntries, "\t Macro Nutrients Proportion")
        pieData = PieData(pieDataSet)
        pieChart.data=pieData
        pieDataSet!!.setColors(*ColorTemplate.MATERIAL_COLORS)
        pieDataSet!!.sliceSpace = 2f
        pieDataSet!!.valueTextColor = Color.WHITE
        pieDataSet!!.valueTextSize = 10f

        pieDataSet!!.sliceSpace = 5f

        val legend: Legend = pieChart.legend
        legend.isEnabled=false

        //Pie chart Description
        proteinGramTv.text=protein?.substring(0,5)+" gm"
        carbsGramTv.text=carb?.substring(0,5)+" gm"
        fatsGramTv.text=fat?.substring(0,5)+" gm"


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

}