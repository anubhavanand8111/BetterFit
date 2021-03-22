package com.example.betterfit.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.betterfit.ActivityBMI
import com.example.betterfit.ExerciseActivity
import com.example.betterfit.HistoryActivity
import com.example.betterfit.R
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //val startBtn = findViewById<View>(R.id.ll_start_btn)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }



    override fun onStart() {

        ll_start_btn.setOnClickListener {
            val i =Intent(requireContext(), ExerciseActivity::class.java)
            startActivity(i)
            // overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }
        tvBmi.setOnClickListener {
            val i =Intent(requireContext(), ActivityBMI::class.java)
            startActivity(i)
            // overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }
        tvHistory.setOnClickListener {
            val i = Intent(requireContext(), HistoryActivity::class.java)
            startActivity(i)
            // overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }
        super.onStart()
    }


}