package com.example.betterfit.ui.view.activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.betterfit.R
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_otp.*
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity(), View.OnClickListener {
    var phoneNumber:String?=null
    lateinit var progressDialog:ProgressDialog
    var mVerificationId:String?=null
    private var countDownTimer:CountDownTimer?=null
    var mResendToken:PhoneAuthProvider.ForceResendingToken?=null
    lateinit var callbacks:PhoneAuthProvider.OnVerificationStateChangedCallbacks


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        initViews()

        startVerification()

        verifyBtn.setOnClickListener(this)
        resendBtn.setOnClickListener(this)

    }

    private fun startVerification() {
        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
                .setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        progressDialog=createProgressDialog("Sending a verification code",false)
       progressDialog.show()
        setTimer(60000)


    }

    private fun initViews() {
        phoneNumber = intent.getStringExtra("PHONE_NUMBER")

        verifyEt.text = "Verify $phoneNumber"


        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d("FIREBASE", "onVerificationCompleted:$credential")
                val smsCode =credential.smsCode



                if (!smsCode.isNullOrBlank()){
                    smsCodeEt.setText(smsCode)

                }
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.


                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                progressDialog.dismiss()
                verifyBtn.isEnabled=true
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId
                mResendToken = token
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        progressDialog=createProgressDialog("Please Wait...",false)
        progressDialog.show()
        val mAuth=FirebaseAuth.getInstance()
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener {
                    if (it.isSuccessful){

                        progressDialog.dismiss()

                        startActivity(Intent(this,ProfileActivity::class.java))

                        finish()

                    }
                    else{
                        Toast.makeText(this, "Verification Failed", Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                    }
                }

    }

    private fun setTimer(timeInMilliSeconds: Long) {

        countDownTimer = object :CountDownTimer(timeInMilliSeconds, 1000){
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {

                timerTv.text="Resend SMS after ${millisUntilFinished/1000} seconds"
            }

            override fun onFinish() {
                timerTv.isVisible=false
                resendBtn.isEnabled = true
            }

        }.start()

        }

    override fun onDestroy() {
        super.onDestroy()
        if (countDownTimer!=null)
        countDownTimer!!.cancel()
    }

    override fun onClick(v: View?) {

        when(v){
            verifyBtn->{
                val code =smsCodeEt.text.toString()
                if (code.isNotEmpty() && !mVerificationId.isNullOrEmpty()){

                    val credential=PhoneAuthProvider.getCredential(mVerificationId!!,code)
                    signInWithPhoneAuthCredential(credential)

                }

            }
            resendBtn->{

                if (mResendToken !=null){
                    timerTv.isVisible=true
                    setTimer(60000)
                    progressDialog=createProgressDialog("Sending OTP...",false)
                    progressDialog.show()

                    val options = PhoneAuthOptions.newBuilder(Firebase.auth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                        .setForceResendingToken(mResendToken)
                        .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)
                }

            }
        }
    }

}


fun Context.createProgressDialog(message:String,isCancellable:Boolean):ProgressDialog{
    return ProgressDialog(this).apply {
         setCancelable(isCancellable)
        setMessage(message)
        setCanceledOnTouchOutside(false)
    }
}