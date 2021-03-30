package com.example.betterfit.Services

import com.example.betterfit.BmiResponse
import com.example.betterfit.CalorieResponse
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

const val BASE_URL_CALC="https://fitness-calculator.p.rapidapi.com"
const val API_KEY_CALC="5bd2fc0bb0msh5957d818a6c5dabp153f56jsn2aa2f00f7c1e"

interface FitnessCalculatorApiServiceInterface {
    @Headers("x-rapidapi-key:$API_KEY_CALC")
    @GET("/bmi")
    suspend fun getBmi(
        @Query("age") age: String,
        @Query("weight") weight: String,
        @Query("height") height: String,

    ) :Response<BmiResponse>

    @Headers("x-rapidapi-key:$API_KEY_CALC")
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
    object FitnessCalculatorApiService{
         val bmiInstance:FitnessCalculatorApiServiceInterface

        init {
            val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL_CALC).addConverterFactory(GsonConverterFactory.create(gson)).build()

            bmiInstance = retrofit.create(FitnessCalculatorApiServiceInterface::class.java)
        }
    }

