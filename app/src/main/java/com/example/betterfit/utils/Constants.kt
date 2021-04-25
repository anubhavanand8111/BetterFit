package com.example.betterfit.utils

import com.example.betterfit.data.model.ExerciseModel
import com.example.betterfit.R

class Constants {
    companion object{
        fun defaultExcerciseList():ArrayList<ExerciseModel>{
            val exerciseList= ArrayList<ExerciseModel>()

            val jumpingJacks = ExerciseModel(1,"Jumping Jacks", R.drawable.ripple_start_btn,false,false,"H1F-UfC8Mb8")
            exerciseList.add(jumpingJacks)

            val wallSit = ExerciseModel(2,"Wall Sit", R.drawable.banner,false,false,"2VuLBYrgG94")
            exerciseList.add(wallSit)

            val test = ExerciseModel(3,"Test Sit", R.drawable.ripple_start_btn,false,false,"1piFN_ioMVI")
            exerciseList.add(test)

            return exerciseList
        }
    }
}