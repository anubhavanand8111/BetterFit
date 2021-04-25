package com.example.betterfit.data.api.service

import com.example.betterfit.data.model.BmiResponse
import com.example.betterfit.data.model.CalorieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val API_KEY_FITNESS_CALCULATOR="5bd2fc0bb0msh5957d818a6c5dabp153f56jsn2aa2f00f7c1e"
interface FitnessCalculatorService {


    @Headers("x-rapidapi-key:$API_KEY_FITNESS_CALCULATOR")
    @GET("bmi")
    suspend fun getBmi(
            @Query("age") age: String,
            @Query("weight") weight: String,
            @Query("height") height: String
    ):Response<BmiResponse>


    @Headers("x-rapidapi-key:$API_KEY_FITNESS_CALCULATOR")
    @GET("/macrocalculator")
    suspend fun getCalorie(
        @Query("age") age: String,
        @Query("gender") gender: String,
        @Query("height") height: String,
        @Query("weight") weight: String,
        @Query("activitylevel") activitylevel: String,
        @Query("goal") goal: String,


        ) :Response<CalorieResponse>

}