package com.example.betterfit.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.betterfit.data.repository.FitnessCalculatorRepo
import com.example.betterfit.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class CalorieViewModel:ViewModel() {

    fun getUserCalorie(age: String,gender:String,height: String,weight: String,activitylevel:String,goal:String) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = FitnessCalculatorRepo.getCalorie(age, gender, height, weight, activitylevel, goal).body()))
        } catch (ex: Exception){
            emit(Resource.error(data=null,msg = ex.message?:"Error Occured!!"))
        }
    }
}