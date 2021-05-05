package com.example.betterfit.ui.view.activity

import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.betterfit.R
import com.google.android.gms.auth.api.credentials.*
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {
    private lateinit var phoneNumber:String
    private lateinit var countryCode:String
    private lateinit var userName:String
    private lateinit var password:String

    companion object {
        var CREDENTIAL_PICKER_REQUEST = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        selectPhoneNumber()

        signupBtn.setOnClickListener {

            userName=userNameEt.text.toString()
            phoneNumber= ccp.selectedCountryCodeWithPlus.toString() +  phoneNumberEt.text.toString()
            password=passwordEt.text.toString()

            startActivity(Intent(this,OtpActivity::class.java).putExtra("PHONE_NUMBER", phoneNumber))
            finish()
        }
    }

    private fun selectPhoneNumber() {

        val hintRequest =HintRequest.Builder().setPhoneNumberIdentifierSupported(true).build()
        val option = CredentialsOptions.Builder().forceEnableSaveDialog().build()

        val credentialClient=Credentials.getClient(application,option)
        val intent = credentialClient.getHintPickerIntent(hintRequest)
        try {
            startIntentSenderForResult(intent.intentSender, CREDENTIAL_PICKER_REQUEST,null,0,0,0,Bundle())
        }catch (e:IntentSender.SendIntentException){
            Toast.makeText(this, "${e.printStackTrace()}", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== CREDENTIAL_PICKER_REQUEST && resultCode== RESULT_OK){
            val credential:Credential?=data?.getParcelableExtra(Credential.EXTRA_KEY)
            credential?.apply {
                phoneNumberEt.setText(credential.id.substring(3))
            }
        }else if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == CredentialsApi.ACTIVITY_RESULT_NO_HINTS_AVAILABLE) {
            Toast.makeText(this, "No phone numbers found", Toast.LENGTH_LONG).show();
        }
    }
}