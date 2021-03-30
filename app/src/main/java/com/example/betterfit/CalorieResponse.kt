package com.example.betterfit

data class CalorieResponse(
	val lowcarbs: Lowcarbs? = null,
	val calorie: Double? = null,
	val lowfat: Lowfat? = null,
	val balanced: Balanced? = null,
	val highprotein: Highprotein? = null
)

data class Lowcarbs(
	val carbs: Double? = null,
	val protein: Double? = null,
	val fat: Double? = null
)

data class Balanced(
	val carbs: Double? = null,
	val protein: Double? = null,
	val fat: Double? = null
)

data class Highprotein(
	val carbs: Double? = null,
	val protein: Double? = null,
	val fat: Double? = null
)

data class Lowfat(
	val carbs: Double? = null,
	val protein: Double? = null,
	val fat: Double? = null
)

