package com.example.betterfit.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.betterfit.data.repository.FitnessCalculatorRepo
import com.example.betterfit.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class BmiViewModel :ViewModel() {

    fun getUserBmi(age:String,weight:String,height:String)= liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = FitnessCalculatorRepo.getBmi(age, weight, height).body()))
        } catch (ex:Exception){
            emit(Resource.error(data=null,msg = ex.message?:"Error Occured!!"))
        }

    }

}