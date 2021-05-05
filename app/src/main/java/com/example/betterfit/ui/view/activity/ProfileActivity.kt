package com.example.betterfit.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.betterfit.R
import com.example.betterfit.data.entities.Profile
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_input_diet.*
import kotlinx.android.synthetic.main.activity_profile.*




class ProfileActivity : AppCompatActivity() {
    var gender:String?="male"
    var activityLevel:String?="1"
     private var profile: Profile?=null
    private val db by lazy {
        Firebase.firestore
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)



        getGenderAndActivityLevel()

        profileDoneFab.setOnClickListener {
            getFieldsAndUploadData()

        }

    }

    private fun getGenderAndActivityLevel() {
        profileGenderRg.setOnCheckedChangeListener { _, checkedId ->
            gender = if (checkedId == R.id.profileFemaleRb){

                profileFemaleRb.setBackgroundResource(R.drawable.gender_radio_selected_background)
                profileMaleRb.setBackgroundResource(R.drawable.gender_radio_not_selected_background)
                "female"
            } else{
                profileMaleRb.setBackgroundResource(R.drawable.gender_radio_selected_background)
                profileFemaleRb.setBackgroundResource(R.drawable.gender_radio_not_selected_background)
                "male"
            }
        }

        profileActivitySl.addOnChangeListener { _, value, _ ->
            activityLevel = value.toInt().toString()
        }
    }

    private fun getFieldsAndUploadData() {
        val name = profileNameEt.text.toString()
        val age = profileAgeEt.text.toString()
        val height = profileHeighEt.text.toString()
        val weight = profileWeightEt.text.toString()




        Log.d("TEST","$name , $age,  $gender,  $height,  $weight,  $activityLevel,  ")

        profile =Profile(name,age,height,weight,gender!!, activityLevel!!)


        db.collection("profiles").add(profile!!)
            .addOnSuccessListener {
                Toast.makeText(this, "Saved to Database", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(this, "Failed to save on Database ${it.message}", Toast.LENGTH_SHORT).show()
            }

    }
}