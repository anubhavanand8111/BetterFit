package com.example.betterfit.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.betterfit.InputDietActivity
import com.example.betterfit.R

import kotlinx.android.synthetic.main.fragment_diet.*
import kotlinx.android.synthetic.main.fragment_diet.view.*


class DietFragment : Fragment(),View.OnClickListener {

   var goal:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_diet, container, false)
        view.maintainCv.setOnClickListener(this)
        view.mildlossCv.setOnClickListener(this)
        view.weightlossCv.setOnClickListener(this)
        view.extremelossCv.setOnClickListener(this)
        view.mildgainCv.setOnClickListener(this)
        view.weightgainCv.setOnClickListener(this)
        view.extremegainCv.setOnClickListener(this)
        view.goalFab.setOnClickListener(this)

        return view
    }

    @SuppressLint("UseCompatLoadingForDrawables", "ResourceType")
    override fun onClick(v: View) {
        when(v.id){
            R.id.maintainCv -> {
                removeForeground()
                maintainCv.foreground =resources.getDrawable(R.drawable.card_selected_border)
                goal= "maintain"
            }
            R.id.mildlossCv -> {
                removeForeground()
                mildlossCv.foreground =resources.getDrawable(R.drawable.card_selected_border)
                goal= "mildlose"
            }
            R.id.weightlossCv -> {
                removeForeground()
                weightlossCv.foreground =resources.getDrawable(R.drawable.card_selected_border)
                goal= "weightlose"
            }
            R.id.extremelossCv -> {
                removeForeground()
                extremelossCv.foreground =resources.getDrawable(R.drawable.card_selected_border)
                goal= "extremelose"
            }
            R.id.mildgainCv -> {
                removeForeground()
                mildgainCv.foreground =resources.getDrawable(R.drawable.card_selected_border)
                goal= "mildgain"
            }
            R.id.weightgainCv -> {
                removeForeground()
                weightgainCv.foreground =resources.getDrawable(R.drawable.card_selected_border)
                goal= "weightgain"
            }
            R.id.extremegainCv -> {
                removeForeground()
                extremegainCv.foreground =resources.getDrawable(R.drawable.card_selected_border)
                goal= "extremegain"
            }
            R.id.goalFab -> {
               val i =Intent(requireContext(),InputDietActivity::class.java)
                i.putExtra("GOAL",goal)
                startActivity(i)
            }
            else->{
                Toast.makeText(requireContext(), "test", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun removeForeground() {
        goalFab.visibility = View.VISIBLE
        maintainCv.foreground=null
        mildlossCv.foreground=null
        weightlossCv.foreground=null
        extremelossCv.foreground=null
        mildgainCv.foreground=null
        weightgainCv.foreground=null
        extremegainCv.foreground=null

    }


}