package com.example.betterfit.Services

import com.example.betterfit.BmiResponse
import retrofit2.Call
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
    fun getBmi(
        @Query("age") age: String,
        @Query("weight") weight: String,
        @Query("height") height: String,

    ) :Call<BmiResponse>


}
    object FitnessCalculatorApiService{
         val bmiInstance:FitnessCalculatorApiServiceInterface
        init {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL_CALC).addConverterFactory(GsonConverterFactory.create()).build()

            bmiInstance = retrofit.create(FitnessCalculatorApiServiceInterface::class.java)
        }
    }

