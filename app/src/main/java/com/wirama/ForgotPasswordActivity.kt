package com.wirama

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.wirama.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitButton.setOnClickListener {
            val email: String = binding.emailInput.text.toString()
            if(email.isEmpty()) {
                binding.emailInput.error = "Please fill your email"
                binding.emailInput.requestFocus()
                return@setOnClickListener
            }

            Log.d("test", "submit email: $email")
            this.firebaseSendPasswordResetEmail(email)
        }
    }

    private fun firebaseSendPasswordResetEmail(email: String) {
        Log.i("AUTH", "Trying to send email.")
        Firebase.auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i("AUTH", "Email forgot password sent.")
                    Toast.makeText(this, "Email sent success", Toast.LENGTH_LONG).show()
                } else {
                    Log.e("AUTH", "sendPasswordResetEmail failed: ${task.exception?.message}")
                    Toast.makeText(this, "Email sent failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}