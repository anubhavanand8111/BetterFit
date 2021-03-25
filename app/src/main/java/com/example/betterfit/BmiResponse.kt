package com.example.betterfit

import com.google.gson.annotations.SerializedName

data class BmiResponse(

	@field:SerializedName("healthy_bmi_range")
	val healthyBmiRange: String? = null,

	@field:SerializedName("health")
	val health: String? = null,

	@field:SerializedName("bmi")
	val bmi: Double? = null
)
