package com.example.betterfit.data.api.client

import com.example.betterfit.data.api.service.FitnessCalculatorService
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FitnessCalculatorClient {

    const val BASE_URL_CALC="https://fitness-calculator.p.rapidapi.com"

    private val gson: Gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_CALC)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    val api = retrofit.create(FitnessCalculatorService::class.java)
}