package com.example.betterfit.data.repository

import com.example.betterfit.data.api.client.FitnessCalculatorClient

object FitnessCalculatorRepo {

    suspend fun getBmi(age:String,weight:String,height:String)=FitnessCalculatorClient.api.getBmi(age,weight,height)

    suspend fun getCalorie(age: String,gender:String,height: String,weight: String,activitylevel:String,goal:String)=FitnessCalculatorClient.api.getCalorie(age, gender, height, weight, activitylevel, goal)

}