package com.example.betterfit

class Constants {
    companion object{
        fun defaultExcerciseList():ArrayList<ExerciseModel>{
            val exerciseList= ArrayList<ExerciseModel>()

            val jumpingJacks = ExerciseModel(1,"Jumping Jacks",R.drawable.ripple_start_btn,false,false)
            exerciseList.add(jumpingJacks)

            val wallSit = ExerciseModel(2,"Wall Sit",R.drawable.banner,false,false)
            exerciseList.add(wallSit)

            val test = ExerciseModel(3,"Test Sit",R.drawable.ripple_start_btn,false,false)
            exerciseList.add(test)

            return exerciseList
        }
    }
}